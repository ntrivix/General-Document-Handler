package controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.tree.DefaultMutableTreeNode;

import models.MElement;
import models.MGraphSlot;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import view.slot.VGraphSlot_Dialog;

public class CCutElement extends AbstractAction {

	public CCutElement() {
		super("Cut", GetResource.getIcon("icons/cut.png"));
		new AbstractActionTranslator(this, "Cut", "Cut");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultMutableTreeNode sel = VGraphSlot_Dialog.getModel();
		if (sel instanceof MGraphSlot) {
			MGraphSlot slot = (MGraphSlot)sel;
			slot.getPage().getDocuments().get(0).getClipboard().addToClipboard((ArrayList)slot.getSelected());
			
			slot.getPage().getDocuments().get(0).getClipboard().setCuted(true);
		}
		VGraphSlot_Dialog.instance.repaint();
	}
}
