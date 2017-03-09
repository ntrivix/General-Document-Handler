package models;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.color.ColorSpace;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import utilities.SerializableBasicStroke;
import view.Element.painters.SquarePainter;

public class MSquareElement extends MElement {
	public MSquareElement(String name, Paint paint, Stroke stroke, Color strokeColor, Dimension size,
			Point2D position) {
		super(paint, new SerializableBasicStroke(stroke), strokeColor, size, position);
		elementPainter = new SquarePainter(this);
		this.name = name;
	}

	public MSquareElement(String name, Point2D position) {
		super(position);
		elementPainter = new SquarePainter(this);
		this.name = name;
	}
}
