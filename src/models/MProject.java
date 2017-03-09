package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import utilities.AdaptiveDefaultMutableTreeNode;
import utilities.DeepObjectCopy;
import utilities.FileActions;
import utilities.Focusable;
import utilities.Observer;
import view.VWorkspace;
import view.Dialogs.VFileChooser;

public class MProject extends AdaptiveDefaultMutableTreeNode implements FileActions, Focusable, Serializable {

	private String pName;
	private ArrayList<MDocument> documents;

	private File file;

	private transient ArrayList<Observer> observers;

	public MProject(String pName) {
		super(pName);
		this.pName = pName;
		this.deleted = false;
		this.changed = true;
		this.file = null;
		this.old = null;

		observers = new ArrayList<>();
		documents = new ArrayList<>();
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	@Override
	public String toString() {
		return (((changed == true) ? "*(" + this.childrenUnsaved + ")" : "") + pName);
	}

	public void InitializeObservers() {
		observers = new ArrayList<>();
		for (MDocument d : documents) {
			d.InitializeObservers();
		}
		this.old = (AdaptiveDefaultMutableTreeNode) DeepObjectCopy.copy(this);
	}

	public Boolean isDeleted() {
		return deleted;
	}

	public void addDocument(MDocument document) {
		this.setChanged(true);
		documents.add(document);
		MWorkspace.getInstance().insertNodeInto(document, this, this.getChildCount());
		MWorkspace.getInstance().getTreeSelectionModel().setSelectionPath(new TreePath(document.getPath()));
		notifyObservers(document, "newDocument");
	}

	public boolean isChanged() {
		return changed;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setChanged(boolean changed) {
		if (!changed) {
			this.setChildrenUnsaved(0);
		}
		this.changed = changed;
	}

	@Override
	public void delete() {
		this.setChanged(true);
		MWorkspace.getInstance().getProjects().remove(this);
		this.deleted = true;
		MWorkspace.getInstance().removeNodeFromParent(this);

		if (this.getFile() != null) {
			MWorkspace.getInstance().removeProjectFromWorkspace(this.getFile().getAbsolutePath());
			this.getFile().delete();
		}

		notifyObservers(this, "delProject");
	}

	public void addObserver(Observer o) {
		observers.add(o);
	}

	public void notifyObservers(Object arg, String command) {
		for (Observer observer : observers) {
			observer.update(this, arg, command);
		}
	}

	public void rename(String pName) {
		this.pName = pName;
		this.setChanged(true);
		this.setChildrenUnsaved(this.getChildrenUnsaved() + 1);
		notifyObservers(pName, "Rename");
	}

	public ArrayList<MDocument> getDocuments() {
		return documents;
	}

	@Override
	public void setFocus() {
		super.setFocus();
		notifyObservers(this, "focusProject");
	}

	@Override
	public void saveStates() {
		this.setChanged(false);
		for (MDocument d : documents)
			d.saveStates();
		this.old = (MProject) DeepObjectCopy.copy(this);
		notifyObservers(this, "Rename");
	}

	public void save() {
		MProject project = (MProject) this.getOld();
		File pf = (project).getFile();
		if (pf == null)
			return;

		String filePath = this.getFile().getAbsolutePath();

		ObjectOutputStream stream;
		try {
			stream = new ObjectOutputStream(new FileOutputStream(pf));
			stream.writeObject(project);
			project.setChanged(false);
			project.setFile(pf);
			this.setFile(pf);
			stream.close();
			ArrayList<String> p = new ArrayList<>();
			p.add(filePath);
			MWorkspace.getInstance().addProjectsToWorkspace(p);
			VWorkspace.getInstance().getvTree().updateUI();
			notifyObservers(this, "Rename");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveFile() {
		MProject P = new MProject(pName);
		for (MDocument d : documents) {
			if (d.getOld() != null)
				((MDocument) d.getOld()).assingToProject(P);
		}
		P.setFile(this.file);
		this.setOld(P);

	}

}
