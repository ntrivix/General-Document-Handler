package states;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.geom.Point2D;

import javax.swing.tree.DefaultMutableTreeNode;

import command.MoveElementCommand;
import command.ResizeElementCommand;
import controller.ActionManager;
import models.MElement;
import models.MGraphSlot;
import view.menus_bars.VPopupMenuSlot;
import view.slot.VGraphSlot_Dialog;

public class SelectState extends State {
	private MGraphSlot model; // save the Mediator
	
	private Point p0 = null, p1 = null, p4 = null;
	private Point2D.Double old = null;
	private Dimension oldSize, newSize;
	
	private String subState = "select";
	
	private MElement resizingElement;
	private int handleX, handleY;
	
	public SelectState(MGraphSlot model) {
		this.model = model;
	}

	public void mousePressed(MouseEvent e) {
		Point position = e.getPoint();
		
		if (e.getButton() == MouseEvent.BUTTON3) {	
			VPopupMenuSlot menu = VPopupMenuSlot.getInstance();
			menu.show(VGraphSlot_Dialog.instance, e.getX(), e.getY()+40);
			
			return;
		}
		
		p0 = new Point(position);
		p1 = new Point(position);
		p4 = new Point(position);
		model.prepareLasoSelect();
		
		if ((boolean)e.getSource()) return;
		MElement sel = model.getElementAt(p0);
		
		//proveri handle
		//proveri da je samo 1
		if (sel != null) {
			handleX = handleY = -1;
			for (int i=0; i<3; i++) {
				for (int j=0; j<3; j++) {
					if (sel.getHandles().mouseAtHandle(p0, i, j)) {
						System.out.println("klik na handle");
						subState = "resize";
						resizingElement = sel;
						handleX = i;
						handleY = j;
						old = new Point2D.Double();
						old.setLocation(sel.getPosition());
						oldSize = sel.getSize();
						model.deselectAll();
						sel.setSelected(true);
					}
				}
			}
			if (handleX == 0 && handleY == 0) VGraphSlot_Dialog.changeCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
			else if (handleX == 0 && handleY == 1) VGraphSlot_Dialog.changeCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
			else if (handleX == 0 && handleY == 2) VGraphSlot_Dialog.changeCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
			else if (handleX == 1 && handleY == 0) VGraphSlot_Dialog.changeCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			//else if (handleX == 1 && handleY == 1) VGraphSlot_Dialog.changeCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
			else if (handleX == 1 && handleY == 2) VGraphSlot_Dialog.changeCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
			else if (handleX == 2 && handleY == 0) VGraphSlot_Dialog.changeCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
			else if (handleX == 2 && handleY == 1) VGraphSlot_Dialog.changeCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
			else if (handleX == 2 && handleY == 2) VGraphSlot_Dialog.changeCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
			else VGraphSlot_Dialog.changeCursor(Cursor.getDefaultCursor());
		}
		
		if (sel == null || !sel.isSelected())
			model.lasoSelect((boolean)e.getSource(), p0, p1);
		if (!subState.equals("resize") && sel != null)
			VGraphSlot_Dialog.changeCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
		if (subState.equals("select")) {
			MElement cur = model.getElementAt(e.getPoint());
			
			if (cur != null && cur.isSelected()) {
				subState = "move";
				VGraphSlot_Dialog.changeCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			}
			else {
				subState = "lasso";
			}
		}
		else if (subState.equals("lasso")) {
			p1.setLocation(e.getPoint());
			model.lasoSelect((boolean)e.getSource(), p0, p1);
		}
		else if (subState.equals("resize")) {
			MElement cur = resizingElement;
			
			if (handleX == 2) {
				cur.setSize(new Dimension(e.getX() - (int)cur.getPosition().getX(), cur.getSize().height));
			}
			if (handleX == 0) {
				if (cur.setSize(new Dimension((int)cur.getPosition().getX() + (int)cur.getSize().getWidth() - e.getX(), cur.getSize().height)))
				cur.setPosition(new Point(e.getX(), (int)cur.getPosition().getY()));
			}
			
			if (handleY == 2) {
				cur.setSize(new Dimension(cur.getSize().width, e.getY() - (int)cur.getPosition().getY()));
			}
			if (handleY == 0) {
				if (cur.setSize(new Dimension(cur.getSize().width, (int)cur.getPosition().getY() + (int)cur.getSize().getHeight() - e.getY())))
				cur.setPosition(new Point((int)cur.getPosition().getX(), e.getY()));
			}
			
			newSize = cur.getSize();
		}
		else {
			Point p2 = e.getPoint();
			int dx = p2.x - p1.x;
			int dy = p2.y - p1.y;
			for (MElement elm : model.getElements()) {
				if (elm.isSelected()) {
					elm.translate(dx, dy);
				}
			}
			p1.setLocation(p2.x, p2.y);
			p0.setLocation(p2.x, p2.y);
			VGraphSlot_Dialog.changeCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		}
			
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		VGraphSlot_Dialog.changeCursor(Cursor.getDefaultCursor());
		if (e.getButton() == MouseEvent.BUTTON3)
			return;
			
		if (subState.equals("select") ) {
			model.lasoSelect((boolean)e.getSource(), e.getPoint(), e.getPoint());
		}
		if (subState.equals("resize")){
		  model.getCommandManager().addCommand(new ResizeElementCommand(model, model.getElementAt(p0), oldSize, newSize));	
		}
		if (subState.equals("move")){
		  p4.setLocation(p4.getX()-p0.getX(), p4.getY() - p0.getY());
		  model.getCommandManager().addCommand(new MoveElementCommand(model, model.getSelected(), p4));
		}
		
		if (subState.equals("lasso") /*|| subState.equals("select")*/) {
			p1.setLocation(e.getPoint());
			model.lasoSelect((boolean)e.getSource(), p0, p1);
		}
		p0 = p1 = null;
		subState = "select";
		VGraphSlot_Dialog.changeCursor(Cursor.getDefaultCursor());
	}

	public Point getP0() {
		return p0;
	}

	public void setP0(Point p0) {
		this.p0 = p0;
	}

	public Point getP1() {
		return p1;
	}

	public void setP1(Point p1) {
		this.p1 = p1;
	}

	@Override
	public DefaultMutableTreeNode getModel() {
		return model;
	}

	@Override
	public void outOfState() {
		super.outOfState();
		ActionManager.getInstance().getcDeleteElements().setEnabled(false);
		model.setFocus();
	}

	@Override
	public void inState() {
		super.inState();
		ActionManager.getInstance().getcDeleteElements().setEnabled(true);
		model.setFocus();
	}
	
	
}
