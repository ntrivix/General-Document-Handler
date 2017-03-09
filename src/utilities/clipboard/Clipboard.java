package utilities.clipboard;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import models.MElement;
import utilities.DeepObjectCopy;
import utilities.FileActions;

public class Clipboard {

	private int pasteCount = 0;
	private ArrayList<FileActions> elements = new ArrayList<>();
	private boolean cuted = false;
	private Point2D.Double translation;

	public ArrayList<FileActions> getElements() {
		return elements;
	}

	private double pasteToX = -1, pasteToY = -1, minx = Double.MAX_VALUE, miny = Double.MAX_VALUE;

	public void addToClipboard(FileActions element) {
		setCuted(false);
		translation = null;
		pasteCount = 0;
		this.elements = new ArrayList<>();
		this.elements.add(element);
		minx = Double.MAX_VALUE;
		miny = Double.MAX_VALUE;
		
		if (element instanceof MElement)
			checkMinPos((MElement) element);
	}
	
	public boolean isCuted() {
		return cuted;
	}

	public void addToClipboard(ArrayList<FileActions> elements) {
		setCuted(false);
		translation = null;
		pasteCount = 0;
		this.elements = (ArrayList<FileActions>) elements.clone();
		minx = Double.MAX_VALUE;
		miny = Double.MAX_VALUE;
		for (FileActions el : elements) {
			if (el instanceof MElement)
				checkMinPos((MElement) el);
		}
		
	}

	private void checkMinPos(MElement el) {

		double tx = ((MElement) el).getPosition().getX();
		double ty = ((MElement) el).getPosition().getY();
		if (tx < minx)
			minx = tx;
		if (ty < miny)
			miny = ty;
	}

	public void setPasteCount(int pasteCount) {
		this.pasteCount = pasteCount;
	}

	public void setCuted(boolean cuted) {
		this.cuted = cuted;
		boolean c = false;
		if (cuted) {
			c = true;
		}
		for (FileActions el : elements) {
			((MElement) el).setCutted(c);
		}
	}

	public ArrayList<FileActions> pasteAll(PasteAction pasteTO) {
		ArrayList<FileActions> newElements = new ArrayList<>();
		for (FileActions element : elements) {
			if (cuted) {
				element.delete();
			}
			MElement e = (MElement) DeepObjectCopy.copy(element);

			pasteTO.paste((FileActions) e);
			((MElement) element).setSelected(false);
			e.setSelected(true);
			newElements.add(e);
		}
		
		setCuted(false);
		pasteCount++;
		pasteToX = -1;
		pasteToY = -1;
		translation = null;
		
		return newElements;
	}

	public void setPasteLocation(double x, double y) {
		pasteToX = x;
		pasteToY = y;
	}

	public void clearClipboard() {
		setCuted(false);
		pasteCount = 0;
		this.elements = new ArrayList<>();
	}

	public int getPasteCount() {
		return pasteCount;
	}

	public void setTranslationVector(Point2D.Double point) {
		translation = point;
	}

	public Point2D.Double getTranslationVector() {
		if (translation != null)
			return translation;
		if (pasteToX < 0 && pasteToY < 0)
			return new Point2D.Double((pasteCount + 1) * 10, ((pasteCount + 1) * 10));

		return new Point2D.Double(pasteToX - minx, pasteToY - miny);
	}

}
