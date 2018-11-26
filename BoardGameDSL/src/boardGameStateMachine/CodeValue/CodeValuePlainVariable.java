package boardGameStateMachine.CodeValue;

import boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameStateMachine.Variable.VarBool;
import boardGameStateMachine.Variable.VarDouble;
import boardGameStateMachine.Variable.VarInt;
import boardGameStateMachine.Variable.VarType;
import boardGameStateMachine.Variable.Variable;

/**
 * Mangages a CodeValue which is base level
 * @author Peter
 *
 */

public class CodeValuePlainVariable extends CodeValue {

	Variable storedVariable = null;
	String idName = null;

	public CodeValuePlainVariable(int i) {
		super(VarType.Int);
		storedVariable = new VarInt(i);
	}

	public CodeValuePlainVariable(double d) {
		super(VarType.Double);
		storedVariable = new VarDouble(d);
	}

	public CodeValuePlainVariable(boolean b) {
		super(VarType.Boolean);
		storedVariable = new VarBool(b);
	}

	public CodeValuePlainVariable(VarType vt, String name) {
		super(vt);
		this.idName = name;
	}

	@Override
	public Variable getValue(MultiScopeVariableManager scope) {
		if (!(idName == null)) {
			return storedVariable;
		} else {
			return scope.getVariableGameObject(idName);
		}

	}

}
