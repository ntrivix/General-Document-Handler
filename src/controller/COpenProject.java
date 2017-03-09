package controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import models.MProject;
import models.MWorkspace;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import utilities.translations.MultilanguageString;
import view.VWorkspace;
import view.Dialogs.VFileChooser;

public class COpenProject extends AbstractAction {

	private static MultilanguageString openProjectMessageS = new MultilanguageString("openProjectMessageS", null);

	public COpenProject() {
		super("", GetResource.getIcon("icons/project_open.png"));
		this.setEnabled(true);

		new AbstractActionTranslator(this, "openProjectS", "openProjectLong");

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl O"));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		VFileChooser jf = VFileChooser.getInstance();
		jf.setCurrentDirectory(new File(MWorkspace.getInstance().getWorkspaceLocation()));
		if (jf.showOpenDialog(VWorkspace.getInstance()) == JFileChooser.APPROVE_OPTION) {
			try {
				String name = jf.getSelectedFile().getName();
				String extension = name.substring(name.lastIndexOf('.') + 1, name.length());
				if (!extension.equals("pf")) {
					JOptionPane.showMessageDialog(null, openProjectMessageS, "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				ObjectInputStream stream = new ObjectInputStream(new FileInputStream(jf.getSelectedFile()));
				MProject project = null;
				try {
					project = (MProject) stream.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

				if (project instanceof MProject) {
					MProject p = (MProject) project;
					p.setpName(jf.getSelectedFile().getName());
					p.InitializeObservers();
					p.setChanged(false);
					MWorkspace.getInstance().addProject(p);
				}
				stream.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
