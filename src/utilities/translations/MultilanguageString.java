package utilities.translations;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;

public class MultilanguageString {

	private static ArrayList<MultilanguageString> allMStrings = new ArrayList<>();

	private String str;
	private String resourceString;
	protected Object ovner;

	public MultilanguageString(String resourceString, Object ovner) {
		this.ovner = ovner;
		this.resourceString = resourceString;
		this.update();
		allMStrings.add(this);
	}

	private void update() {
		try {
			str = getResourceBundle().getString(resourceString);
		} catch (Exception e) {
			return;
		}
		updateOvner();
	}

	public String getStrign() {
		return str;
	}

	@Override
	public String toString() {
		return str;
	}

	protected void updateOvner(){
		if (ovner instanceof JMenu){
			((JMenu) ovner).setName(str);
			((JMenu) ovner).setText(str);
		} else
			if (ovner instanceof JFrame){
				((JFrame)ovner).setTitle(str);
			} else 
				if (ovner instanceof JLabel){
					((JLabel)ovner).setText(str);
				}
	};

	public static void changeLanguage(Locale locale) {
		updateAll();
	}

	private static void updateAll() {
		for (MultilanguageString multilanguageString : allMStrings) {
			multilanguageString.update();
		}
	}

	public static ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle("view.Settings.MessageResources.MessageResources", Locale.getDefault());
	}

}
