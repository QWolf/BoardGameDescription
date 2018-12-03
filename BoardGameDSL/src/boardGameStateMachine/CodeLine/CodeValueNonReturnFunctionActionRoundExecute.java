package boardGameStateMachine.CodeLine;

import boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameStateMachine.CodeLine.Return.CodeLineReturnType;
import boardGameStateMachine.CodeValue.CodeValue;
import boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameStateMachine.Variable.Variable;
import boardGameStateMachine.stateModel.ActionRound;

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
