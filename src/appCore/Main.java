package appCore;
import java.util.Locale;

import javax.swing.SwingUtilities;

import models.MWorkspace;
import view.VWorkspace;

public class Main {
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "GB"));
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MWorkspace.getInstance();
				VWorkspace.getInstance();
			}
		});
	}
}
