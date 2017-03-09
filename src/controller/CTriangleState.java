package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import models.MGraphSlot;
import models.MWorkspace;
import states.StateManager;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import view.slot.VGraphSlot_Dialog;

public class CTriangleState extends AbstractAction {

	public CTriangleState() {
		super("Triangle state", GetResource.getIcon("icons/triangle.png"));
		this.setEnabled(true);
		putValue(SHORT_DESCRIPTION, "Triangle");
		putValue(LONG_DESCRIPTION, "Triangle state");
		new AbstractActionTranslator(this, "triangeS", "triangleLongS");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MGraphSlot slot = VGraphSlot_Dialog.getModel();
		slot.startTriangleState();
	}

}