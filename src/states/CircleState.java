package states;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.tree.DefaultMutableTreeNode;

import command.AddElementCommand;
import models.MGraphSlot;

public class CircleState extends State {
	private MGraphSlot model;

	public CircleState(MGraphSlot model) {
		this.model = model;
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (model.getElementAt(e.getPoint()) != null)
				return;
			model.getCommandManager().addCommand(new AddElementCommand(model, e.getPoint(), "Circle"));
		}
	}

	@Override
	public DefaultMutableTreeNode getModel() {
		return model;
	}
}
