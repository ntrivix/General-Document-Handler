package view.menus_bars;

import javax.swing.JToolBar;

import controller.ActionManager;

public class VToolbar extends JToolBar {

	public VToolbar() {
		super("Toolbar");
		add(ActionManager.getInstance().getcAddProject());
		add(ActionManager.getInstance().getcAddDocument());
		add(ActionManager.getInstance().getcAddPage());
		
		add(ActionManager.getInstance().getcAddGraphSlot());
		add(ActionManager.getInstance().getcAddTextSlot());
		
		addSeparator();

		add(ActionManager.getInstance().getcSaveProject());
		add(ActionManager.getInstance().getcSaveAll());
		add(ActionManager.getInstance().getcProjectRemove());

		addSeparator();

		add(ActionManager.getInstance().getcCascadeAlignment());
		add(ActionManager.getInstance().getcHorizontalAlignment());
		add(ActionManager.getInstance().getcVerticalAlignment());
		add(ActionManager.getInstance().getcMatrixAlignment());

		addSeparator();

		add(ActionManager.getInstance().getcOpenSettingsDialog());

		add(ActionManager.getInstance().getcAbout());

		addSeparator();
	}

}
