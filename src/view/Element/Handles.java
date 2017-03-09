package view.Element;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import models.MElement;

public class Handles {
	Point2D point[][] = new Point2D[3][3];
	private final int SIZE = 8;
	private Handle[][] handles = new Handle[3][3];
	private MElement element;
	
	public Handles(MElement element) {
		this.element = element;
		double x0 = element.getPosition().getX();
		double y0 = element.getPosition().getY();
		double width = element.getSize().getWidth();
		double height = element.getSize().getHeight();
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				handles[i][j] = new Handle(new Point2D.Double(x0 + i * width / 2, y0 + j * height / 2));
			}
		}
		element.setHandles(this);
	}
	
	public void paint(Graphics2D g) {
		g.setPaint(Color.BLUE);
		double x0 = element.getPosition().getX();
		double y0 = element.getPosition().getY();
		double width = element.getSize().getWidth();
		double height = element.getSize().getHeight();
		
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				if (i == 1 && j == 1) continue;
				handles[i][j].setTo(new Point2D.Double(x0 + i * width / 2, y0 + j * height / 2));
				handles[i][j].paint(g);
			}
		}
	}
	
	public boolean mouseAtHandle(Point p, int i, int j) {
		return handles[i][j].mouseAtHandle(p);
	}
	
	public Point2D getPoint(int i, int j) {
		return handles[i][j].p;
	}
	
	private class Handle {
		private Rectangle2D.Double square;
		private Point2D.Double p;
		private Handle(Point2D.Double p) {
			this.p = p;
			square = new Rectangle2D.Double(p.getX() - SIZE/2, p.getY() - SIZE/2, SIZE, SIZE);
		}
		
		private void setTo(Point2D.Double p) {
			this.p = p;
			square.setRect(p.getX() - SIZE/2, p.getY() - SIZE/2, SIZE, SIZE);
		}
		
		private boolean mouseAtHandle(Point p) {
			return square.contains(p);
		}
		
		private void paint(Graphics2D g) {
			g.fill(square);
		}
	}
}
