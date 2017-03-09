package view.Dialogs;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class VFileChooser extends JFileChooser {

	private static VFileChooser instance;

	private class CProjectFileFilter extends FileFilter {

		@Override
		public boolean accept(File arg) {
			return (arg.isDirectory() || arg.getName().toLowerCase().endsWith(".pf"));
		}

		@Override
		public String getDescription() {
			return "Project Files (.pf)";
		}

	}

	private VFileChooser() {
		CProjectFileFilter p = new CProjectFileFilter();
		this.setFileFilter(p);
	}

	public static VFileChooser getInstance() {
		if (instance == null) {
			instance = new VFileChooser();
		}
		return instance;
	}
}
