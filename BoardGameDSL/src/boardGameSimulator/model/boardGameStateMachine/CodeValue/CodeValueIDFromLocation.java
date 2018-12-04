package boardGameSimulator.model.boardGameStateMachine.CodeValue;

import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarType;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
/**
 * A Codevalue which handles the format: Place.Attr
 * 
 * @author Peter
 *
 */
public class CodeValueIDFromLocation extends CodeValue{
	
	private String variableName;
	private CodeValue fromSpace;
	
	

	public CodeValueIDFromLocation(VarType returnType, CodeValue searchSpace, String toGetVariableName) {
		super(returnType);
		this.variableName = toGetVariableName;
		this.fromSpace = searchSpace;
	}

	
	
	@Override
	public Variable getValue(MultiScopeVariableManager scope) {
		Variable fromVariable = fromSpace.getValue(scope);
		
		return fromVariable.getGameObjectVariableManager().getVariable(variableName);
	}

}
