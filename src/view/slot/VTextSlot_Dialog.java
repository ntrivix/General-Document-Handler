package view.slot;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JTextArea;

import models.MTextSlot;
import utilities.translations.MultilanguageString;
import view.VWorkspace;

public class VTextSlot_Dialog extends JDialog {
	private JTextArea area;
	private MTextSlot model;
	private VSlot view;

	public VTextSlot_Dialog(MTextSlot model, VTextSlot view) {
		super(VWorkspace.getInstance(), true);
		
		this.model = model;
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Text slot");
		area = new JTextArea(model.getText());
		setContentPane(area);
		
		new MultilanguageString("textslot", this) {
			protected void updateOvner() {
				((JDialog) ovner).setTitle(this.toString());
			}
		};
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				model.setText(area.getText());
			}

			@Override
			public void windowActivated(WindowEvent e) {
				
			}
		});
		setVisible(true);
	}
}
