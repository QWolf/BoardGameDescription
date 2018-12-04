package boardGameSimulator.view;

import boardGameSimulator.model.boardGameStateMachine.Variable.RawVariable;

public interface ViewInterface {
	
	public String getActionName();
	
	public RawVariable[] getParameters();

}
