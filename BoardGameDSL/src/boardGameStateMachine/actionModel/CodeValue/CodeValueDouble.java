package boardGameStateMachine.actionModel.CodeValue;

import boardGameStateMachine.util.IDType;

public abstract class CodeValueDouble extends CodeValue{

	public CodeValueDouble(){
		super(IDType.Double);
	}
	
	public abstract double getValue();
}
