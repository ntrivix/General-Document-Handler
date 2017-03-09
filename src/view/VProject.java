package view;

import java.beans.PropertyVetoException;
import java.io.Serializable;

import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;

import models.MDocument;
import models.MProject;
import utilities.GetResource;
import utilities.Observer;
import controller.ActionManager;
import controller.CProjectSelect;

public class VProject extends JInternalFrame implements Observer {

	public static final int WIDTH = 400, HEIGHT = 400;
	public static final int offsetX = 30, offsetY = 30;
	private static int counter = 0;

	private MProject project;
	private JTabbedPane documentTab;

	public VProject(MProject project) {
		super(project.toString(), true, false, true, true);
		this.project = project;

		setSize(WIDTH, HEIGHT);
		setLocation(counter * offsetX, counter * offsetY);
		counter++;
		setFrameIcon(GetResource.getIcon("icons/project.png"));
		documentTab = new JTabbedPane();

		for (MDocument d : project.getDocuments()) {
			documentTab.add(d.toString(), new VDocument(d));
		}

		this.add(documentTab);
		setVisible(true);
		updateUI();
		documentTab.addChangeListener(ActionManager.getInstance().getcDocumentSelect());
		addInternalFrameListener(new CProjectSelect(project));
		project.addObserver(this);
	}

	public void focus() {
		try {
			this.setSelected(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
	}

	public void unfocus() {
		try {
			this.setSelected(false);
			updateUI();
		} catch (PropertyVetoException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void update(Object observable, Object arg, String command) {
		if (command.equals("Rename")) {
			setTitle(project.toString());
			updateUI();
			return;
		}
		if (observable instanceof MProject) {
			if (project.isDeleted())
				this.dispose();
			else {
				MProject project = (MProject) observable;
				this.focus();
				this.toFront();
			}
		}
		if (arg instanceof MDocument) {
			if (command.equals("newDocument")) {
				VDocument vDoc = new VDocument((MDocument) arg);
				documentTab.add(((MDocument) arg).toString(), vDoc);
				documentTab.setSelectedComponent(vDoc);
			}
		}
		if (command.equals("changed")) {
			project.setChanged(true);
		}
	}

	public JTabbedPane getDocumentTab() {
		return documentTab;
	}

}
