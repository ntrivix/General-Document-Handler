package utilities;

import javax.swing.tree.DefaultMutableTreeNode;

public interface FileActions {
	public void delete();

	public void rename(String name);

	public void saveStates();

	public void saveFile();
}
