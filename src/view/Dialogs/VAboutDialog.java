package view.Dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import javax.swing.JPanel;


import utilities.GetResource;
import utilities.translations.MultilanguageString;


public class VAboutDialog extends JDialog{

	JPanel bgPanel;
	private MultilanguageString aboutS = new MultilanguageString("aboutS",null);


	public VAboutDialog() {
		setSize(450, 700);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(aboutS.toString());

		bgPanel = new BackgroundPanel();
		add(bgPanel);

		bgPanel.setLayout(new GridLayout(2, 2));

		insertPhoto("Aleksandar Ogrizovic", "griz");
		insertPhoto("Ivan Dejkovic", "dejko");
		insertPhoto("Ivan Kukic", "kjuk");
		insertPhoto("Nikola Trivic", "trivic");
		setResizable(false);
		setVisible(true);
	}

	private void insertPhoto(String fullName, String picName) {
		PhotoPanel pnl = new PhotoPanel(GetResource.getIcon("photos/" + picName + ".jpg"), fullName);
		bgPanel.add(pnl);
	}

	private class PhotoPanel extends JPanel {
		ImageIcon img;
		String name;

		private PhotoPanel(ImageIcon img, String name) {
			this.img = img;
			this.name = name;
			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			int h = this.getHeight() - 30;
			int w = h * img.getIconWidth() / img.getIconHeight();

			g.setColor(Color.WHITE);
			g.setFont(new Font(Font.DIALOG, Font.BOLD, 12));

			if (name.equals("Aleksandar Ogrizovic") || name.equals("Ivan Kukic")) {
				g.drawImage(img.getImage(), 0, 0, w, h, null);
				g.drawString(name, 0, this.getHeight() - 10);
			} else {
				g.drawImage(img.getImage(), this.getWidth() - w, 0, w, h, null);
				g.drawString(name, this.getWidth() - w, this.getHeight() - 10);
			}
		}
	}

	private class BackgroundPanel extends JPanel {
		public BackgroundPanel() {
			super();

		}

		@Override
		protected void paintComponent(Graphics g) {
			g.drawImage(GetResource.getIcon("icons/space1.jpg").getImage(), 0, 0, null);
		}
	}

}
