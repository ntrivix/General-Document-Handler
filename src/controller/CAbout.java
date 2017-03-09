package controller;

import java.awt.event.ActionEvent;


import javax.swing.AbstractAction;

import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import utilities.translations.MultilanguageString;
import view.Dialogs.VAboutDialog;


public class CAbout extends AbstractAction {

	public CAbout() {
		super("", GetResource.getIcon("icons/about.png"));
		new AbstractActionTranslator(this, "aboutS", "aboutS");
		putValue(MNEMONIC_KEY, new Integer('A'));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		new VAboutDialog();
	}
}
