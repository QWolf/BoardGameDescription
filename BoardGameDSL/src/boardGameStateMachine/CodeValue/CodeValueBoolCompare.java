package boardGameStateMachine.CodeValue;

import boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameStateMachine.Variable.VarType;
import boardGameStateMachine.Variable.Variable;

public class CodeValueBoolCompare extends CodeValue {

	public enum CompareOperator {
		LT, LTE, EQ, NE, GT, GTE;
	}

	private CodeValue par2;
	private CompareOperator co;
	private CodeValue par1;

	public CodeValueBoolCompare(CodeValue par1, CompareOperator co, CodeValue par2) {
		super(VarType.Boolean);
		this.par1 = par1;
		this.co = co;
		this.par2 = par2;

	}

	@Override
	public Variable getValue(MultiScopeVariableManager scope) {
		Variable calculatedPar1 = par1.getValue(scope);
		Variable calculatedPar2 = par2.getValue(scope);

		switch (calculatedPar1.getVarType()) {
		case Boolean:
			break;
		case Double:
			ERRORTODO
			break;
		case Int:
			break;
		case List:
			break;
		case Location:
			break;
		case Object:
			break;
		case Owner:
			break;
		case Player:
			break;
		case Round:
			break;
		case Unspecified:
			break;
		default:
			break;

		}
		// TODO proper error handling
		System.out.println("Variable type could not be compared!");
		return null;
	}

}
