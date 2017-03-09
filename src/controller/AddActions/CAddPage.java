package controller.AddActions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;

import models.MDocument;
import models.MPage;
import models.MWorkspace;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;

public class CAddPage extends AbstractAction {

	public CAddPage() {
		super("", GetResource.getIcon("icons/page.png"));
		this.setEnabled(false);

		new AbstractActionTranslator(this, "newPageS", "newPageLong");

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl G"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultMutableTreeNode document = MWorkspace.getInstance().getActiveNode();
		if (document instanceof MDocument) {
			MPage newPage = new MPage("Page " + document.getChildCount());
			newPage.assignToDocument((MDocument) document);
		}
	}
}
