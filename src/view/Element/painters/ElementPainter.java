package view.Element.painters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import models.MCircleElement;
import models.MElement;
import models.MTriangleElement;

public class ElementPainter implements Serializable {
	protected Shape shape;

	public ElementPainter(MElement element) {

	}

	public void paint(Graphics2D g, MElement element) {
		RenderingHints h = g.getRenderingHints(); 
		
		if (element instanceof MCircleElement || element instanceof MTriangleElement) 
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (element.isCutted())
			g.setPaint(Color.decode("#f2f2f2"));
		else
			g.setPaint(element.getPaint());
		g.fill(getShape());
		if (element.isCutted())
			g.setPaint(Color.decode("#eaeaea"));
		else
		g.setPaint(element.getStrokeColor());
		g.setStroke(element.getStroke());
		if (shape instanceof Ellipse2D.Double) {
			shape = new Ellipse2D.Double(element.getPosition().getX(), element.getPosition().getY(), element.getSize().getWidth(), element.getSize().getHeight());
		}
		else if (shape instanceof Rectangle2D.Double){
			shape = new Rectangle2D.Double(element.getPosition().getX(), element.getPosition().getY(), element.getSize().getWidth(), element.getSize().getHeight());
		}
		else {
			shape=new GeneralPath();
			if (element.getRotated() % 4 == 0) {
				((GeneralPath)shape).moveTo(element.getPosition().getX(),element.getPosition().getY() + element.getSize().getHeight());
				((GeneralPath)shape).lineTo(element.getPosition().getX() + element.getSize().getWidth() ,element.getPosition().getY() + element.getSize().getHeight());
				((GeneralPath)shape).lineTo(element.getPosition().getX() + element.getSize().getWidth()/2 ,element.getPosition().getY());
				((GeneralPath)shape).closePath();
			}
			else if (element.getRotated() % 4 == 1) {
				((GeneralPath)shape).moveTo(element.getPosition().getX(), element.getPosition().getY());
				((GeneralPath)shape).lineTo(element.getPosition().getX(), element.getPosition().getY() + element.getSize().getHeight());
				((GeneralPath)shape).lineTo(element.getPosition().getX() + element.getSize().getWidth(), element.getPosition().getY() + element.getSize().getHeight()/2);
				((GeneralPath)shape).closePath();
			}
			else if (element.getRotated() % 4 == 2) {
				((GeneralPath)shape).moveTo(element.getPosition().getX(),element.getPosition().getY());
				((GeneralPath)shape).lineTo(element.getPosition().getX() + element.getSize().getWidth() ,element.getPosition().getY());
				((GeneralPath)shape).lineTo(element.getPosition().getX() + element.getSize().getWidth()/2 ,element.getPosition().getY() + element.getSize().getHeight());
				((GeneralPath)shape).closePath();
			}
			else if (element.getRotated() % 4 == 3) {
				((GeneralPath)shape).moveTo(element.getPosition().getX(), element.getPosition().getY() + element.getSize().getHeight()/2);
				((GeneralPath)shape).lineTo(element.getPosition().getX() + element.getSize().getWidth(), element.getPosition().getY());
				((GeneralPath)shape).lineTo(element.getPosition().getX() + element.getSize().getWidth(), element.getPosition().getY() + element.getSize().getHeight());
				((GeneralPath)shape).closePath();
			}
		}
		g.draw(getShape());
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}

	public boolean isElementAt(Point pos) {
		return getShape().contains(pos);
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}
}
