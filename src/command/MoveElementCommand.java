package command;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import models.MElement;
import models.MGraphSlot;

public class MoveElementCommand extends AbstractCommand {
	Point vektor;
	ArrayList<MElement> elements;
	MGraphSlot model;
	boolean flag = false;

	public MoveElementCommand(MGraphSlot model, ArrayList<MElement> elements, Point vektor) {
		this.model = model;
		this.vektor = vektor;
		this.elements = elements;
		flag = false;
	}

	@Override
	public void doCommand() {
		if (flag) {
			for (MElement e : elements) {
				Point2D point = model.getElements().get(model.getElements().indexOf(e)).getPosition();
				point.setLocation(point.getX() - vektor.getX(), point.getY() - vektor.getY());
				model.getElements().get(model.getElements().indexOf(e)).setPosition(point);
			}
		}
		flag = true;
		model.setFocus();
	}

	@Override
	public void undoCommand() {
		for (MElement e : elements) {
			Point2D point = model.getElements().get(model.getElements().indexOf(e)).getPosition();
			point.setLocation(point.getX() + vektor.getX(), point.getY() + vektor.getY());
			model.getElements().get(model.getElements().indexOf(e)).setPosition(point);
		}
		model.setFocus();

	}

}
