package view.Settings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import models.MWorkspace;
import utilities.translations.MultilanguageString;
import view.VWorkspace;
import view.menus_bars.VPopupMenu;

import com.alee.laf.WebLookAndFeel;

public class VChangeTheme extends JPanel {
	
	private static VChangeTheme instance;
	private MultilanguageString chooseThemeS;
	private MultilanguageString chooseLanguageS; 
	
	private JLabel text = new JLabel();
	private JLabel text2 = new JLabel();

	private VChangeTheme() {
		super();
		chooseThemeS = new MultilanguageString("chooseThemeS",text){
			protected void updateOvner() {
				((JLabel) ovner).setText(this.toString());
			}
		};
		chooseLanguageS = new MultilanguageString("chooseLanguageS",text2){
			protected void updateOvner() {
				((JLabel) ovner).setText(this.toString());
			}
		}; 
		add(text);
		JComboBox<String> themesComboBox = new JComboBox<>();
		
		UIManager.LookAndFeelInfo[] themes=UIManager.getInstalledLookAndFeels();
		for (int i=0;i<themes.length;i++){
			themesComboBox.addItem(themes[i].getClassName());
			if (UIManager.getLookAndFeel().getName().equals(themes[i].getName())){
				themesComboBox.setSelectedIndex(i);
			}
		}
		
		themesComboBox.addItem(WebLookAndFeel.class.getCanonicalName());
		if (UIManager.getLookAndFeel().getName().equals(WebLookAndFeel.class.getName())){
			themesComboBox.setSelectedIndex(themesComboBox.getItemCount()-1);
		}
		
		add(themesComboBox);
		add(text2, 2);
		add(VChangeLanguage.getInstance());
		
		themesComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				try {
					UIManager.setLookAndFeel((String)cb.getSelectedItem());
					SwingUtilities.updateComponentTreeUI(VWorkspace.getInstance());
					MWorkspace.getInstance().getWorkspaceFile().setDefaultTheme((String) cb.getSelectedItem());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				VPopupMenu.getInstance().updateUI();
			}
		});
			
	}

	public static VChangeTheme getInstance() {
		if (instance == null){
			instance = new VChangeTheme();
		}
		return instance;
	}	
	
}
