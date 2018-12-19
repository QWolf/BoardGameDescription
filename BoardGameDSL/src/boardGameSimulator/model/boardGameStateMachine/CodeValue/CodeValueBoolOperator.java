package boardGameSimulator.model.boardGameStateMachine.CodeValue;

import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarBool;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarType;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

public class CodeValueBoolOperator extends CodeValue {

	public enum BoolOperator {
		And, Or;
	}

	private CodeValue par1;
	private BoolOperator operator;
	private CodeValue par2;

	public CodeValueBoolOperator(CodeValue par1, BoolOperator bo, CodeValue par2) {
		super(VarType.Boolean);
		this.par1 = par1;
		this.operator = bo;
		this.par2 = par2;
	}

	@Override
	public Variable getValue(MultiScopeVariableManager scope) {
		VarBool calculatedPar1 = (VarBool) par1.getValue(scope);
		
		boolean outcome = false;
		switch (operator) {
		case And:
			if(calculatedPar1.getValue() == false){
				outcome = false;
				break;
			}else{
				outcome = ((VarBool) par2.getValue(scope)).getValue();
			}
			break;
		case Or:
			if(calculatedPar1.getValue() == true){
				outcome = true;
			}else{
				outcome = ((VarBool) par2.getValue(scope)).getValue();
			}
			break;
		default:
			return null;

		}
		return new VarBool(outcome);
	}

}
