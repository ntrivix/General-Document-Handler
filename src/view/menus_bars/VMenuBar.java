package view.menus_bars;

import java.util.ResourceBundle;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import controller.ActionManager;
import utilities.translations.MultilanguageString;

public class VMenuBar extends JMenuBar {
	private JMenu file;
	private JMenu window;
	private JMenu help;
	private JMenu navigation;

	public VMenuBar() {

		file = new JMenu();
		file.setMnemonic('P');
		window = new JMenu();
		window.setMnemonic('W');
		help = new JMenu();
		help.setMnemonic('H');
		navigation = new JMenu();
		navigation.setMnemonic('N');

		new MultilanguageString("projectS", file);
		new MultilanguageString("windowS", window);
		new MultilanguageString("helpS",help);
		new MultilanguageString("navigateS",navigation);

		file.add(ActionManager.getInstance().getcAddProject());
		file.add(ActionManager.getInstance().getcAddDocument());
		file.add(ActionManager.getInstance().getcAddPage());
		file.add(ActionManager.getInstance().getcAddGraphSlot());
		file.add(ActionManager.getInstance().getcAddTextSlot());

		file.addSeparator();

		file.add(ActionManager.getInstance().getcOpenProject());
		file.add(ActionManager.getInstance().getcSaveProject());
		file.add(ActionManager.getInstance().getcSaveAs());
		file.add(ActionManager.getInstance().getcSaveAll());
		file.add(ActionManager.getInstance().getcProjectRemove());

		file.addSeparator();

		file.add(ActionManager.getInstance().getcChangeWorkspace());

		window.add(ActionManager.getInstance().getcCascadeAlignment());
		window.add(ActionManager.getInstance().getcHorizontalAlignment());
		window.add(ActionManager.getInstance().getcVerticalAlignment());
		window.add(ActionManager.getInstance().getcMatrixAlignment());

		navigation.add(ActionManager.getInstance().getcMoveUp());
		navigation.add(ActionManager.getInstance().getcMoveDown());

		help.add(ActionManager.getInstance().getcAbout());

		add(file);
		add(window);
		add(navigation);
		add(help);

	}
}
