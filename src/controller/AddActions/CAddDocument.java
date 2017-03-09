package controller.AddActions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;


import models.MDocument;
import models.MProject;
import models.MWorkspace;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import utilities.translations.MultilanguageString;



public class CAddDocument extends AbstractAction{

	private MultilanguageString newDocumentS;
	private MultilanguageString newDocumentLong;

	public CAddDocument() {
		
		super("", GetResource.getIcon("icons/document.png"));
		
		new AbstractActionTranslator(this, "newDocumentS", "newDocumentLong");
		
		this.setEnabled(false);
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultMutableTreeNode project = MWorkspace.getInstance().getActiveNode();
		if (project instanceof MProject) {
			MDocument newDoc = new MDocument("Doc " + project.getChildCount());
			newDoc.assingToProject((MProject) project);
		}
	}

}
