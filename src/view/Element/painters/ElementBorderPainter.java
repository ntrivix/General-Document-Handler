package view.Element.painters;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import models.MElement;
import view.Element.Handles;

public class ElementBorderPainter implements Serializable {
	private Rectangle2D.Double shape;
	private transient Handles handles;

	public ElementBorderPainter(MElement element) {
		shape = new Rectangle2D.Double(element.getPosition().getX(), element.getPosition().getY(), element.getSize().getWidth(), element.getSize().getHeight());
		handles = new Handles(element);
	}

	public void paint(Graphics2D g, MElement element) {
		if (!element.isSelected()) return;
		
		try {
			handles.paint(g);
		} catch ( Exception e ) {
			handles = new Handles(element);
			handles.paint(g);
		}
		
		shape.setFrame(element.getPosition().getX(), element.getPosition().getY(), element.getSize().getWidth(), element.getSize().getHeight());
		g.setPaint(Color.BLUE);
		g.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{4,4}, 0));
		g.draw(shape);
		
		
		
	}

	public boolean isElementAt(Point pos) {
		return getShape().contains(pos);
	}

	public Shape getShape() {
		return shape;
	}

}
