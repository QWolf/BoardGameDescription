package boardGameStateMachine.actionModel.CodeValue;

import boardGameStateMachine.util.VariableType;

public abstract class CodeValueDouble extends CodeValue{

	public CodeValueDouble(){
		super(VariableType.DOUBLE);
	}
	
	public abstract double getValue();
}
