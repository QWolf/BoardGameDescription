package boardGameSimulator.model.boardGameStateMachine.CodeLine;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;

public class CodeLineChooseAction extends CodeLine{
	
	
	private CodeValue player;

	public CodeLineChooseAction(CodeValue cv){
		this.player = cv;
		
	}

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		// TODO Auto-generated method stub
		asdf
		return null;
	}

}
