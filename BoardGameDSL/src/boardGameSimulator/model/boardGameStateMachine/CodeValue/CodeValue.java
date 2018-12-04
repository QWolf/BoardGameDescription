package boardGameSimulator.model.boardGameStateMachine.CodeValue;

import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarType;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;


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
