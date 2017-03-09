package controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.tree.DefaultMutableTreeNode;

import models.MGraphSlot;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import view.slot.VGraphSlot_Dialog;

public class CCopyElements extends AbstractAction {

	public CCopyElements() {
		super("Copy", GetResource.getIcon("icons/Copy.png"));
		new AbstractActionTranslator(this, "Copy", "Copy");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultMutableTreeNode sel = VGraphSlot_Dialog.getModel();
		if (sel instanceof MGraphSlot) {
			MGraphSlot slot = (MGraphSlot)sel;
			slot.getPage().getDocuments().get(0).getClipboard().addToClipboard((ArrayList)slot.getSelected());
			slot.getPage().getDocuments().get(0).getClipboard().setCuted(false);
		}
		VGraphSlot_Dialog.instance.repaint();
	}

}
