package boardGameStateMachine.actionModel.CodeValue.deprecated;

import boardGameStateMachine.util.IDType;

public abstract class CodeValue_old {
	
	private IDType idType;
	
	
	
	public CodeValue_old(IDType vt){
		idType = vt;
		
	}
	
	public  IDType getIDType(){
		return idType;
	}
	
	public abstract Object getValue();
	
	

	

}
