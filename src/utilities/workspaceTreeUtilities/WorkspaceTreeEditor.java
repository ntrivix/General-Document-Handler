package utilities.workspaceTreeUtilities;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;

import models.MDocument;
import models.MPage;
import models.MProject;
import utilities.FileActions;

public class WorkspaceTreeEditor extends DefaultTreeCellEditor implements ActionListener {
	private JTextField edit;
	private Object value = null;

	public WorkspaceTreeEditor(JTree tree, DefaultTreeCellRenderer renderer) {
		super(tree, renderer);
	}

	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
			boolean leaf, int row) {
		super.getTreeCellEditorComponent(tree, value, isSelected, expanded, leaf, row);
		this.value = value;

		edit = new JTextField(value.toString());
		edit.addActionListener(this);
		return edit;
	}

	@Override
	public boolean isCellEditable(EventObject event) {
		if (event instanceof MouseEvent) {
			if (((MouseEvent) event).getClickCount() == 2)
				return true;
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (value instanceof FileActions) {
			((FileActions) value).rename(e.getActionCommand());
			;
		}
		stopCellEditing();
	}

}
