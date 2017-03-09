package controller;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreePath;

import utilities.Focusable;
import view.VWorkspace;
import view.menus_bars.VPopupMenu;

public class CPopupMenu extends MouseAdapter {

	@Override
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		if (SwingUtilities.isRightMouseButton(e)) {
			TreePath path = VWorkspace.getInstance().getvTree().getPathForLocation(e.getX(), e.getY());
			Rectangle pathBounds = VWorkspace.getInstance().getvTree().getUI()
					.getPathBounds(VWorkspace.getInstance().getvTree(), path);
			if (pathBounds != null && pathBounds.contains(e.getX(), e.getY())) {
				if (path.getLastPathComponent() instanceof Focusable)
					((Focusable) path.getLastPathComponent()).setFocus();
				VPopupMenu menu = VPopupMenu.getInstance();
				menu.show((JComponent) e.getSource(), e.getX() - 25, e.getY());
			}
		}
	}

}
