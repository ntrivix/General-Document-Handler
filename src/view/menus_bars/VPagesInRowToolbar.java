package view.menus_bars;

import javax.swing.JToolBar;

import controller.ActionManager;
import controller.CRearrangePages;
import models.MDocument;

public class VPagesInRowToolbar extends JToolBar {

	public VPagesInRowToolbar(MDocument mDocument) {
		super();
		for (int i = 0; i < 10; i++)
			this.add(ActionManager.getInstance().getcRearrangePages(mDocument));
		CRearrangePages.resetCounter();
		setFloatable(false);
	}

}
