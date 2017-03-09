package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;

import models.MGraphSlot;
import models.MWorkspace;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import view.slot.VGraphSlot_Dialog;

public class CRedoCommand extends AbstractAction {
	public CRedoCommand() {
		super("null", GetResource.getIcon("icons/Redo.png"));
		this.setEnabled(false);
		new AbstractActionTranslator(this, "Redo", "RedoL");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl X"));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		MWorkspace.getInstance().setActiveNode(VGraphSlot_Dialog.instance.getModel());
		DefaultMutableTreeNode sel = MWorkspace.getInstance().getActiveNode();
		if (sel instanceof MGraphSlot) {
			MGraphSlot slot = (MGraphSlot) sel;
			slot.getCommandManager().doCommand();
		}
		VGraphSlot_Dialog.instance.repaint();
	}
}
