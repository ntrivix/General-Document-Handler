
package controller.AddActions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;


import models.MPage;
import models.MTextSlot;
import models.MWorkspace;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import utilities.translations.MultilanguageString;

public class CAddTextSlot extends AbstractAction{

	public CAddTextSlot() {
		super("", GetResource.getIcon("icons/Pen.png"));
		this.setEnabled(false);
		new AbstractActionTranslator(this, "newTextSlotS", "newTextSlotLong");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl L"));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultMutableTreeNode page = MWorkspace.getInstance().getActiveNode();
		if (page instanceof MPage) {
			MTextSlot newSlot = new MTextSlot("Slot " + page.getChildCount());
			newSlot.assignToPage((MPage) page);

		}
	}
}
