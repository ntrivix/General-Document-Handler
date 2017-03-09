package controller;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;


import models.MProject;
import models.MWorkspace;
import utilities.AdaptiveDefaultMutableTreeNode;
import utilities.FileActions;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import utilities.translations.MultilanguageString;
import view.VWorkspace;
import view.Dialogs.VFileChooser;


public class CSaveAs extends AbstractAction {
	

	public CSaveAs() {
		super("", GetResource.getIcon("icons/SaveAs.png"));
		this.setEnabled(false);
		new AbstractActionTranslator(this, "saveAsS", "saveAsLong");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl alt S"));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		/* Ako nije projekat, spremi deep klonove za cuvanje */
		DefaultMutableTreeNode node = MWorkspace.getInstance().getActiveNode();
		DefaultMutableTreeNode project = node;
		while (!(project instanceof MProject))
			project = (DefaultMutableTreeNode) project.getParent();
		
		//File projectFile = ((MProject) project).getFile();
		File projectFile = null;
		
		if (projectFile == null){
			VFileChooser jf = VFileChooser.getInstance();
			jf.setCurrentDirectory(new File(MWorkspace.getInstance().getWorkspaceLocation()));
			if (jf.showSaveDialog(VWorkspace.getInstance()) == JFileChooser.APPROVE_OPTION) {
				String name = jf.getSelectedFile().getName();				
				
				String extension = name.substring(name.lastIndexOf('.') + 1, name.length());
				
				if (!extension.equals("pf")) {
					projectFile = new File(jf.getSelectedFile().getAbsolutePath() + ".pf");
				} else
					projectFile = jf.getSelectedFile();

				((MProject)project).setFile(projectFile);
			} else 
				return;
		}
		
		
		((AdaptiveDefaultMutableTreeNode) node).setChanged(false);
		((FileActions) node).saveStates();
		if (!(node instanceof MProject)) {
			node = (DefaultMutableTreeNode) node.getParent();
			((FileActions) node).saveFile();
		}

		
		((MProject) project).save();
		
	}
}
