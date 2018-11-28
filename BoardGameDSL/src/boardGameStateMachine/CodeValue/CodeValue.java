package boardGameStateMachine.CodeValue;

import boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameStateMachine.Variable.VarType;
import boardGameStateMachine.Variable.Variable;


/**
 * A value in a line of code, abstractized
 * @author Peter
 *
 */
public abstract class CodeValue {
	
	protected VarType varType;
	
	
	public CodeValue(VarType vt){
		this.varType = vt;
	}
	
	
	
	
	public abstract Variable getValue(MultiScopeVariableManager scope);
	
	public VarType getType(){
		return varType;
	}

}
