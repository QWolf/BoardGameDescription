package boardGameStateMachine.actionModel.CodeValue;

import boardGameStateMachine.util.IDType;

public abstract class CodeValue {
	
	private IDType idType;
	
	
	
	public CodeValue(IDType vt){
		idType = vt;
		
	}
	
	public  IDType getIDType(){
		return idType;
	}
	
	

	

}
