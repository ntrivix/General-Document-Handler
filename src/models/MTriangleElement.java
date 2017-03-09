package models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import utilities.SerializableBasicStroke;
import view.Element.painters.TrianglePainter;

public class MTriangleElement extends MElement {
	public MTriangleElement(String name, Paint paint, Stroke stroke, Color strokeColor, Dimension size,
			Point2D position) {
		super(paint, new SerializableBasicStroke(stroke), strokeColor, size, position);
		elementPainter = new TrianglePainter(this);
		this.name = name;
	}

	public MTriangleElement(String name, Point2D position) {
		super(position);
		elementPainter = new TrianglePainter(this);
		this.name = name;
	}

}
