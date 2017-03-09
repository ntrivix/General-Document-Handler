package view.Element.painters;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import models.MElement;

public class CirclePainter extends ElementPainter {

	public CirclePainter(MElement element) {
		super(element);
		shape = new Ellipse2D.Double(element.getPosition().getX(), element.getPosition().getY(), element.getSize().getWidth(), element.getSize().getHeight());
	}
}
