package controller.Alignment;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JInternalFrame;

import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import utilities.translations.MultilanguageString;
import view.VWorkspace;

public class CHorizontalAlignment extends AbstractAction {

	public CHorizontalAlignment() {
		super("", GetResource.getIcon("icons/horizontal.png"));

		new AbstractActionTranslator(this, "horizontalAlignmentS", "horizontalAlignmentS");

		putValue(MNEMONIC_KEY, new Integer('H'));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JInternalFrame frames[] = VWorkspace.getInstance().getDesktop().getAllFrames();
		Dimension dim = VWorkspace.getInstance().getDesktop().getSize();

		int h = dim.height / frames.length;
		int w = dim.width;

		int i = 0;

		for (JInternalFrame frame : frames) {
			frame.setBounds(0, i * h, w, h);
			i++;
		}

		VWorkspace.getInstance().getDesktop().setAlignment(this);
	}
}
