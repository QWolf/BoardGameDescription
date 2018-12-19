package boardGameSimulator.model.boardGameStateMachine.CodeValue;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarList;
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
	public CodeValueActionRound(VarType vt, ActionRound a, CodeValue[] parameters, String rawString) {
		super(vt, rawString);
		this.actionRound = a;
		this.parameters = parameters;
	}

	@Override
	public Variable getValue(MultiScopeVariableManager scope) {
		System.out.println("--------CVActionRound--123-");

		
		Variable[] calculatedParameters = new Variable[parameters.length];
		for(int i = 0; i<parameters.length; i++){
			calculatedParameters[i] = parameters[i].getValue(scope);
		}
		
		System.out.println("--------CVActionRound---");
		System.out.println(actionRound);
		System.out.println(parameters);		
		System.out.println(new VarList(calculatedParameters));

		executedSolution = actionRound.executeActionRound(calculatedParameters);
		this.varType = executedSolution.getVariable().getVarType();
		if(executedSolution.isVariable()){
			return executedSolution.getVariable();
		}else{
			return null;
		}
		
	}

}
