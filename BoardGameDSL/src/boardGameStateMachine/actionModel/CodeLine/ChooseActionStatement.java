package boardGameStateMachine.actionModel.CodeLine;

import boardGameStateMachine.stateModel.Action;

public class ChooseActionStatement extends CodeLine{

	public ChooseActionStatement() {
		super(CodeLineType.ChooseAction);
		// TODO Auto-generated constructor stub
	}
	
	
	public Object execute(Action a){
		return a.execute();
	}

}
