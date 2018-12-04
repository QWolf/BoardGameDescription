package z_deprecated.deprecatedBoardGameStateMachine.actionModel.CodeValue.deprecated;

import boardGameSimulator.model.boardGameStateMachine.util.IDType;

public abstract class CodeValueDouble extends CodeValue_old{

	public CodeValueDouble(){
		super(IDType.Double);
	}
	
	public abstract double getValueDouble();
	
	public Object getValue(){
		return (Object) getValueDouble();
	}
}
