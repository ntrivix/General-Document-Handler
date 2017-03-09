package states;

import java.awt.event.MouseEvent;

import javax.swing.tree.DefaultMutableTreeNode;

import models.MGraphSlot;

public abstract class State {
	public void mousePressed(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
	
	public void outOfState() {
		MGraphSlot model = (MGraphSlot)getModel();
		model.deselectAll();
	}
	
	public void inState() {
	}
	
	public abstract DefaultMutableTreeNode getModel();

}
