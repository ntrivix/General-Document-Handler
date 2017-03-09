package utilities;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import models.MProject;
import models.MWorkspace;

public abstract class AdaptiveDefaultMutableTreeNode extends DefaultMutableTreeNode implements Focusable {

	protected transient AdaptiveDefaultMutableTreeNode old;
	protected int childrenUnsaved;
	protected transient boolean deleted;
	protected transient boolean changed;

	public abstract void setChanged(boolean changed);

	public AdaptiveDefaultMutableTreeNode getOld() {
		return old;
	}

	public void setOld(AdaptiveDefaultMutableTreeNode old) {
		this.old = old;
	}

	public boolean isChanged() {
		return changed;
	}

	public AdaptiveDefaultMutableTreeNode() {
		super();
	}

	public AdaptiveDefaultMutableTreeNode(Object userObject) {
		super(userObject);
	}

	public int getChildrenUnsaved() {
		return childrenUnsaved;
	}

	public void setChildrenUnsaved(int childrenUnsaved) {
		if (childrenUnsaved == this.childrenUnsaved)
			return;
		this.childrenUnsaved = childrenUnsaved;
		if (this.childrenUnsaved > 0)
			this.setChanged(true);
		else
			this.setChanged(false);
	}

	public void setFocus() {
		if (this.equals(MWorkspace.getInstance().getActiveNode()))
			return;
		try {
			((Focusable) MWorkspace.getInstance().getActiveNode()).unfocus();
		} catch (Exception e) {

		}
		try {
			((Focusable) this.getParent()).setFocus();
		} catch (Exception e) {

		}
		MWorkspace.getInstance().setActiveNode(this);
		MWorkspace.getInstance().getTreeSelectionModel().setSelectionPath(new TreePath(this.getPath()));

	}

	@Override
	public void unfocus() {
		
	}
	
	
}
