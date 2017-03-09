package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;


import models.MWorkspace;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import utilities.translations.MultilanguageString;


public class CMoveDown extends AbstractAction  {

	public CMoveDown() {
		super("", GetResource.getIcon("icons/down.png"));
		new AbstractActionTranslator(this, "moveDownS", "moveDownS");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt DOWN"));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			DefaultMutableTreeNode down = MWorkspace.getInstance().getActiveNode().getNextNode();
			MWorkspace.getInstance().getTreeSelectionModel().setSelectionPath(new TreePath(down.getPath()));
		} catch (Exception e) {

		}
	}
}
