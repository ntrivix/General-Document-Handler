package controller.Alignment;

import java.awt.Dimension;
import java.awt.event.ActionEvent;


import javax.swing.AbstractAction;
import javax.swing.JInternalFrame;

import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import utilities.translations.MultilanguageString;
import view.VWorkspace;


public class CMatrixAlignment extends AbstractAction {

	public CMatrixAlignment() {
		super("", GetResource.getIcon("icons/matrix.png"));
		new AbstractActionTranslator(this, "matrixAlignmentS", "matrixAlignmentS");
		putValue(MNEMONIC_KEY, new Integer('M'));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JInternalFrame frames[] = VWorkspace.getInstance().getDesktop().getAllFrames();
		Dimension dim = VWorkspace.getInstance().getDesktop().getSize();

		int root = (int) Math.sqrt(frames.length);
		if (root * root < frames.length)
			root++;

		int h;
		if (frames.length > root * (root - 1))
			h = dim.height / root;
		else
			h = dim.height / (root - 1);
		int w = dim.width / root;

		int x = 0, y = 0;

		for (JInternalFrame frame : frames) {
			frame.setBounds(x * w, y * h, w, h);
			x++;
			if (x == root) {
				x = 0;
				y++;
			}
		}

		VWorkspace.getInstance().getDesktop().setAlignment(this);
	}
}
