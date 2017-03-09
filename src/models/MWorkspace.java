package models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import controller.ActionManager;
import utilities.Observer;
import view.VDesktop;
import view.VWorkspace;

public class MWorkspace extends DefaultTreeModel implements Serializable {
	private MGraphSlot mGraphSlot;

	public MGraphSlot getmGraphSlot() {
		return mGraphSlot;
	}

	public void setmGraphSlot(MGraphSlot mGraphSlot) {
		this.mGraphSlot = mGraphSlot;
	}

	private static MWorkspace instance;

	private ArrayList<MProject> projects;
	private DefaultMutableTreeNode activeNode; // instanca fokusiranog objekta
												// koji implementira Focusable
												// (Mproject, MDocument ...)
	private TreeSelectionModel treeSelectionModel;

	private ArrayList<Observer> observers;

	private String workspaceLocation;
	private MWorkspaceFile workspaceFile;

	private MWorkspace(String workspaceLocation) {
		super(new DefaultMutableTreeNode(workspaceLocation));
		observers = new ArrayList<>();
		this.initialize(workspaceLocation);
	}

	private void initialize(String workspaceLocation) {

		this.setRoot(new DefaultMutableTreeNode(workspaceLocation));
		projects = new ArrayList<>();
		// observers = new ArrayList<>();
		activeNode = null;
		treeSelectionModel = new DefaultTreeSelectionModel();
		treeSelectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.workspaceLocation = workspaceLocation;

		workspaceFile = MWorkspaceFile.open(workspaceLocation);
		ArrayList<String> missing = new ArrayList<>();
		for (String f : workspaceFile.getProjectPaths()) {

			ObjectInputStream stream;

			try {

				stream = new ObjectInputStream(new FileInputStream(f));
				MProject project;
				try {
					project = (MProject) stream.readObject();
					project.InitializeObservers();
					this.addProject(project);
				} catch (ClassNotFoundException e) {

				}
				stream.close();
			} catch (FileNotFoundException e1) {
				missing.add(f);
			} catch (IOException e1) {

			}
		}
		if (missing.size() > 0) {
			StringBuilder message = new StringBuilder();
			message.append("Missing files:\n");
			for (String p : missing) {
				this.workspaceFile.remove(p);
				message.append(p + "\n");
			}
			this.workspaceFile.save();
			JOptionPane.showMessageDialog(null, message.toString());
		}
	}

	public void addProjectsToWorkspace(ArrayList<String> projects) {
		for (String p : projects) {
			this.workspaceFile.addProjectPath(p);
		}
		this.workspaceFile.save();
	}

	public void removeProjectFromWorkspace(String project) {
		this.workspaceFile.remove(project);
		this.workspaceFile.save();
	}

	public static MWorkspace getInstance() {
		if (instance == null) {
			instance = new MWorkspace(FileSystemView.getFileSystemView().getDefaultDirectory().getPath());
		}
		return instance;
	}

	public static void changeWorkspace(String workspaceLocation) {

		VWorkspace.getInstance().getDesktop().removeAll();
		instance.initialize(workspaceLocation);

		ActionManager.getInstance().getcTree().init();
		VWorkspace.getInstance().getvTree().init();

		VWorkspace.getInstance().getDesktop().updateUI();
	}

	public void InitializeObservers() {
		observers = new ArrayList<Observer>();
		for (MProject p : projects) {
			p.InitializeObservers();
		}
	}

	public void addProject(MProject project) {
		projects.add(project);
		this.insertNodeInto(project, (MutableTreeNode) this.getRoot(),
				((MutableTreeNode) this.getRoot()).getChildCount());
		notifyObservers(project, "addProject");
	}

	public ArrayList<MProject> getProjects() {
		return projects;
	}

	public TreeSelectionModel getTreeSelectionModel() {
		return treeSelectionModel;
	}

	public void addObserver(Observer o) {
		observers.add(o);
	}

	public DefaultMutableTreeNode getActiveNode() {
		return activeNode;
	}

	public void setActiveNode(DefaultMutableTreeNode activeNode) {
		try {
			if (activeNode.equals(getActiveNode()))
				return;
			this.activeNode = activeNode;
		} catch (Exception nullException) {
			this.activeNode = null;
		}
		notifyObservers(this.activeNode, "focusChanged");
	}

	public void notifyObservers(Object arg, String command) {
		try {
			for (Observer observer : observers) {
				observer.update(this, arg, command);
			}
		} catch (Exception e) {
			observers = new ArrayList<>();
		}
	}

	public String getWorkspaceLocation() {
		return workspaceLocation;
	}

	public MWorkspaceFile getWorkspaceFile() {
		return workspaceFile;
	}

}
