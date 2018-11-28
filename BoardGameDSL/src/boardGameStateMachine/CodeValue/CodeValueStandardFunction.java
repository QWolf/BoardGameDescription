package boardGameStateMachine.CodeValue;

import boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameStateMachine.Variable.VarList;
import boardGameStateMachine.Variable.VarType;
import boardGameStateMachine.Variable.Variable;

public class CodeValueStandardFunction extends CodeValue {

	public enum StandardFunction {
		ListCount;

		public static VarType getVarType(StandardFunction sf) {
			switch (sf) {
			case ListCount:
				return VarType.Int;
			default:
				return null;
			}
		}
	}

	private StandardFunction function;
	private CodeValue par1;

	public CodeValueStandardFunction(StandardFunction sf, CodeValue par1) {
		super(StandardFunction.getVarType(sf));

		this.function = sf;
		this.par1 = par1;
	}

	@Override
	public Variable getValue(MultiScopeVariableManager scope) {
		Variable calculatedPar1 = par1.getValue(scope);
		switch (function) {
		case ListCount:
			return ((VarList) calculatedPar1).getSize();
		default:
			break;
		}
		return null;
	}
}
