package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.tree.DefaultMutableTreeNode;

import command.PasteElementCommand;
import models.MGraphSlot;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import view.slot.VGraphSlot_Dialog;

public class CPasteElement extends AbstractAction {
	public CPasteElement() {
		super("Paste", GetResource.getIcon("icons/paste.png"));
		new AbstractActionTranslator(this, "Paste", "Paste");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultMutableTreeNode sel = VGraphSlot_Dialog.getModel();	
		
		if (sel instanceof MGraphSlot) {
			MGraphSlot slot = (MGraphSlot)sel;
		    double pastetox = -1;
		    double pastetoy = -1;
		    
			if (!(e.getSource() instanceof JButton)) {
				pastetox = VGraphSlot_Dialog.mouseClickX;
				pastetoy = VGraphSlot_Dialog.mouseClickY;
			}
			slot.getCommandManager().addCommand(new PasteElementCommand(slot, slot.getPage().getDocuments().get(0).getClipboard().getElements(), pastetox, pastetoy));
		}
		VGraphSlot_Dialog.instance.repaint();
		VGraphSlot_Dialog.instance.revalidate();
	}
}
