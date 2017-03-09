package view.Element.painters;

import java.awt.geom.Rectangle2D;

import models.MElement;

public class SquarePainter extends ElementPainter {

	public SquarePainter(MElement element) {
		super(element);
		shape = new Rectangle2D.Double(element.getPosition().getX(), element.getPosition().getY(), element.getSize().getWidth(), element.getSize().getHeight());
		
	
	}

}
