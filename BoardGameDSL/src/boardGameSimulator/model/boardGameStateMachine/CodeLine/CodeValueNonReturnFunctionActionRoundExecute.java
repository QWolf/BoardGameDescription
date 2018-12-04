package boardGameSimulator.model.boardGameStateMachine.CodeLine;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturnType;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.ActionRound;

public class CodeValueNonReturnFunctionActionRoundExecute extends CodeLine{
	
	
	private ActionRound actionround;
	private CodeValue[] arguments;
	
	/**
	 * Executes an action (as a function), it does not care about the return;
	 * 
	 * @param ar		The action which needs to be executed
	 * @param arguments The arguments, expressed in CodeValues
	 */
	public CodeValueNonReturnFunctionActionRoundExecute(ActionRound ar, CodeValue[] arguments){
		this.actionround = ar;
		this.arguments = arguments;
	}

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		Variable[] variables = new Variable[arguments.length];
		actionround.executeActionRound(variables);
		return new CodeLineReturn(CodeLineReturnType.Empty,false);
	}

}
