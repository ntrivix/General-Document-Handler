package controller.Alignment;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JInternalFrame;

import utilities.translations.AbstractActionTranslator;
import utilities.translations.MultilanguageString;
import utilities.GetResource;
import view.VWorkspace;


public class CVerticalAlignment extends AbstractAction {
	
	public CVerticalAlignment() {
		super("",GetResource.getIcon("icons/vertical.png"));
		new AbstractActionTranslator(this, "verticalAlignmentS", "verticalAlignmentS");
		putValue(MNEMONIC_KEY, new Integer('V'));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JInternalFrame frames[] = VWorkspace.getInstance().getDesktop().getAllFrames();
		Dimension dim = VWorkspace.getInstance().getDesktop().getSize();
		
		int h = dim.height;
		int w = dim.width / frames.length;
		
		int i = 0;
		
		for (JInternalFrame frame: frames) {
			frame.setBounds(i*w, 0, w, h);
			i++;
		}
		
		VWorkspace.getInstance().getDesktop().setAlignment(this);
	}	
}
