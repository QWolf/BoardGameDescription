package boardGameSimulator.model.boardGameStateMachine.CodeValue;

import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

public class CodeValueParameters extends CodeValue{
	
	CodeValue value;
	
	
	public CodeValueParameters(CodeValue cv) {
		super(cv.getType());
		this.value = cv;
	}

	@Override
	public Variable getValue(MultiScopeVariableManager scope) {
		return value.getValue(scope);
	}

}
