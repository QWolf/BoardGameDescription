package boardGameStateMachine.actionModel.CodeValue;

import boardGameStateMachine.util.VariableType;

public abstract class CodeValueInt extends CodeValue{

	public CodeValueInt(){
		super(VariableType.INT);
	}
	
	public abstract int getValue();
}
