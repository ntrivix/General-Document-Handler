package states;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.tree.DefaultMutableTreeNode;

import command.AddElementCommand;
import models.MGraphSlot;
import models.MTriangleElement;

public class TriangleState extends State {
	private MGraphSlot model;

	public TriangleState(MGraphSlot model) {
		this.model = model;
	}

	public void mousePressed(MouseEvent e) {
		Point position = e.getPoint();
		if (e.getButton() == MouseEvent.BUTTON1) {

			if (model.getElementAt(e.getPoint()) != null)
				return;
			model.getCommandManager().addCommand(new AddElementCommand(model, e.getPoint(), "Triangle"));
		}
	}

	@Override
	public DefaultMutableTreeNode getModel() {
		return model;
	}
}
