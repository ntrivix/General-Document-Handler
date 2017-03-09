package command;

import java.util.ArrayList;

import models.MElement;
import models.MGraphSlot;

public class RotateElementCommand extends AbstractCommand {
	MGraphSlot model;
	ArrayList<MElement> elements;
	int angle;

	public RotateElementCommand(MGraphSlot model, ArrayList<MElement> elements, int angle) {
		this.model = model;
		this.elements = elements;
		this.angle = angle;
	}

	@Override
	public void doCommand() {
		model.deselectAll();
		for (MElement e : elements) {
			e.rotate(angle);
			e.setSelected(true);
		}
		model.setFocus();
	}

	@Override
	public void undoCommand() {
		model.deselectAll();
		for (MElement e : elements) {
			e.rotate(-angle);
			e.setSelected(true);
		}
		model.setFocus();
	}

}
