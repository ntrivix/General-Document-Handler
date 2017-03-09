package controller;

import java.awt.event.ActionEvent;


import javax.swing.AbstractAction;


import utilities.GetResource;

public class CRemoveProjectFromWorkspace extends AbstractAction {


	//private static MultilanguageString removeProjFromWorkspace = new MultilanguageString("");
	private static String removeProjFromWorkspace = "remove project from workspace";
	public CRemoveProjectFromWorkspace() {
		super(removeProjFromWorkspace.toString(), GetResource.getIcon("icons/workspace.png"));
		putValue(SHORT_DESCRIPTION, removeProjFromWorkspace);
		putValue(LONG_DESCRIPTION, removeProjFromWorkspace);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
				
	}


}

