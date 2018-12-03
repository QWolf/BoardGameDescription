package boardGameStateMachine.CodeValue;

import boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameStateMachine.Variable.VarDouble;
import boardGameStateMachine.Variable.VarInt;
import boardGameStateMachine.Variable.VarType;
import boardGameStateMachine.Variable.Variable;

public class CodeValueAddOp extends CodeValue {

	public enum AddOp {
		Add,Subtract;
	}

	private CodeValue par1;
	private CodeValue par2;
	private AddOp operator;

	/**
	 * CodeValue of a Addiction action, expressed in CodeValues
	 * 
	 * @param par1
	 *            First parameter
	 * @param ao
	 *            Addition or Subtraction
	 * @param par2
	 *            Second Parameter
	 */
	public CodeValueAddOp(CodeValue par1, AddOp ao, CodeValue par2) {
		super(getReturnType(par1, par2));
		this.par1 = par1;
		this.par2 = par2;
		this.operator = ao;
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
		case Add:
			return getReturnVariable(par1ToDouble + par2ToDouble);
		case Subtract:
			return getReturnVariable(par1ToDouble - par2ToDouble);
		default:
			System.out.println("Invalid AddOperator!");
			return null;

		}
	}
}
