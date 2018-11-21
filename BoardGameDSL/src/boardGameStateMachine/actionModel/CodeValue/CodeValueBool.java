package boardGameStateMachine.actionModel.CodeValue;

import boardGameStateMachine.util.IDType;

public abstract class CodeValueBool extends CodeValue{

	public CodeValueBool() {
		super(IDType.Boolean);
	}
	
	public abstract boolean getValue();

}
