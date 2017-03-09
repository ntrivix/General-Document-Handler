package controller.AddActions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import models.MProject;
import models.MWorkspace;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import utilities.translations.MultilanguageString;

public class CAddProject extends AbstractAction {

	public CAddProject() {
		super("", GetResource.getIcon("icons/project.png"));

		new AbstractActionTranslator(this, "newProjectS", "newProjectLong");

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl P"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int count = MWorkspace.getInstance().getProjects().size();
		MWorkspace.getInstance().addProject(new MProject("Project " + count));
	}
}
