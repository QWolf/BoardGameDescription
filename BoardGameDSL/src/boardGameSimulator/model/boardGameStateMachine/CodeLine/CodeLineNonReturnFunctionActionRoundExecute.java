package boardGameSimulator.model.boardGameStateMachine.CodeLine;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturnType;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.ActionRound;

public class CodeLineNonReturnFunctionActionRoundExecute extends CodeLine{
	
	
	private ActionRound actionround;
	private CodeValue[] arguments;
	
	/**
	 * Executes an action (as a function), it does not care about the return;
	 * 
	 * @param ar		The action which needs to be executed
	 * @param arguments The arguments, expressed in CodeValues
	 */
	public CodeLineNonReturnFunctionActionRoundExecute(ActionRound ar, CodeValue[] arguments){
		this.actionround = ar;
		this.arguments = arguments;
	}

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		Variable[] variables = new Variable[arguments.length];
		if(actionround.executeActionRound(variables).isInvalidAction()){
			System.out.println("NonReturnAction was invalid!");			
		}
		return new CodeLineReturn(CodeLineReturnType.Empty,false);
	}

}
