package controller.settings;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import utilities.translations.MultilanguageString;
import view.Settings.VGenerallSettings;
//13213

public class COpenSettingsDialog extends AbstractAction {
	

	public COpenSettingsDialog() {
		super("", GetResource.getIcon("icons/Settings.png"));
		new AbstractActionTranslator(this, "settingsS", "settingsLong");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		VGenerallSettings.getInstance();
	}
}
