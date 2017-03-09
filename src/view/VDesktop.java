package view;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

import models.MProject;
import models.MWorkspace;
import utilities.GetResource;
import utilities.Observer;
import controller.ActionManager;

public class VDesktop extends JDesktopPane implements Observer {
	private ArrayList<MProject> projectsList = MWorkspace.getInstance().getProjects();
	private AbstractAction alignment = ActionManager.getInstance().getcCascadeAlignment();

	public VDesktop() {
		super();
		this.setDragMode(OUTLINE_DRAG_MODE);
		for (MProject project : projectsList) {
			VProject tmp = new VProject(project);
			add(tmp);
		}
		MWorkspace.getInstance().addObserver(this);
	}

	@Override
	public void update(Object observable, Object arg, String command) {
		if (arg instanceof MProject && command.equals("addProject")) {
			MProject project = (MProject) arg;
			VProject p = new VProject(project);
			add(p);
			p.focus();
			p.toFront();
			rearange();
		}
	}

	public void setAlignment(AbstractAction alignment) {
		this.alignment = alignment;
	}

	public void rearange() {
		// Odmah update-uje raspored u skladu sa trenutnim
		alignment.actionPerformed(null);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon icon = GetResource.getIcon("icons/spejsmen.png");
		g.drawImage(icon.getImage(), (this.getWidth() - icon.getIconWidth()) / 2,
				(this.getHeight() - icon.getIconHeight()) / 2, null);
	}

}
