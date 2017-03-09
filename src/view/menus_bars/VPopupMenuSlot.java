package view.menus_bars;

import javax.swing.JPopupMenu;

import controller.ActionManager;

public class VPopupMenuSlot extends JPopupMenu {
	private static VPopupMenuSlot instance;

	private VPopupMenuSlot() {
		super();
		
		this.add(ActionManager.getInstance().getcCutElement());
		this.add(ActionManager.getInstance().getcCopyElements());
		this.add(ActionManager.getInstance().getcPasteElement());
		
		this.addSeparator();

		this.add(ActionManager.getInstance().getcDeleteElements());
	}

	public static VPopupMenuSlot getInstance() {
		if (instance == null)
			instance = new VPopupMenuSlot();
		return instance;
	}
}
