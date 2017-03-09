package models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import utilities.SerializableBasicStroke;
import view.Element.painters.CirclePainter;
import view.Element.painters.SquarePainter;

public class MCircleElement extends MElement {

	public MCircleElement(Paint paint, Stroke stroke, Color strokeColor, Dimension size, Point2D position) {
		super(paint, new SerializableBasicStroke(stroke), strokeColor, size, position);
		elementPainter = new CirclePainter(this);
	}

	public MCircleElement(String name, Point2D position) {
		super(position);
		elementPainter = new CirclePainter(this);
		this.name = name;
	}

	@Override
	public void translate(double x, double y) {
		super.translate(x, y);
	}

	

}
