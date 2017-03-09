package view.menus_bars;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JPanel;

import models.MWorkspace;
import utilities.Observer;
import utilities.translations.MultilanguageString;
import view.Settings.VChangeLanguage;

public class VStatusBar extends JPanel {
	JLabel label1;
	JLabel label2;
	String language = "English";
	String ll = "Language";

	public VStatusBar() {

		setLayout(new GridLayout(1, 2));
		label1 = new JLabel();
		label2 = new JLabel();
		init();
		this.add(label1);
		this.add(label2);

	}

	public void init() {
		label1.setText("Workspace");
		new MultilanguageString("radniprostor", label1);
		label2.setText(ll + ": " + language);
		new MultilanguageString("jezikstatus", label2);
	}

}
