package controller;

import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.plaf.basic.BasicTreeUI.TreeCancelEditingAction;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import models.MElement;
import models.MWorkspace;
import utilities.Focusable;
import view.VProject;
import view.VWorkspace;

public class CTree implements TreeSelectionListener {

	private MWorkspace workspace = MWorkspace.getInstance();
	private TreeSelectionModel tree;
	
	

	public CTree() {
		super();
		init();
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		TreePath path = tree.getSelectionPath();
		try {
			if (path.getPathComponent(path.getPathCount() - 1).equals(MWorkspace.getInstance().getActiveNode()) )
				return;

			if (MWorkspace.getInstance().getActiveNode() instanceof Focusable) {
				((Focusable) MWorkspace.getInstance().getActiveNode()).unfocus();
			}

			DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getPathComponent(path.getPathCount() - 1);
			if (node instanceof Focusable) {
				((Focusable) node).setFocus();
			} else {
				workspace.setActiveNode(node);

			}
		} catch (Exception ex) {

			workspace.setActiveNode(null);
			VProject tmp = (VProject) VWorkspace.getInstance().getDesktop().getSelectedFrame();
			try {
				tmp.unfocus();
			} catch (Exception nullE) {

			}
		}
		
	}
	
	public void init(){
		tree = MWorkspace.getInstance().getTreeSelectionModel();
	}

}
