package utilities.workspaceTreeUtilities;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import models.MCircleElement;
import models.MDocument;
import models.MGraphSlot;
import models.MPage;
import models.MProject;
import models.MSquareElement;
import models.MTextSlot;
import models.MTriangleElement;
import utilities.GetResource;

public class WorkspaceTreeCellRenderer extends DefaultTreeCellRenderer {
	public WorkspaceTreeCellRenderer() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

		if (value instanceof MProject) {
			setIcon(GetResource.getIcon("icons/project.png"));
		} else if (value instanceof MDocument) {
			setIcon(GetResource.getIcon("icons/document.png"));
		} else if (value instanceof MPage) {
			setIcon(GetResource.getIcon("icons/page.png"));
		}
		else if (value instanceof MGraphSlot) {
			setIcon(GetResource.getIcon("icons/Ruller.png"));
		} else if (value instanceof MTextSlot) {
			setIcon(GetResource.getIcon("icons/Pen.png"));
		} else if (value instanceof MCircleElement) {
			setIcon(GetResource.getIcon("icons/circle.png"));
		} else if (value instanceof MSquareElement) {
			setIcon(GetResource.getIcon("icons/rectangle.png"));
		} else if (value instanceof MTriangleElement) {
			setIcon(GetResource.getIcon("icons/triangle.png"));
		} else {
			setIcon(GetResource.getIcon("icons/workspace.png"));
		}
		if (selected) {
			setForeground(Color.BLUE);
		}
		return this;
	}
}
