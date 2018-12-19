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
	private String rawString;
	

	
	public CodeValue(VarType vt){
		this(vt, "NoStringGiven");
	}
	public CodeValue(VarType vt, String rawString){
		this.varType = vt;
		this.rawString = rawString;
	}
	
	
	
	
	public abstract Variable getValue(MultiScopeVariableManager scope);
	
	public VarType getType(){
		return varType;
	}
	
	public String toRawString(){
		return "Raw CodeValue: " + rawString;
	}

}
