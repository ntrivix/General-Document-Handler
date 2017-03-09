package models;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.tree.TreePath;

import utilities.AdaptiveDefaultMutableTreeNode;
import utilities.DeepObjectCopy;
import utilities.FileActions;
import utilities.Focusable;
import utilities.Observer;
import utilities.clipboard.Clipboard;

public class MDocument extends AdaptiveDefaultMutableTreeNode implements FileActions, Serializable {

	private String name;
	private int pagesInRow;

	private transient ArrayList<Observer> observers = new ArrayList<Observer>();
	private ArrayList<MProject> projects = new ArrayList<>();
	private ArrayList<MPage> pages = new ArrayList<>();

	private transient Clipboard clipboard;

	public void rename(String name) {
		this.name = name;
		this.setChanged(true);
		this.setChildrenUnsaved(this.getChildrenUnsaved() + 1);
		notifyObservers(name, "RenameTab");
	}

	@Override
	public void delete() {
		this.deleted = true;
		this.setChanged(true);
		MWorkspace.getInstance().removeNodeFromParent(this);
		notifyObservers(this, "del");
	}

	public MDocument(String name) {
		super();
		this.name = name;
		this.deleted = false;
		this.changed = true;
		this.pagesInRow = 1;
		this.old = null;
		for (MProject p : projects)
			p.setChildrenUnsaved(p.getChildrenUnsaved() + 1);
		clipboard = new Clipboard();
	}

	@Override
	public String toString() {
		return ((changed == true || childrenUnsaved > 0) ? ("*(" + this.childrenUnsaved + ")" + this.name)
				: (this.name));
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void addObserver(Observer o) {
		observers.add(o);
	}

	public void InitializeObservers() {
		clipboard = new Clipboard();
		observers = new ArrayList<>();
		for (MPage p : pages) {
			p.InitializeObservers();
		}
		this.old = (AdaptiveDefaultMutableTreeNode) DeepObjectCopy.copy(this);
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		for (MProject p : projects) {
			if (changed && this.changed == false) {
				p.setChildrenUnsaved(p.getChildrenUnsaved() + 1);
			} else if (!changed && this.changed) {
				p.setChildrenUnsaved(p.getChildrenUnsaved() - 1);
			}
		}
		if (!changed) {
			for (MPage p : pages) {
				p.setChildrenUnsaved(0);
			}
			this.setChildrenUnsaved(0);
		}
		this.changed = changed;
	}

	public void notifyObservers(Object arg, String command) {
		for (Observer observer : observers) {
			observer.update(this, arg, command);
		}
	}

	@Override
	public void setFocus() {
		super.setFocus();
		notifyObservers(this, "focus");
	}

	public void addPage(MPage page) {
		pages.add(page);
		MWorkspace.getInstance().insertNodeInto(page, this, this.getChildCount());
		MWorkspace.getInstance().getTreeSelectionModel().setSelectionPath(new TreePath(page.getPath()));
		notifyObservers(page, "newPage");
		this.setChanged(true);
	}

	public void assingToProject(MProject p) {
		this.projects.add(p);
		p.addDocument(this);
		p.setChildrenUnsaved(p.getChildrenUnsaved() + 1);
	}

	public ArrayList<MProject> getAssignedProjects() {
		return this.projects;
	}

	public ArrayList<MPage> getPages() {
		return pages;
	}

	public int getPagesInRow() {
		return pagesInRow;
	}

	public void setPagesInRow(int pagesInRow) {
		this.pagesInRow = pagesInRow;
		notifyObservers((Integer) pagesInRow, "rearrange");
	}

	@Override
	public void unfocus() {

	}

	@Override
	public void saveStates() {
		this.setChanged(false);
		for (MPage p : pages)
			p.saveStates();
		this.old = (MDocument) DeepObjectCopy.copy(this);
		notifyObservers(this, "RenameTab");
	}

	@Override
	public void saveFile() {
		MDocument D = new MDocument(name);
		D.setChanged(false);
		D.setPagesInRow(pagesInRow);
		for (MPage p : pages)
			if (p.getOld() != null)
				((MPage) p.getOld()).assignToDocument(D);
		this.setOld(D);
		projects.get(0).saveFile();
	}

	public Clipboard getClipboard() {
		return clipboard;
	}

}
