package controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;

import models.MWorkspace;
import utilities.translations.MultilanguageString;

public class CChangeLanguage implements ItemListener{
			@Override
			public void itemStateChanged(ItemEvent e) {
				Locale newLocale = ((JCheckBoxMenuItem)((JComboBox)e.getSource()).getModel().getSelectedItem()).getLocale();
				Locale.setDefault(newLocale);	
				ResourceBundle.getBundle("view.Settings.MessageResources.MessageResources", Locale.getDefault());
				MWorkspace.getInstance().getWorkspaceFile().setDefaultLanguage(((JCheckBoxMenuItem)((JComboBox)e.getSource()).getModel().getSelectedItem()).toString());
				MultilanguageString.changeLanguage(newLocale);
			}
}


