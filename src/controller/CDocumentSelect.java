package controller;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.VDocument;
import view.VProject;
import view.VWorkspace;

public class CDocumentSelect implements ChangeListener {

	public CDocumentSelect() {
		super();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		VProject project = (VProject) VWorkspace.getInstance().getDesktop().getSelectedFrame();
		if (project == null)
			return;
		VDocument document = (VDocument) project.getDocumentTab().getSelectedComponent();
		document.getDocument().setFocus();
	}

}
