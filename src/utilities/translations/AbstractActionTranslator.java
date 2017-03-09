package utilities.translations;

import java.awt.Desktop.Action;

import javax.swing.AbstractAction;

public class AbstractActionTranslator {
	public AbstractActionTranslator(AbstractAction action, String resourceShort, String resourceLong) {

			new MultilanguageString(resourceLong, action) {
				protected void updateOvner() {
					((AbstractAction) ovner).putValue("ShortDescription", this.toString());
				}
			};
			new MultilanguageString(resourceShort, action) {
				protected void updateOvner() {
					((AbstractAction) ovner).putValue("Name", this.toString());
				}
			};

			new MultilanguageString(resourceLong, action) {
				protected void updateOvner() {
					((AbstractAction) ovner).putValue("LongDescription", this.toString());
				}
			};
	
	}

}
