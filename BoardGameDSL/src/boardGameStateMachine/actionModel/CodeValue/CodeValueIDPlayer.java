package boardGameStateMachine.actionModel.CodeValue;

import boardGameStateMachine.stateModel.Player;
import boardGameStateMachine.util.IDType;

public abstract class CodeValueIDPlayer extends CodeValue{
	
	public CodeValueIDPlayer(){
		super(IDType.Player);
	}
	
	public abstract Player getValue();

}
