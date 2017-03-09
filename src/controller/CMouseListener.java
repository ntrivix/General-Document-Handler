package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import models.MSlot;
import models.MWorkspace;
import utilities.Focusable;
import view.menus_bars.VPopupMenu;
import view.slot.VSlot;

public class CMouseListener extends MouseAdapter {

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		if (SwingUtilities.isRightMouseButton(e)) {
			VPopupMenu menu = VPopupMenu.getInstance();
			menu.show((JComponent) e.getSource(), e.getX() - 25, e.getY());
		}
		if (MWorkspace.getInstance().getActiveNode() instanceof Focusable) {
			((Focusable) MWorkspace.getInstance().getActiveNode()).unfocus();
		}
		if (e.getComponent() instanceof Focusable) {
			((Focusable) e.getComponent()).setFocus();
			if (e.getComponent() instanceof VSlot) {
				if (e.getClickCount() == 2 && !e.isConsumed()) {
					e.consume();
					ActionManager.getInstance().getcEditMode().actionPerformed(null);
				}
			}
		}

	}
}
