package boardGameStateMachine.actionModel.CodeValue;

import boardGameStateMachine.util.VariableType;

public abstract class CodeValueBool extends CodeValue{

	public CodeValueBool() {
		super(VariableType.BOOL);
	}
	
	public abstract boolean getValue();

}
