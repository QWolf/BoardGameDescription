package boardGameStateMachine.actionModel.CodeValue;

import boardGameStateMachine.util.VariableType;

public abstract class CodeValue {
	
	private VariableType varType;
	
	
	
	public CodeValue(VariableType vt){
		varType = vt;
		
	}
	
	public  VariableType getCodeValueVariable(){
		return varType;
	}
	
	

}
