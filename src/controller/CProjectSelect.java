package controller;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import models.MProject;

public class CProjectSelect extends InternalFrameAdapter {

	private MProject projectM;

	public CProjectSelect(MProject projectM) {
		super();
		this.projectM = projectM;
	}

	@Override
	public void internalFrameActivated(InternalFrameEvent e) {
		projectM.setFocus();
	}
}
