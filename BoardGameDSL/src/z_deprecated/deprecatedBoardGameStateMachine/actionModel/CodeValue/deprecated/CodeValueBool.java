package z_deprecated.deprecatedBoardGameStateMachine.actionModel.CodeValue.deprecated;

import boardGameSimulator.model.boardGameStateMachine.util.IDType;

public abstract class CodeValueBool extends CodeValue_old{

	public CodeValueBool() {
		super(IDType.Boolean);
	}
	
	public abstract boolean getValueBool();
	
	public Object getValue(){
		return (boolean) getValueBool();
	}

}