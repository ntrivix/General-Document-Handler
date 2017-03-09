package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.tree.DefaultMutableTreeNode;


import models.MGraphSlot;
import models.MTextSlot;
import models.MWorkspace;
import states.StateManager;
import utilities.GetResource;
import utilities.Observer;
import utilities.translations.MultilanguageString;
import view.Settings.VChangeLanguage;
import view.slot.VGraphSlot_Dialog;
import view.slot.VTextSlot;

public class CEditMode extends AbstractAction {

	private static MultilanguageString editModeS = new MultilanguageString("editModeS",null);

	
	public CEditMode() {
		super(editModeS.toString(), GetResource.getIcon("icons/edit.png"));
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		DefaultMutableTreeNode sel = MWorkspace.getInstance().getActiveNode();
		if (sel instanceof MGraphSlot) {
			MGraphSlot slot = (MGraphSlot)sel;
			slot.notifyObservers(slot, "EditMode");
		}
		else if (sel instanceof MTextSlot) {
			MTextSlot slot = (MTextSlot)sel;
			slot.notifyObservers(slot, "EditMode");
		}
	}

}


