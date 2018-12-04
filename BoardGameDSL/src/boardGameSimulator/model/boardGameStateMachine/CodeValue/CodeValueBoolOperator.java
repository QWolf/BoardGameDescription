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
		VarBool calculatedPar2 = (VarBool) par2.getValue(scope);
		switch (operator) {
		case And:
			return new VarBool(calculatedPar1.getValue() && calculatedPar2.getValue());
		case Or:
			return new VarBool(calculatedPar1.getValue() || calculatedPar2.getValue());
		default:
			return null;

		}
	}

}
