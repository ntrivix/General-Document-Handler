package controller;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import models.MProject;
import models.MWorkspace;
import utilities.AdaptiveDefaultMutableTreeNode;
import utilities.FileActions;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import utilities.translations.MultilanguageString;
import view.VWorkspace;
import view.Dialogs.VFileChooser;

public class CSaveAll extends AbstractAction {

	private static MultilanguageString message = new MultilanguageString("noProjectSave",null);

	public CSaveAll() {
		super("null", GetResource.getIcon("icons/Save.png"));
		this.setEnabled(false);
		
		new AbstractActionTranslator(this, "saveAllS", "saveAllLong");
		
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift S"));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		/* Ako nije projekat, spremi deep klonove za cuvanje */
		if (MWorkspace.getInstance().getProjects().size() == 0) {
			JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		for (MProject project : MWorkspace.getInstance().getProjects()) {
			File projectFile = project.getFile();

			if (projectFile == null) {
				VFileChooser jf = VFileChooser.getInstance();
				jf.setCurrentDirectory(new File(MWorkspace.getInstance().getWorkspaceLocation()));
				if (jf.showSaveDialog(VWorkspace.getInstance()) == JFileChooser.APPROVE_OPTION) {
					String name = jf.getSelectedFile().getName();

					String extension = name.substring(name.lastIndexOf('.') + 1, name.length());

					if (!extension.equals("pf")) {
						projectFile = new File(jf.getSelectedFile().getAbsolutePath() + ".pf");
					} else
						projectFile = jf.getSelectedFile();

					project.setFile(projectFile);
				} else
					return;
			}
			((AdaptiveDefaultMutableTreeNode) project).setChanged(false);
			((FileActions) project).saveStates();
			project.save();
		}
	}

}
