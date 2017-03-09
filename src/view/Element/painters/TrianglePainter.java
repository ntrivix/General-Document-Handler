package view.Element.painters;

import java.awt.geom.GeneralPath;

import models.MElement;

public class TrianglePainter extends ElementPainter {

	public TrianglePainter(MElement element) {
		super(element);

		shape=new GeneralPath();
		
		((GeneralPath)shape).moveTo(element.getPosition().getX(),element.getPosition().getY() + element.getSize().getHeight());
		
		((GeneralPath)shape).lineTo(element.getPosition().getX() + element.getSize().getWidth() ,element.getPosition().getY() + element.getSize().getHeight());
		
		((GeneralPath)shape).lineTo(element.getPosition().getX() + element.getSize().getWidth()/2 ,element.getPosition().getY());
		
		((GeneralPath)shape).closePath();
		
		
	}

}
