package boardGameSimulator.model.boardGameStateMachine.CodeValue;

import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarBool;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarDouble;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarInt;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarType;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

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
		if ((idName == null)) {
			return storedVariable;
		} else {
			return scope.getVariableGameObject(idName);
		}

	}

}
