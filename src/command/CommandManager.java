package command;

import java.util.ArrayList;

import controller.ActionManager;
import models.MWorkspace;

public class CommandManager {
	private ArrayList<AbstractCommand> commands = new ArrayList<AbstractCommand>();
	private int counter = 0;

	public void addCommand(AbstractCommand com) {
		while (counter < commands.size())
			commands.remove(counter);
		commands.add(com);
		doCommand();
	}

	public void doCommand() {
			ActionManager.getInstance().getcUndoCommand().setEnabled(true);
			commands.get(counter++).doCommand();
		if (counter == commands.size()) {
			ActionManager.getInstance().getcRedoCommand().setEnabled(false);
		}
	}

	public void undoCommand() {
			ActionManager.getInstance().getcRedoCommand().setEnabled(true);
			commands.get(--counter).undoCommand();
		if (counter == 0) {
			ActionManager.getInstance().getcUndoCommand().setEnabled(false);
		}
	}
}
