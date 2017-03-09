package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import models.MProject;
import models.MWorkspace;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import utilities.translations.MultilanguageString;
import view.VWorkspace;
import view.Dialogs.VWorkspaceChooser;

public class CChangeWorkspace extends AbstractAction {

	private static MultilanguageString message1 = new MultilanguageString("SaveNow",null);
	private static MultilanguageString message2 = new MultilanguageString("Warning", null);

	public CChangeWorkspace() {
		super("", GetResource.getIcon("icons/workspace.png"));
		new AbstractActionTranslator(this, "changeWorkspaceS", "changeWorkspaceS");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean bool = false;
		for (MProject p : MWorkspace.getInstance().getProjects()) {
			if (p.isChanged()) {
				bool = true;
				break;
			}
		}

		if (bool) {
			int button = JOptionPane.YES_NO_OPTION;
			int result = JOptionPane.showConfirmDialog(null, message1.toString(), message2.toString(), button);
			if (result == JOptionPane.YES_OPTION) {
				ActionManager.getInstance().getcSaveAll().actionPerformed(null);
			}
		}

		VWorkspaceChooser fc = VWorkspaceChooser.getInstance();
		if (fc.showOpenDialog(VWorkspace.getInstance()) == JFileChooser.APPROVE_OPTION) {

			MWorkspace.changeWorkspace(fc.getSelectedFile().getAbsolutePath());

			//setuj temu na poslednju sacuvanu u workspaceu
			try {
				UIManager.setLookAndFeel(MWorkspace.getInstance().getWorkspaceFile().getDefaultTheme());
				SwingUtilities.updateComponentTreeUI(VWorkspace.getInstance());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
