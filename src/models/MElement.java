package models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import utilities.FileActions;
import utilities.Focusable;
import utilities.Observer;
import utilities.SerializableBasicStroke;
import view.Element.Handles;
import view.Element.painters.ElementBorderPainter;
import view.Element.painters.ElementPainter;

public abstract class MElement extends DefaultMutableTreeNode implements Focusable, Serializable, FileActions {
	protected Paint paint;
	protected SerializableBasicStroke stroke;
	protected Color strokeColor;
	protected Dimension size;
	
	private boolean cutted = false;
	
	protected int rotated = 0;

	protected Point2D position;
	
	public int getRotated() {
		return rotated;
	}

	public boolean isCutted() {
		return cutted;
	}

	public void setCutted(boolean cutted) {
		this.cutted = cutted;
	}

	protected double rotation = 0;
	
	protected transient Handles handles;
	
	public Handles getHandles() {
		return handles;
	}

	public void setHandles(Handles handles) {
		this.handles = handles;
	}

	public double getRotation() {
		return rotation;
	}

	protected String name;
	protected String description;

	protected ElementPainter elementPainter;
	protected ElementBorderPainter elementBorderPainter;

	protected boolean selected = false;
	protected boolean selectedOld = false;
	
	public boolean isSelectedOld() {
		return selectedOld;
	}

	public void setSelectedOld(boolean selectedOld) {
		this.selectedOld = selectedOld;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public boolean intersect(Rectangle2D rect) {
		Rectangle2D shp = new Rectangle2D.Double(position.getX(), position.getY(), size.getWidth(), size.getHeight());
		return shp.intersects(rect);
	}

	private transient boolean deleted;
	private transient ArrayList<Observer> observers = new ArrayList<>();
	private MGraphSlot slot;

	public MElement(Paint paint, SerializableBasicStroke stroke, Color strokeColor, Dimension size, Point2D position) {
		super();
		this.deleted = false;
		this.paint = paint;
		this.stroke = stroke;
		this.strokeColor = strokeColor;
		this.size = size;
		position.setLocation(position.getX() - size.getWidth() / 2, position.getY() - size.getHeight() / 2);
		this.position = position;
		elementBorderPainter = new ElementBorderPainter(this);
	}

	public ElementBorderPainter getElementBorderPainter() {
		return elementBorderPainter;
	}

	public MElement(Point2D position) {
		this((Paint) (Color.WHITE), new SerializableBasicStroke(new BasicStroke(2)), Color.BLACK, new Dimension(100, 50),
				position);
	}

	@Override
	public void setFocus() {	
		if (this.equals(MWorkspace.getInstance().getActiveNode()))
			return;
		((Focusable) this.getParent()).setFocus();
		MWorkspace.getInstance().setActiveNode(this);
		TreePath paths[] = {new TreePath(this.getPath())}; 
		MWorkspace.getInstance().getTreeSelectionModel().setSelectionPaths(paths);
		MWorkspace.getInstance().getTreeSelectionModel().setSelectionPath(new TreePath(this.getPath()));		
		notifyObservers(this, "focus");
		
	}

	public void InitializeObservers() {
		observers = new ArrayList<>();
		handles = new Handles(this);
	}

	@Override
	public void unfocus() {
		//notifyObservers(this, "unfocus");
	}

	public void delete() {
		this.deleted = true;
		MWorkspace.getInstance().removeNodeFromParent(this);
		slot.getElements().remove(this);
		notifyObservers(this, "del");
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void notifyObservers(Object arg, String command) {
		try {
			for (Observer observer : observers) {
				observer.update(this, arg, command);
			}
		} catch (Exception e) {
			observers = new ArrayList<>();
		}
	}

	public void addObserver(Observer o) {
		observers.add(o);
	}

	public void setSlot(MGraphSlot slot) {
		this.slot = slot;
	}

	public boolean hasPoint(Point p) {
		return elementBorderPainter.getShape().contains(p);
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Paint getPaint() {
		return paint;
	}

	public Dimension getSize() {
		return size;
	}

	public boolean setSize(Dimension size) {
		this.size = size;
		if (size.getWidth() < 20 || size.getHeight() < 20) {
			if (size.getWidth() < 20) size.width = 20;
			if (size.getHeight() < 20) size.height = 20;
			return false;
		}
		return true;
	}

	public Point2D getPosition() {
		return position;
	}
	
	

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public Stroke getStroke() {
		return stroke;
	}

	public void setStroke(Stroke stroke) {
		this.stroke = new SerializableBasicStroke(stroke);
	}

	public String toString() {
		return name;
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}

	public ElementPainter getElementPainter() {
		return elementPainter;
	}

	public void setElementPainter(ElementPainter elementPainter) {
		this.elementPainter = elementPainter;
	}
	
	public void translate(double x, double y){
		position.setLocation(position.getX()+x, position.getY()+y);
	}
	
	public void rotate(int angle) {
		rotated += angle / 90;
		if (rotated < 0) rotated += 4;
		rotation += angle * Math.PI / 180;
		slot.notifyObservers(slot, "rotate");
		Point2D centerOld = new Point2D.Double(position.getX() + size.getWidth()/2, position.getY() + size.getHeight()/2);
		size.setSize(size.getHeight(), size.getWidth());
		Point2D centerNew = new Point2D.Double(position.getX() + size.getWidth()/2, position.getY() + size.getHeight()/2);
		double mvX = centerOld.getX() - centerNew.getX();
		double mvY = centerOld.getY() - centerNew.getY();
		position.setLocation(position.getX() + mvX, position.getY() + mvY);
		this.setFocus();
	}
	
	public void rename(String name){};

	public void saveStates(){};

	public void saveFile(){};

}
