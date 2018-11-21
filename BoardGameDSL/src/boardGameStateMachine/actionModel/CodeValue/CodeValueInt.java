package boardGameStateMachine.actionModel.CodeValue;

import boardGameStateMachine.util.IDType;

public abstract class CodeValueInt extends CodeValue{

	public CodeValueInt(){
		super(IDType.Int);
	}
	
	public abstract int getValue();
}
