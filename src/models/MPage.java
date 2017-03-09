package models;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.tree.TreePath;

import utilities.AdaptiveDefaultMutableTreeNode;
import utilities.DeepObjectCopy;
import utilities.FileActions;
import utilities.Focusable;
import utilities.Observer;

public class MPage extends AdaptiveDefaultMutableTreeNode implements FileActions, Focusable, Serializable {
	private String name;
	private transient ArrayList<Observer> observers = new ArrayList<>();
	private ArrayList<MDocument> documents = new ArrayList<>();
	private ArrayList<MSlot> slots = new ArrayList<>();

	public ArrayList<MSlot> getSlots() {
		return slots;
	}

	public MPage(String name) {
		super();
		this.name = name;
		this.deleted = false;
		this.changed = true;
		this.old = null;
	}

	public void rename(String name) {
		this.name = name;
		this.setChildrenUnsaved(this.getChildrenUnsaved() + 1);
		this.setChanged(true);
	}

	@Override
	public String toString() {
		return ((changed == true || childrenUnsaved > 0) ? ("*(" + this.childrenUnsaved + ")" + this.name)
				: (this.name));
	}

	public void setFocus() {
		super.setFocus();
		notifyObservers(this, "focus");
	}

	public void notifyObservers(Object arg, String command) {
		for (Observer observer : observers) {
			observer.update(this, arg, command);
		}
	}

	public void InitializeObservers() {
		observers = new ArrayList<>();
		for (MSlot s : slots) {
			s.InitializeObservers();

		}
		this.old = (AdaptiveDefaultMutableTreeNode) DeepObjectCopy.copy(this);
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void addObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void delete() {
		this.setChanged(true);
		this.deleted = true;
		MWorkspace.getInstance().removeNodeFromParent(this);
		for (MDocument d : documents) {
			d.getPages().remove(this);
		}
		notifyObservers(this, "del");
	}

	public ArrayList<MDocument> getAssignedDocuments() {
		return this.documents;
	}

	public void assignToDocument(MDocument document) {
		document.addPage(this);
		this.documents.add(document);
		document.setChildrenUnsaved(document.getChildrenUnsaved() + 1);
	}

	@Override
	public void unfocus() {
		notifyObservers(this, "unfocus");
	}

	public void addSlot(MSlot slot) {
		this.setChanged(true);
		slots.add(slot);
		MWorkspace.getInstance().insertNodeInto(slot, this, this.getChildCount());
		MWorkspace.getInstance().getTreeSelectionModel().setSelectionPath(new TreePath(slot.getPath()));
		slot.setPage(this);
		if (slot instanceof MGraphSlot)
			notifyObservers(slot, "newGraphSlot");
		else if (slot instanceof MTextSlot)
			notifyObservers(slot, "newTextSlot");
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		for (MDocument d : documents) {
			if (changed && this.changed == false) {
				d.setChildrenUnsaved(d.getChildrenUnsaved() + 1);
			} else if (!changed && this.changed) {
				d.setChildrenUnsaved(d.getChildrenUnsaved() - 1);
			}
		}
		if (!changed) {
			for (MSlot s : slots) {
				s.setChildrenUnsaved(0);
			}
			this.setChildrenUnsaved(0);
		}

		this.changed = changed;
	}

	@Override
	public void saveStates() {
		this.setChanged(false);
		for (MSlot m : slots) {
			m.saveStates();
		}
		this.old = (MPage) DeepObjectCopy.copy(this);
		notifyObservers(this, "Rename");
	}

	public void setOld(MPage old) {
		this.old = old;
	}

	@Override
	public void saveFile() {
		MPage P = new MPage(name);
		P.setChanged(false);
		for (MSlot s : slots)
			if (s.getOld() != null)
				((MSlot) s.getOld()).assignToPage(P);
		this.setOld(P);
		documents.get(0).saveFile();
	}

	public ArrayList<MDocument> getDocuments() {
		return documents;
	}

}
