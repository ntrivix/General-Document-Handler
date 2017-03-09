package command;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import models.MElement;
import models.MGraphSlot;
import utilities.FileActions;

public class PasteElementCommand extends AbstractCommand {
	private MGraphSlot model;
	private ArrayList<FileActions> elements;
	private ArrayList<FileActions> newelements;
	private double x, y;
	private boolean cutted;

	public PasteElementCommand(MGraphSlot model, ArrayList<FileActions> elements, double x, double y) {
		this.model = model;
		this.elements = elements;
		this.x = x;
		this.y = y;
		this.cutted = model.getPage().getDocuments().get(0).getClipboard().isCuted();
	}

	@Override
	public void doCommand() {

		if (newelements == null) {
			model.getPage().getDocuments().get(0).getClipboard().setPasteLocation(x, y);
			newelements = model.getPage().getDocuments().get(0).getClipboard().pasteAll(model);
		} else {
			for (FileActions el : newelements) {
				model.addElement((MElement) el);
			}
			if (cutted)
				for (FileActions el : elements) {
					el.delete();
				}
		}
	}

	@Override
	public void undoCommand() {
		for (FileActions el : newelements) {
			el.delete();
		}
		if (cutted)
			for (FileActions el : elements) {
				model.addElement((MElement) el);
			}

	}
}
