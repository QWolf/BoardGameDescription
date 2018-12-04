package boardGameSimulator.model.boardGameStateMachine.CodeValue;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarType;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.ActionRound;

/**
 * A codeValue which refers to another Action/Round. (Most likely a Round). An
 * action performed from "Choose Action" is not considered here, these are the
 * actions with "predefined" parameters.
 * 
 * @author Peter
 *
 */
public class CodeValueActionRound extends CodeValue {
	ActionRound actionRound;
	CodeValue[] parameters;
	CodeLineReturn executedSolution = null;

	/**
	 * Executes an Action or Round with Codevalues as arguments
	 * @param vt return Type
	 * @param a the action to be executed
	 * @param parameters The codevalues of the parameters
	 */
	public CodeValueActionRound(VarType vt, ActionRound a, CodeValue[] parameters) {
		super(vt);
		this.actionRound = a;
		this.parameters = parameters;
	}

	@Override
	public Variable getValue(MultiScopeVariableManager scope) {
		
		Variable[] calculatedParameters = new Variable[parameters.length];
		for(int i = 0; i<parameters.length-1; i++){
			calculatedParameters[i] = parameters[i].getValue(scope);
		}
		executedSolution = actionRound.executeActionRound(calculatedParameters);
		if(executedSolution.isVariable()){
			return executedSolution.getVariable();
		}else{
			return null;
		}
		
	}

}
