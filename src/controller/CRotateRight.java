package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.tree.DefaultMutableTreeNode;

import command.RotateElementCommand;
import models.MElement;
import models.MGraphSlot;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import view.slot.VGraphSlot_Dialog;

public class CRotateRight extends AbstractAction {

	public CRotateRight() {
		super("Rotate right", GetResource.getIcon("icons/RotateRight.png"));
		this.setEnabled(true);
		new AbstractActionTranslator(this, "RotateRight", "RotateRightL");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultMutableTreeNode sel = VGraphSlot_Dialog.getModel();
		if (sel instanceof MGraphSlot) {
			MGraphSlot slot = (MGraphSlot)sel;
			slot.getCommandManager().addCommand(new RotateElementCommand(slot, slot.getSelected(), 90));
			slot.setFocus();
		}
		VGraphSlot_Dialog.instance.repaint();
		VGraphSlot_Dialog.instance.validate();
		VGraphSlot_Dialog.instance.invalidate();
	}
}
