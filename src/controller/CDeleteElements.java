package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.tree.DefaultMutableTreeNode;

import command.DeleteElementCommand;
import models.MGraphSlot;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import view.slot.VGraphSlot_Dialog;

public class CDeleteElements extends AbstractAction {

	public CDeleteElements() {
		super("Delete elements", GetResource.getIcon("icons/eraser.png"));
		this.setEnabled(true);
		new AbstractActionTranslator(this, "deleteElementS", "deleteElementS");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultMutableTreeNode sel = VGraphSlot_Dialog.getModel();
		if (sel instanceof MGraphSlot) {
			MGraphSlot slot = (MGraphSlot)sel;
			slot.getCommandManager().addCommand(new DeleteElementCommand(slot, slot.getSelected()));
		}
	}

}