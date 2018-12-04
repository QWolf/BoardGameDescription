package z_deprecated.deprecatedBoardGameStateMachine.actionModel.CodeValue.deprecated;

import boardGameSimulator.model.boardGameStateMachine.stateModel.Player;
import boardGameSimulator.model.boardGameStateMachine.util.IDType;

public abstract class CodeValueIDPlayer extends CodeValue_old{
	
	public CodeValueIDPlayer(){
		super(IDType.Player);
	}
	
	public abstract Player getValue();

}
