package boardGameStateMachine.CodeValue;

import boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameStateMachine.Variable.VarDouble;
import boardGameStateMachine.Variable.VarInt;
import boardGameStateMachine.Variable.VarType;
import boardGameStateMachine.Variable.Variable;

public class CodeValueMultOp extends CodeValue {

	public enum MultOp {
		Mult, Div;
	}

	private CodeValue par1;
	private CodeValue par2;
	private MultOp operator;

	/**
	 * CodeValue of a multiplication action, expressed in CodeValues
	 * 
	 * @param par1
	 *            First parameter
	 * @param mo
	 *            Multiplication or Division
	 * @param par2
	 *            Second Parameter
	 */
	public CodeValueMultOp(CodeValue par1, MultOp mo, CodeValue par2) {
		super(getReturnType(par1, par2));
		this.par1 = par1;
		this.par2 = par2;
		this.operator = mo;
	}

	private static VarType getReturnType(CodeValue par1, CodeValue par2) {
		if (par1.getType() == VarType.Int && par2.getType() == VarType.Int) {
			return VarType.Int;
		} else {
			return VarType.Double;
		}
	}

	private Variable getReturnVariable(double answer) {
		if (varType == VarType.Int) {
			return new VarInt((int) answer);
		} else {
			return new VarDouble(answer);
		}
	}

	@Override
	public Variable getValue(MultiScopeVariableManager scope) {
		Variable calculatedPar1 = par1.getValue(scope);
		Variable calculatedPar2 = par2.getValue(scope);

		double par1ToDouble = 0;
		double par2ToDouble = 0;

		if (calculatedPar1.getVarType() == VarType.Double) {
			par1ToDouble = ((VarDouble) calculatedPar1).getValue();
		} else {
			par1ToDouble = (double) ((VarInt) calculatedPar1).getValue();
		}

		if (calculatedPar2.getVarType() == VarType.Double) {
			par2ToDouble = ((VarDouble) calculatedPar2).getValue();
		} else {
			par2ToDouble = (double) ((VarInt) calculatedPar2).getValue();
		}

		switch (operator) {
		case Div:
			return getReturnVariable(par1ToDouble / par2ToDouble);
		case Mult:
			return getReturnVariable(par1ToDouble * par2ToDouble);
		default:
			System.out.println("Invalid MultOperator!");
			return null;

		}
	}
}
