
package controller.AddActions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;

import models.MGraphSlot;
import models.MPage;
import models.MWorkspace;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;

public class CAddGraphSlot extends AbstractAction {

	public CAddGraphSlot() {
		super("", GetResource.getIcon("icons/Ruller.png"));
		this.setEnabled(false);

		new AbstractActionTranslator(this, "newGraphSlotS", "newGraphSlotLong");

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl L"));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultMutableTreeNode page = MWorkspace.getInstance().getActiveNode();
		if (page instanceof MPage) {
			MGraphSlot newSlot = new MGraphSlot("Slot " + page.getChildCount());
			newSlot.assignToPage((MPage) page);
		}
	}

}
