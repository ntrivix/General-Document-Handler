package view;

import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JTree;
import javax.swing.plaf.TreeUI;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import controller.ActionManager;
import models.MWorkspace;
import utilities.workspaceTreeUtilities.WorkspaceTreeCellRenderer;
import utilities.workspaceTreeUtilities.WorkspaceTreeEditor;

public class VTree extends JTree {

	public VTree(TreeModel newModel) {
		
		setCellRenderer(new WorkspaceTreeCellRenderer());

		setCellEditor(new WorkspaceTreeEditor(this, new WorkspaceTreeCellRenderer()));
		init();
	}
	
	public void init(){
		setModel(MWorkspace.getInstance());
		setSelectionModel(MWorkspace.getInstance().getTreeSelectionModel());
		addTreeSelectionListener(ActionManager.getInstance().getcTree());
		addMouseListener(ActionManager.getInstance().getcPopupMenu());
		setFocusable(false);
		setEditable(true);
	}

	@Override
	public boolean isPathEditable(TreePath path) {
		// Koren nije editable
		if (path != null) {
			DefaultMutableTreeNode tn = (DefaultMutableTreeNode) path.getLastPathComponent();
			if (!tn.isRoot()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

}
