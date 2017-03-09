package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.LinkOption;
import java.util.ArrayList;

import com.thoughtworks.xstream.io.path.Path;

public class MWorkspaceFile implements Serializable {

	private ArrayList<String> projectPaths = new ArrayList<String>();
	private String defaultLanguage = "English";
	private String defaultAlignment = "Cascade";
	private String defaultTheme = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
	private String fileLocation;

	private MWorkspaceFile(String location) {
		super();
		this.fileLocation = location;
	}

	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
		MWorkspace.getInstance().notifyObservers(defaultLanguage, "changeLanguage");
	}

	public ArrayList<String> getProjectPaths() {
		return projectPaths;
	}

	public void addProjectPath(String path) {
		if (!this.projectPaths.contains(path)) {
			this.projectPaths.add(path);
		}
	}

	public static MWorkspaceFile open(String path) {
		MWorkspaceFile file = null;
		ObjectInputStream stream;
		try {
			stream = new ObjectInputStream(new FileInputStream(path + "/" + ".gerudoc"));
			MProject project;
			try {
				file = (MWorkspaceFile) stream.readObject();
			} catch (ClassNotFoundException e) {

			}
			stream.close();
		} catch (FileNotFoundException e1) {
			file = new MWorkspaceFile(path);
			file.save();
		} catch (IOException e1) {
			file = new MWorkspaceFile(path);
			file.save();
		}

		return file;
	}

	public String getDefaultTheme() {
		return defaultTheme;
	}

	public void setDefaultTheme(String defaultTheme) {
		this.defaultTheme = defaultTheme;
		save();
	}

	public void save() {
		try {
			try {

			} catch (Exception t) {

			}
			ObjectOutputStream stream = new ObjectOutputStream(
					new FileOutputStream(new File(fileLocation + "/" + ".gerudoc")));
			stream.writeObject(this);
			stream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void remove(String path) {
		projectPaths.remove(path);
	}

}
