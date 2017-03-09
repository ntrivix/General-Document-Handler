package models;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.tree.TreePath;

import command.CommandManager;
import states.StateManager;
import utilities.FileActions;
import utilities.clipboard.Clipboard;

public class MGraphSlot extends MSlot {

	private ArrayList<MElement> elements;
	private transient CommandManager commandManager;
	private transient StateManager stateManager;

	public MGraphSlot(String name) {
		super(name);
		elements = new ArrayList<>();
		commandManager = new CommandManager();
		stateManager = new StateManager(this);
		MWorkspace.getInstance().setmGraphSlot(this);
	}

	public CommandManager getCommandManager() {
		return commandManager;
	}

	@Override
	public void InitializeObservers() {
		super.InitializeObservers();
		this.stateManager = new StateManager(this);
		this.commandManager = new CommandManager();
		for (MElement el : this.elements) {
			el.InitializeObservers();
		}
	}

	public void addElement(MElement element) {
		elements.add(element);
		MWorkspace.getInstance().insertNodeInto(element, this, this.getChildCount());
		MWorkspace.getInstance().getTreeSelectionModel().setSelectionPath(new TreePath(element.getPath()));
		element.setSlot(this);
		this.setChanged(true);
		notifyObservers(element, "newElement");
		this.setFocus();
	}

	public ArrayList<MElement> getElements() {
		return elements;
	}

	public MElement getElementAt(Point p) {
		for (int i = elements.size() - 1; i >= 0; i--) {
			MElement el = elements.get(i);
			boolean handleSelected = false;
			for (int ii = 0; ii < 3; ii++) {
				for (int j = 0; j < 3; j++) {
					if (ii == 1 && j == 1)
						continue;
					if (el.getHandles().mouseAtHandle(p, ii, j))
						handleSelected = true;
				}
			}
			if (el.hasPoint(p) || handleSelected)
				return el;
		}

		return null;
	}

	public ArrayList<MElement> getSelected() {
		ArrayList<MElement> selected = new ArrayList<>();
		for (int i = elements.size() - 1; i >= 0; i--) {
			MElement elm = elements.get(i);
			if (elm.isSelected()) {
				selected.add(elm);
			}
		}
		return selected;
	}

	public void removeSelected() {
		for (int i = elements.size() - 1; i >= 0; i--) {
			MElement elm = elements.get(i);
			if (elm.isSelected()) {
				elements.remove(i);
				MWorkspace.getInstance().removeNodeFromParent(elm);
			}
		}
		notifyObservers(this, "deleted");
	}

	public void deselectAll() {
		for (MElement elm : elements) {
			elm.setSelected(false);
		}
		notifyObservers(this, "deselect");
	}

	public void prepareLasoSelect() {
		for (MElement elm : elements) {
			elm.setSelectedOld(elm.isSelected());
		}
	}

	public void lasoSelect(boolean ctrlDown, Point p0, Point p1) {
		this.setFocus();
		if (!ctrlDown) {
			for (MElement elm : elements) {
				elm.setSelected(false);
				elm.setSelectedOld(false);
			}
		}

		if (p0.equals(p1)) {
			for (int i = elements.size() - 1; i >= 0; i--) {
				MElement elm = elements.get(i);
				if (elm.hasPoint(p0)) {
					elm.setSelected(!elm.isSelected());
					return;
				}
			}
			return;
		}

		Point pmin = new Point(Math.min(p0.x, p1.x), Math.min(p0.y, p1.y));
		Point pmax = new Point(Math.max(p0.x, p1.x), Math.max(p0.y, p1.y));
		int width = pmax.x - pmin.x;
		int height = pmax.y - pmin.y;

		Rectangle2D lasoRect = new Rectangle2D.Double(pmin.getX(), pmin.getY(), width, height);

		for (MElement elm : elements) {
			if (elm.intersect(lasoRect)) {
				elm.setSelected(!elm.isSelectedOld());
			}
		}
	}

	public void startCircleState() {
		stateManager.setState(stateManager.getCircleState());
	}

	public void startSelectState() {
		stateManager.setState(stateManager.getSelectState());
	}

	public void startSquareState() {
		stateManager.setState(stateManager.getSquareState());
	}

	public void startTriangleState() {
		stateManager.setState(stateManager.getTriangleState());
	}

	public StateManager getStateManager() {
		return stateManager;
	}

	@Override
	public ArrayList<MElement> getElement() {
		return this.elements;
	}

	@Override
	public void paste(FileActions object) {

		Clipboard clipboard = this.getPage().getDocuments().get(0).getClipboard();
		float offsetx = (float) clipboard.getTranslationVector().getX();
		float offsety = (float) clipboard.getTranslationVector().getY();

		if (object instanceof MElement) {
			MElement el = (MElement) object;
			el.setCutted(false);
			el.translate(offsetx, offsety);
			addElement(el);
		}
		this.setFocus();
	}

}
