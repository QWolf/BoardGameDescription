package boardGameStateMachine.actionModel.CodeValue;

import boardGameStateMachine.util.IDType;

public abstract class CodeValueInt extends CodeValue_old{

	public CodeValueInt(){
		super(IDType.Int);
	}
	
	public abstract int getValueInt();
	
	public Object getValue(){
		return (Object) getValueInt();
	}
}
