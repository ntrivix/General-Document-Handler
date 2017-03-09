package view.slot;

import java.awt.Graphics;

import javax.swing.JFrame;

import models.MSlot;
import models.MTextSlot;

public class VTextSlot extends VSlot {
	private MTextSlot slot;

	public VTextSlot(MTextSlot slot) {
		super(slot);
		this.slot = slot;
	}

	@Override
	public void update(Object observable, Object arg, String command) {
		
		super.update(observable, arg, command);
		if (command.equals("EditMode")) {
			new VTextSlot_Dialog((MTextSlot) slot, this);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x = 10, y = 10;
		String text = slot.getText();
		for (String line : text.split("\n"))
			g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}


}
