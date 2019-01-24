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
public class CodeValueIDFromLocation extends CodeValue {

	private String variableName;
	private CodeValue fromSpace;

	public CodeValueIDFromLocation(VarType returnType, CodeValue searchSpace, String toGetVariableName, String rawString) {
		super(returnType, rawString);
		this.variableName = toGetVariableName;
		this.fromSpace = searchSpace;
	}

	@Override
	public Variable getValue(MultiScopeVariableManager scope) {
//		System.out.println("@"+"-------IDFromLoc: " + variableName);

		Variable fromVariable = fromSpace.getValue(scope);
//		System.out.println("@"+fromSpace);
//		System.out.println("@"+fromSpace.getValue(scope));
//		System.out.println("@"+fromVariable);
//		System.out.println("@"+this.toRawString());

		Variable toReturn = fromVariable.getGameObjectVariableManager().getVariable(variableName);
		// System.out.println(toReturn);
		this.varType = toReturn.getVarType();
		return toReturn;
	}

}
