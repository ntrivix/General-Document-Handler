package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import models.MWorkspace;
import utilities.GetResource;
import utilities.translations.MultilanguageString;
import view.menus_bars.VMenuBar;
import view.menus_bars.VStatusBar;
import view.menus_bars.VToolbar;

import com.alee.laf.WebLookAndFeel;

import controller.ActionManager;

public class VWorkspace extends JFrame {
	private static VWorkspace instance;

	private VDesktop desktop;
	private MWorkspace mWorkspace = MWorkspace.getInstance();
	private VTree vTree;
	private JSplitPane split;
	private JScrollPane scroll;

	private VWorkspace() {
		setSize(1024, 768);
		setLocationRelativeTo(null);
		setTitle("GeRuDoc Prototip");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		try {
			// WebLookAndFeel.install();
			UIManager.setLookAndFeel(MWorkspace.getInstance().getWorkspaceFile().getDefaultTheme());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

		vTree = new VTree(mWorkspace);

		setJMenuBar(new VMenuBar());
		add(new VToolbar(), BorderLayout.NORTH);

		desktop = new VDesktop();

		scroll = new JScrollPane(vTree);
		scroll.setMinimumSize(new Dimension(200, 150));
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, desktop);
		add(split, BorderLayout.CENTER);
		split.setDividerLocation(250);
		
		add(new VStatusBar(), BorderLayout.PAGE_END);

		this.setIconImage(GetResource.getIcon("icons/spejsmen.png").getImage());

		setVisible(true);
		
		new MultilanguageString("gerudoc", this);

		addComponentListener(ActionManager.getInstance().getcWindowResize());
	}

	public static VWorkspace getInstance() {
		if (instance == null) {
			instance = new VWorkspace();
		}
		return instance;
	}

	public VDesktop getDesktop() {
		return desktop;
	}

	public VTree getvTree() {
		return vTree;
	}

}
