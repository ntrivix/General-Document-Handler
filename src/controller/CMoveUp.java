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



public class CMoveUp extends AbstractAction {

	public CMoveUp() {
		super("", GetResource.getIcon("icons/up.png"));	
		new AbstractActionTranslator(this, "moveUpS", "moveUpS");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("alt UP"));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			DefaultMutableTreeNode up = MWorkspace.getInstance().getActiveNode().getPreviousNode();
			MWorkspace.getInstance().getTreeSelectionModel().setSelectionPath(new TreePath(up.getPath()));
		} catch (Exception e) {

		}

	}
}
