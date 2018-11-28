package boardGameStateMachine.CodeValue;

import boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameStateMachine.Variable.VarBool;
import boardGameStateMachine.Variable.VarType;
import boardGameStateMachine.Variable.Variable;

public class CodeValueBoolNot extends CodeValue{

	private CodeValue toInvert;

	public CodeValueBoolNot(CodeValue toInvert) {
		super(VarType.Boolean);
		this.toInvert = toInvert;
	}

	@Override
	public Variable getValue(MultiScopeVariableManager scope) {
		VarBool calculatedToInvert = (VarBool) toInvert.getValue(scope);
		
		return new VarBool(!calculatedToInvert.getValue());
	}

}
