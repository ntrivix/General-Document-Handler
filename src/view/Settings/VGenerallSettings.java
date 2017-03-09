package view.Settings;

import javax.swing.JDialog;

public class VGenerallSettings extends JDialog {
	private static VGenerallSettings instance;

	private VGenerallSettings() {
		super();
		add(VChangeTheme.getInstance());
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		setModalityType(ModalityType.APPLICATION_MODAL);
	}

	public static VGenerallSettings getInstance() {
		if (instance == null) {
			instance = new VGenerallSettings();
		}
		instance.setVisible(true);
		return instance;
	}

}
