package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import controller.ActionManager;
import models.MGraphSlot;
import models.MPage;
import models.MSlot;
import models.MTextSlot;
import net.miginfocom.swing.MigLayout;
import utilities.Focusable;
import utilities.Observer;
import view.slot.VGraphSlot;
import view.slot.VSlot;
import view.slot.VTextSlot;

public class VPage extends JPanel implements Observer, Focusable {

	private MPage page;
	private MigLayout layout;
	private boolean focused;

	private float heightScale = (float) 1.3;

	public VPage(MPage page) {
		super();
		this.page = page;
		this.layout = new MigLayout("wrap 3", "[grow,fill]");

		this.setLayout(layout);
		this.setVisible(true);
		page.addObserver(this);
		focused = false;
		addMouseListener(ActionManager.getInstance().getcMouseListener());
		for (MSlot s : page.getSlots()) {
			if (s instanceof MGraphSlot) {
				VGraphSlot slot = new VGraphSlot(s);
				this.add(slot);

			} else {
				VTextSlot slot = new VTextSlot((MTextSlot) s);
				this.add(slot);
			}
			updateUI();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		if (focused) {
			this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		} else
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	public void update(Object observable, Object arg, String command) {
		if (arg instanceof MPage) {
			if (command.equals("focus")) {
				focused = true;
				scrollToPage();
			}
			if (command.equals("Rename")) {
				this.setName(page.toString());
				updateUI();
			}
			if (command.equals("unfocus")) {
				focused = false;
				repaint();
			}
			if (command.equals("del")) {
				this.getParent().remove(this);
				page = null;
			}
		} else if (arg instanceof MSlot && command.equals("newGraphSlot")) {
			VSlot slot = new VGraphSlot((MSlot) arg);
			this.add(slot);
			((MSlot) arg).setFocus();
			validate();
			slot.updateUI();
		} else if (arg instanceof MSlot && command.equals("newTextSlot")) {
			VSlot slot = new VTextSlot((MTextSlot) arg);
			this.add(slot);
			((MSlot) arg).setFocus();
			validate();
			slot.updateUI();
		}
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(0, (int) (this.getWidth() * heightScale));
	}

	@Override
	public void setFocus() {
		page.setFocus();
		focused = true;
	}

	@Override
	public void unfocus() {
		focused = false;
		repaint();
	}

	public void scrollToPage() {
		JScrollPane jsp = (JScrollPane) (this.getParent().getParent().getParent());
		jsp.getVerticalScrollBar().setValue(this.getY());
	}

}
