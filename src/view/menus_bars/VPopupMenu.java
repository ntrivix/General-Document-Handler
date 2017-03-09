package view.menus_bars;

import javax.swing.JPopupMenu;

import controller.ActionManager;

public class VPopupMenu extends JPopupMenu {
	private static VPopupMenu instance;

	private VPopupMenu() {
		super();
		this.add(ActionManager.getInstance().getcAddProject());
		this.add(ActionManager.getInstance().getcAddDocument());
		this.add(ActionManager.getInstance().getcAddPage());
		
		this.add(ActionManager.getInstance().getcAddGraphSlot());
		this.add(ActionManager.getInstance().getcAddTextSlot());

		this.addSeparator();

		this.add(ActionManager.getInstance().getcEditMode());
		this.addSeparator();

		this.add(ActionManager.getInstance().getcProjectRemove());
	}

	public static VPopupMenu getInstance() {
		if (instance == null)
			instance = new VPopupMenu();
		return instance;
	}

}
