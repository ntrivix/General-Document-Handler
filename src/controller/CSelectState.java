package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import models.MGraphSlot;
import models.MWorkspace;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;

public class CSelectState extends AbstractAction {

	public CSelectState() {
		super("Select state", GetResource.getIcon("icons/cursor.png"));
		this.setEnabled(true);
		new AbstractActionTranslator(this, "Select", "Select");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MGraphSlot slot = (MGraphSlot) MWorkspace.getInstance().getActiveNode();
		slot.startSelectState();
		slot.setFocus();
	}

}
