package controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import command.DeleteElementCommand;
import models.MElement;
import models.MGraphSlot;
import models.MProject;
import models.MWorkspace;
import utilities.FileActions;
import utilities.Focusable;
import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import utilities.translations.MultilanguageString;
import view.VWorkspace;

public class CRemoveObject extends AbstractAction {

	public CRemoveObject() {
		super("", GetResource.getIcon("icons/delete.png"));

		new AbstractActionTranslator(this, "deleteS", "deleteS");
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl DELETE"));
		this.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		// Ovde uzmi podatak o aktivnom nodu iz modela
		boolean rerange = false;
		TreePath[] path = MWorkspace.getInstance().getTreeSelectionModel().getSelectionPaths();
		try {
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path[path.length - 1].getLastPathComponent();
			if (selectedNode instanceof MProject && ((MProject) selectedNode).getFile() != null) {

				String message1 = "Are you sure you want to delete this project?";
				String message2 = "Warning";

				int result = JOptionPane.showConfirmDialog(null, message1, message2, JOptionPane.YES_NO_OPTION);
				if (result != JOptionPane.YES_OPTION)
					return;

				rerange = true;
			}
			if (selectedNode instanceof FileActions) {
				DefaultMutableTreeNode pareNtnode = (DefaultMutableTreeNode) selectedNode.getParent();
				if (pareNtnode instanceof Focusable) {
					((Focusable) pareNtnode).setFocus();
				} else {
					MWorkspace.getInstance().setActiveNode(null);
				}
				if (selectedNode instanceof MElement){
					ArrayList<MElement> elements = new ArrayList<>();
					elements.add((MElement) selectedNode);
					((MGraphSlot) selectedNode.getParent()).getCommandManager().addCommand(new DeleteElementCommand((MGraphSlot) selectedNode.getParent(), elements));
				} else
				((FileActions) selectedNode).delete();
			}
		} catch (Exception e) {
			System.out.println("remove exception");

		}
		if (rerange)
			VWorkspace.getInstance().getDesktop().rearange();
	}
	
}
