package command;

import java.awt.Dimension;

import models.MElement;
import models.MGraphSlot;

public class ResizeElementCommand extends AbstractCommand {

	MGraphSlot model;
	MElement element;
	Dimension oldSize, newSize;

	public ResizeElementCommand(MGraphSlot model, MElement element, Dimension oldSize, Dimension newSize) {
		this.model = model;
		this.oldSize = oldSize;
		this.newSize = newSize;
		this.element = element;
	}

	@Override
	public void doCommand() {
		try {
			element.setSize(newSize);
		} catch (Exception е) {
			System.out.println("eksepsn!!!");
		}

		model.setFocus();
	}

	@Override
	public void undoCommand() {
		element.setSize(oldSize);
		model.setFocus();
	}

}
