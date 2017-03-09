package utilities;

import java.io.Serializable;

public interface Observer {
	public void update(Object observable, Object arg, String command);
}
