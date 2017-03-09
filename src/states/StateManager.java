package states;

import models.MGraphSlot;
import utilities.Observer;

public class StateManager implements Observer {
	private MGraphSlot model;
	
	private CircleState circleState;
	private TriangleState triangleState;
	private SquareState squareState;
	private SelectState selectState;
	private State currentState;
	
	
	public CircleState getCircleState() {
		return circleState;
	}

	public void setCircleState(CircleState circleState) {
		this.circleState = circleState;
	}

	public SquareState getSquareState() {
		return squareState;
	}

	public void setSquareState(SquareState squareState) {
		this.squareState = squareState;
	}

	public SelectState getSelectState() {
		return selectState;
	}

	public void setSelectState(SelectState selectState) {
		this.selectState = selectState;
	}
	
	public TriangleState getTriangleState() {
		return triangleState;
	}

	public void setTriangleState(TriangleState triangleState) {
		this.triangleState = triangleState;
	}
	

	

	public StateManager(MGraphSlot med) {
		model = med;

		circleState = new CircleState(med);
		squareState = new SquareState(med);
		selectState = new SelectState(med);
		triangleState = new TriangleState(med);
		currentState = selectState;
	}
	
	public void setState(State state) {
		model.setFocus();
		currentState.outOfState();
		state.inState();
		currentState = state;
	}

	public State getCurrentState() {
		return currentState;
	}

	@Override
	public void update(Object observable, Object arg, String command) {
		
	}
}