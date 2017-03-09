package view.slot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import models.MElement;
import models.MGraphSlot;
import models.MSlot;
import models.MTextSlot;

public class VGraphSlot extends VSlot {

	private VGraphSlot_Dialog gDialog = null;

	public VGraphSlot(MSlot slot) {
		super(slot);
		this.slot = slot;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		Graphics2D g = (Graphics2D) gr;
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		MGraphSlot model = (MGraphSlot) slot;

		double maxW = this.getWidth();
		double maxH = this.getWidth();
		for (int i = 0; i < model.getElements().size(); i++) {
			MElement element = model.getElements().get(i);
			double tmpW = element.getPosition().getX() + element.getSize().getWidth();
			double tmpH = element.getPosition().getY() + element.getSize().getHeight();
			if (tmpW > maxW)
				maxW = tmpW;
			if (tmpH > maxH)
				maxH = tmpH;
		}

		BufferedImage bimage = new BufferedImage((int) Math.round(maxW), (int) Math.round(maxH),
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = bimage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		for (int i = 0; i < model.getElements().size(); i++) {
			MElement element = model.getElements().get(i);
			element.getElementPainter().paint(g2d, element);
		}

		int w = this.getWidth();
		int h = this.getHeight();
		double scale = 1;

		double s1, s2;
		s1 = w / (double) maxW;
		s2 = h / (double) maxH;
		if (s1 > s2) {
			scale = s2;
		} else {
			scale = s1;
		}

		g2d.scale(scale, scale);
		g.drawImage(bimage, g2d.getTransform(), null);
		g2d.dispose();
	}

	@Override
	public void update(Object observable, Object arg, String command) {
		super.update(observable, arg, command);
		if (command.equals("EditMode")) {
			VGraphSlot_Dialog.getInstance((MGraphSlot) slot, this);
		}
		if (gDialog != null) {
			gDialog.repaint();
		}
	}

	public void setgDialog(VGraphSlot_Dialog gDialog) {
		this.gDialog = gDialog;
	}

}
