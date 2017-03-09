package view.Settings;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Observer;


import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;

import javax.swing.JPanel;

import controller.ActionManager;


public class VChangeLanguage extends JPanel {
	
	private class MenuItem extends JCheckBoxMenuItem{
	  	
	  public MenuItem(String text) {
			super(text);
		}

	@Override
	  public String toString(){
		return this.getName();  
	  }
	}
	
	private static VChangeLanguage instance;
	
	private MenuItem jezikSrpski;
	private MenuItem jezikEngleski;
	private MenuItem jezikSrpskiCirilica;

	private VChangeLanguage() {

		jezikSrpski = new MenuItem("Srpski");
		jezikSrpski.setName("Srpski");
		jezikSrpski.setLocale(new Locale("sr", "RS", "#Latn"));

		jezikSrpskiCirilica = new MenuItem("Srpski (Cyrillic)");
		jezikSrpskiCirilica.setName("Srpski (Cyrillic)");
		jezikSrpskiCirilica.setLocale(new Locale("sr"));

		jezikEngleski = new MenuItem("English");
		jezikEngleski.setName("English");
		jezikEngleski.setLocale(new Locale("en", "GB"));

		JComboBox<JCheckBoxMenuItem> bg = new JComboBox<JCheckBoxMenuItem>();
		bg.addItem((JCheckBoxMenuItem) jezikEngleski);
		bg.addItem((JCheckBoxMenuItem) jezikSrpski);
		bg.addItem((JCheckBoxMenuItem) jezikSrpskiCirilica);
		
		bg.addItemListener(ActionManager.getInstance().getcChangeLanguage());

		this.add(bg);
	}

	public static VChangeLanguage getInstance() {
		if (instance == null)
			instance = new VChangeLanguage();
		return instance;
	}

}
