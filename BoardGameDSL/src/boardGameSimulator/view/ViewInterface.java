package boardGameSimulator.view;

import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

public interface ViewInterface {
	
	public String getActionName();
	
	public Variable[] getParameters();

	public Variable[] getActionParameters();

}
