package boardGameStateMachine.CodeValue;

import boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameStateMachine.Variable.VarType;
import boardGameStateMachine.Variable.Variable;

public class CodeValueParameters extends CodeValue{
	
	CodeValue value;

	public CodeValueParameters(VarType vt, CodeValue cv) {
		super(cv.getType());
		this.value = cv;
	}

	@Override
	public Variable getValue(MultiScopeVariableManager scope) {
		return value.getValue(scope);
	}

}
