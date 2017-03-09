package controller.Alignment;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JInternalFrame;

import utilities.GetResource;
import utilities.translations.AbstractActionTranslator;
import utilities.translations.MultilanguageString;
import view.VProject;
import view.VWorkspace;

public class CCascadeAlignment extends AbstractAction {

	public CCascadeAlignment() {
		super("", GetResource.getIcon("icons/cascade.png"));
		new AbstractActionTranslator(this, "cascadeAlignmentS", "cascadeAlignmentS");
		putValue(MNEMONIC_KEY, new Integer('C'));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JInternalFrame frames[] = VWorkspace.getInstance().getDesktop().getAllFrames();
		Dimension dim = VWorkspace.getInstance().getDesktop().getSize();

		int h = VProject.HEIGHT;
		int w = VProject.WIDTH;

		int x = 0;
		int y = 0;
		boolean down = true;

		for (int i = frames.length - 1; i >= 0; i--) {
			JInternalFrame frame = frames[i];

			frame.setBounds(x, y, w, h);
			if (down) {
				x += VProject.offsetX;
				y += VProject.offsetY;

				if (x > dim.height - 5 || y > dim.width - 5) {
					x -= 2 * VProject.offsetX;
					y -= 2 * VProject.offsetY;
					down = false;
				}
			} else {
				x -= VProject.offsetX;
				y -= VProject.offsetY;

				if (x < 0 || y < 0) {
					x += 2 * VProject.offsetX;
					y += 2 * VProject.offsetY;
					down = true;
				}
			}
		}

		VWorkspace.getInstance().getDesktop().setAlignment(this);
	}
}
