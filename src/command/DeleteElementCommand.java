package command;

import java.util.ArrayList;

import models.MElement;
import models.MGraphSlot;

public class DeleteElementCommand extends AbstractCommand {
	ArrayList<MElement> elements;
	MGraphSlot model;

	public DeleteElementCommand(MGraphSlot model, ArrayList<MElement> element) {
		this.elements = element;
		this.model = model;
	}

	@Override
	public void doCommand() {
		model.deselectAll();
		for (MElement e : elements) {
			e.setSelected(true);
		}
		model.removeSelected();
		model.setFocus();
	}

	@Override
	public void undoCommand() {
		model.deselectAll();
		for (MElement e : elements) {
			model.addElement(e);
		}
		model.setFocus();

	}
}
