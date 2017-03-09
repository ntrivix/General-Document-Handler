package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import models.MDocument;
import models.MPage;
import net.miginfocom.swing.MigLayout;
import utilities.Observer;
import view.menus_bars.VPagesInRowToolbar;

public class VDocument extends JPanel implements Observer {

	private MDocument document;
	private JPanel container;
	private JScrollPane scrollPane;
	private MigLayout layout;

	public VDocument(MDocument document) {
		super();
		this.document = document;
		layout = new MigLayout("wrap 1", "[grow,fill]");
		this.setLayout(new BorderLayout());

		this.add(new VPagesInRowToolbar(document), BorderLayout.PAGE_START);
		container = new JPanel(layout);
		scrollPane = new JScrollPane(container);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar().setUnitIncrement(15);
		
		this.add(scrollPane, BorderLayout.CENTER);
		container.setBackground(Color.LIGHT_GRAY);
		setVisible(true);
		document.addObserver(this);
		
		document.setPagesInRow(document.getPagesInRow());

		for (MPage p : document.getPages()) {
			VPage vPage = new VPage((MPage) p);
			container.add(vPage);
			layout.minimumLayoutSize(container);
			vPage.validate();
			vPage.invalidate();
		}
	}

	@Override
	public void update(Object observable, Object arg, String command) {
		if (command.equals("RenameTab")) {
			JTabbedPane parent = (JTabbedPane) getParent();
			parent.setTitleAt(parent.getSelectedIndex(), document.toString());
			updateUI();

			return;
		}
		if (arg instanceof MPage && command.equals("newPage")) {
			VPage vPage = new VPage((MPage) arg);
			container.add(vPage);
			layout.minimumLayoutSize(container);
			vPage.validate();
			vPage.invalidate();

			container.invalidate();
			container.validate();
			
			container.repaint();

			scrollPane.invalidate();
			scrollPane.validate();
			scrollPane.repaint();
			vPage.setFocus();
			vPage.scrollToPage();
		}
		if (arg instanceof MDocument && command.equals("focus")) {
			MDocument doc = (MDocument) arg;
			VProject project = (VProject) VWorkspace.getInstance().getDesktop().getSelectedFrame();
			project.getDocumentTab().setSelectedComponent(this);
		} else {
			if (arg instanceof MDocument && command.equals("del")) {
				this.setVisible(false);
				JTabbedPane projectTab = (JTabbedPane) this.getParent();
				this.removeAll();
				projectTab.remove(this);
				projectTab.validate();
				document = null;
			}
		}
		if (arg instanceof Integer && command.equals("rearrange")) {
			layout.setLayoutConstraints("wrap " + arg);
			container.updateUI();
		}

	}

	public MDocument getDocument() {
		return document;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}
	
	
}
