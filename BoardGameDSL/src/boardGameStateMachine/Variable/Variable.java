package boardGameStateMachine.Variable;

public abstract class Variable {
	
	private VarType varType;
	
	public Variable(VarType vt){
		this.varType = vt;
	}
	
	public VarType getVarType(){
		return varType;
	}
	


}
