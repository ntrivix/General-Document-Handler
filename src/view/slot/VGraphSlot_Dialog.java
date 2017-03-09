package view.slot;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import command.CommandManager;
import controller.ActionManager;
import models.MElement;
import models.MGraphSlot;
import models.MWorkspace;
import states.SelectState;
import utilities.translations.MultilanguageString;
import view.VWorkspace;

public class VGraphSlot_Dialog extends JDialog {
	public PaintArea area = new PaintArea();
	private ArrayList<Integer> x = new ArrayList<>();
	private static MGraphSlot model;
	private VSlot view;
	private static Cursor cursor;
	public static double mouseClickX, mouseClickY;

	boolean ctrlDown = false;

	public static VGraphSlot_Dialog instance = null;

	public static VGraphSlot_Dialog getInstance(MGraphSlot model, VGraphSlot view) {
		new VGraphSlot_Dialog(model, view);

		return instance;
	}

	public static void changeCursor(Cursor c) {
		cursor = c;
	}

	public void updateTree() {
		int cnt = 0;
		for (int i = 0; i < model.getElement().size(); i++) {
			MElement element = model.getElements().get(i);
			MWorkspace.getInstance().getTreeSelectionModel().removeSelectionPath(new TreePath(element.getPath()));
		}
		for (int i = 0; i < model.getElements().size(); i++) {
			MElement element = model.getElements().get(i);
			if (element.isSelected()) {
				MWorkspace.getInstance().getTreeSelectionModel().addSelectionPath(new TreePath(element.getPath()));
				cnt++;
			}
		}
		VWorkspace.getInstance().getvTree().updateUI();
		VWorkspace.getInstance().getvTree().repaint();

	}

	private VGraphSlot_Dialog(MGraphSlot model, VGraphSlot view) {
		super(VWorkspace.getInstance(), true);

		VGraphSlot_Dialog.instance = this;

		view.setgDialog(this);

		VGraphSlot_Dialog.model = model;
		this.view = view;

		setSize(500, 650);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Graph slot");

		add(area, BorderLayout.CENTER);

		JToolBar toolbar = new JToolBar();
		toolbar.setOrientation(JToolBar.VERTICAL);
		toolbar.add(ActionManager.getInstance().getcSelectState());
		toolbar.add(ActionManager.getInstance().getcCircleState());
		toolbar.add(ActionManager.getInstance().getcSquareState());
		toolbar.add(ActionManager.getInstance().getcTriangleState());
		toolbar.addSeparator();
		toolbar.add(ActionManager.getInstance().getcUndoCommand());
		toolbar.add(ActionManager.getInstance().getcRedoCommand());
		toolbar.addSeparator();
		toolbar.add(ActionManager.getInstance().getcDeleteElements());
		toolbar.add(ActionManager.getInstance().getcCutElement());
		toolbar.add(ActionManager.getInstance().getcCopyElements());
		toolbar.add(ActionManager.getInstance().getcPasteElement());
		toolbar.addSeparator();
		toolbar.add(ActionManager.getInstance().getcRotateLeft());
		toolbar.add(ActionManager.getInstance().getcRotateRight());

		new MultilanguageString("editmod", this) {
			protected void updateOvner() {
				((JDialog) ovner).setTitle(this.toString());
			}
		};

		add(toolbar, BorderLayout.EAST);

		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				MWorkspace.getInstance().getTreeSelectionModel()
						.setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
			}

			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {}

			@Override
			public void windowClosed(WindowEvent e) {		
				model.deselectAll();
				view.repaint();
				MWorkspace.getInstance().getTreeSelectionModel()
						.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
				updateTree();
			}

			@Override
			public void windowActivated(WindowEvent e) {
				
			}
		});
		
		setFocusable(true);
		requestFocus();
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				ctrlDown = e.isControlDown();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				ctrlDown = e.isControlDown();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				ctrlDown = e.isControlDown();
			}
		});

		setVisible(true);
	}

	public static MGraphSlot getModel() {
		return model;
	}

	private class PaintArea extends JPanel {
		PaintArea() {
			setBackground(Color.WHITE);
			setFocusable(true);
			addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseDragged(MouseEvent e) {
					e.setSource(ctrlDown);
					model.getStateManager().getCurrentState().mouseDragged(e);
					area.repaint();
				}
			});
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					mouseClickY = e.getY();
					mouseClickX = e.getX();
					e.setSource(ctrlDown);
					model.getStateManager().getCurrentState().mouseReleased(e);
					area.repaint();
				}

				@Override
				public void mousePressed(MouseEvent e) {
					e.setSource(ctrlDown);
					model.getStateManager().getCurrentState().mousePressed(e);
					area.repaint();
				}
			});

		}

		@Override
		protected void paintComponent(Graphics gr) {
			setCursor(cursor);
			VGraphSlot_Dialog.this.requestFocus();
			super.paintComponent(gr);
			Graphics2D g = (Graphics2D) gr;
			for (int i = 0; i < model.getElements().size(); i++) {
				MElement element = model.getElements().get(i);
				element.getElementPainter().paint(g, element);
				element.getElementBorderPainter().paint(g, element);

			}
			updateTree();

			if (model.getStateManager().getCurrentState() instanceof SelectState) {
				SelectState selSt = (SelectState) model.getStateManager().getCurrentState();
				Point p0 = selSt.getP0();
				Point p1 = selSt.getP1();

				if (p0 == null || p1 == null)
					return;
				Rectangle2D rect;

				Point pmin = new Point(Math.min(p0.x, p1.x), Math.min(p0.y, p1.y));
				Point pmax = new Point(Math.max(p0.x, p1.x), Math.max(p0.y, p1.y));
				int width = pmax.x - pmin.x;
				int height = pmax.y - pmin.y;

				Shape lasoRect = new Rectangle2D.Double(pmin.getX(), pmin.getY(), width, height);
				g.setPaint(Color.BLUE);

				g.setStroke(new BasicStroke(1));
				g.draw(lasoRect);
				g.setPaint(new Color(0f, 0f, 1f, 0.2f));
				g.fill(lasoRect);
			}
		}

	}

	@Override
	public String toString() {
		return "VGraphSlot_Dialog []";
	}

}
