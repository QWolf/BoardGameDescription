package boardGameStateMachine.actionModel.CodeValue;

import boardGameStateMachine.util.VariableType;

public abstract class CodeValueID extends CodeValue{
	
	public CodeValueID(){
		super(VariableType.ID);
	}
	
	public abstract String getValue();

}
