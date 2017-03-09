package view.slot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.ActionManager;
import models.MElement;
import models.MGraphSlot;
import models.MSlot;
import models.MTextSlot;
import utilities.Focusable;
import utilities.Observer;

public abstract class VSlot extends JPanel implements Observer, Focusable {
	protected MSlot slot;
	private boolean focused;
	private float heightScale = (float) 1.3;

	public VSlot(MSlot slot) {
		super();
		this.slot = slot;
		focused = false;
		setLayout(new BorderLayout());

		slot.addObserver(this);
		addMouseListener(ActionManager.getInstance().getcMouseListener());
	}

	@Override
	public void setFocus() {
		slot.setFocus();
		focused = true;
	}

	@Override
	public void unfocus() {
		focused = false;
		repaint();
		updateUI();
	}

	@Override
	public void update(Object observable, Object arg, String command) {
		if (arg instanceof MSlot) {
			if (command.equals("focus")) {
				focused = true;
			}
			if (command.equals("unfocus")) {
				unfocus();
			}
			if (command.equals("del")) {
				this.getParent().remove(this);
				slot = null;
			}
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.decode("#e3e8ef"));
		if (focused) {
			this.setBorder(BorderFactory.createLineBorder(Color.decode("#3961c6")));
		} else
			this.setBorder(null);
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(0, (int) (this.getWidth() * heightScale));
	}

}
