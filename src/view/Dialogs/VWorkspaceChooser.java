package view.Dialogs;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class VWorkspaceChooser extends JFileChooser {
	private static VWorkspaceChooser instance;

	private VWorkspaceChooser() {
		setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	}

	public static VWorkspaceChooser getInstance() {
		if (instance == null) {
			instance = new VWorkspaceChooser();
		}
		return instance;
	}
}
