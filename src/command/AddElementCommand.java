package command;

import java.awt.geom.Point2D;

import models.MCircleElement;
import models.MElement;
import models.MGraphSlot;
import models.MSquareElement;
import models.MTriangleElement;

public class AddElementCommand extends AbstractCommand{

  MGraphSlot model;
  MElement element = null;
  String type;
  Point2D lastPosition;
  
    public AddElementCommand(MGraphSlot model, Point2D lastPosition, String type){
      this.model = model;
      this.lastPosition = lastPosition;
      this.type = type;
    }
	
	@Override
	public void doCommand() {
	  if (element == null){
		if (type.equals("Circle")){
	     element = new MCircleElement("Circle " + model.getChildCount(), lastPosition);		
		}
		else if (type.equals("Square")){
	     element = new MSquareElement("Square " + model.getChildCount(), lastPosition);		
		}
		else if (type.equals("Triangle")){
	     element = new MTriangleElement("Triangle " + model.getChildCount(), lastPosition);		
		}
	  }
	    model.deselectAll();
	    model.addElement(element);
		model.setFocus();	  
	}

	@Override
	public void undoCommand() {
	 model.deselectAll();
	 element.setSelected(true);
	 model.removeSelected();
	 model.setFocus();
	}

}
