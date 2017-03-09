package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import models.MGraphSlot;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import view.slot.VGraphSlot_Dialog;

public class CCircleState extends AbstractAction {

	public CCircleState() {
		super("Circle state", GetResource.getIcon("icons/circle.png"));
		this.setEnabled(true);
		new AbstractActionTranslator(this,"Circle", "CircleL");

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MGraphSlot slot = VGraphSlot_Dialog.getModel();
		System.out.println(slot);
		slot.startCircleState();
	}

}