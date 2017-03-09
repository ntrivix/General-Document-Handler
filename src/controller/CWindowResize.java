package controller;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import view.VWorkspace;

public class CWindowResize extends ComponentAdapter {
	public void componentResized(ComponentEvent e) {
		super.componentResized(e);
		VWorkspace.getInstance().getDesktop().rearange();
	}
}
