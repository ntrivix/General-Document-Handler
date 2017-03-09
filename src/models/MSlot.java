package models;

import java.io.Serializable;
import java.util.ArrayList;

import utilities.AdaptiveDefaultMutableTreeNode;
import utilities.DeepObjectCopy;
import utilities.FileActions;
import utilities.Focusable;
import utilities.Observer;
import utilities.clipboard.PasteAction;

public abstract class MSlot extends AdaptiveDefaultMutableTreeNode
		implements FileActions, Focusable, Serializable, PasteAction {
	private String name;
	private transient ArrayList<Observer> observers = new ArrayList<>();
	private MPage page;

	public MSlot(String name) {
		super();
		this.name = name;
		this.deleted = false;
		this.changed = true;
		this.old = null;
	}

	public MPage getPage() {
		return page;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		if (this.changed && !changed) {
			page.setChildrenUnsaved(page.getChildrenUnsaved() - 1);
		}
		if (!this.changed && changed) {
			page.setChildrenUnsaved(page.getChildrenUnsaved() + 1);
		}
		this.changed = changed;
	}

	@Override
	public void setFocus() {
		super.setFocus();
		notifyObservers(this, "focus");
	}

	public void InitializeObservers() {
		observers = new ArrayList<>();
		this.old = (AdaptiveDefaultMutableTreeNode) DeepObjectCopy.copy(this);
	}

	@Override
	public void unfocus() {
		notifyObservers(this, "unfocus");
	}

	@Override
	public void delete() {
		this.deleted = true;
		this.setChanged(true);
		MWorkspace.getInstance().removeNodeFromParent(this);
		page.getSlots().remove(this);
		notifyObservers(this, "del");
	}

	@Override
	public void rename(String name) {
		this.name = name;
		this.setChildrenUnsaved(this.getChildrenUnsaved() + 1);
		this.setChanged(true);
	}

	@Override
	public String toString() {
		return ((changed == true) ? ("*" + this.name) : (this.name));
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void notifyObservers(Object arg, String command) {
		for (Observer observer : observers) {
			observer.update(this, arg, command);
		}
	}

	public void addObserver(Observer o) {
		observers.add(o);
	}

	public void setPage(MPage page) {
		this.page = page;
	}

	@Override
	public void saveStates() {
		this.setChanged(false);
		this.old = (MSlot) DeepObjectCopy.copy(this);
		notifyObservers(this, "Rename");
	}

	@Override
	public void saveFile() {
		this.setOld(this);
		page.saveFile();
		notifyObservers(this, "Rename");
	}

	public void assignToPage(MPage page) {
		this.page = page;
		page.addSlot(this);
		page.setChildrenUnsaved(page.getChildrenUnsaved() + 1);
	}

	public abstract void addElement(MElement element);

	public abstract ArrayList<MElement> getElement();

	@Override
	public void paste(FileActions object) {

	}

}
