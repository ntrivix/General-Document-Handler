package models;

import java.util.ArrayList;

public class MTextSlot extends MSlot {
	private String text = "";

	public MTextSlot(String name) {
		super(name);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.setChanged(true);
		this.text = text;
	}

	@Override
	public void addElement(MElement element) {

	}

	@Override
	public ArrayList<MElement> getElement() {
		return null;
	}
}
