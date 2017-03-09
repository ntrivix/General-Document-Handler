package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import models.MGraphSlot;
import models.MWorkspace;
import states.StateManager;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import view.slot.VGraphSlot_Dialog;

public class CSquareState extends AbstractAction {

	public CSquareState() {
		super("Square state", GetResource.getIcon("icons/rectangle.png"));
		this.setEnabled(true);
		new AbstractActionTranslator(this,"Rectangle", "RectangleL");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MGraphSlot slot = VGraphSlot_Dialog.getModel();
		slot.startSquareState();
	}

}