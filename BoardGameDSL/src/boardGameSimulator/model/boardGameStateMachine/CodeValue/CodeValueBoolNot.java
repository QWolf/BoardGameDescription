package boardGameSimulator.model.boardGameStateMachine.CodeValue;

import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarBool;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarType;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

public class CodeValueBoolNot extends CodeValue{

	private CodeValue toInvert;

	public CodeValueBoolNot(CodeValue toInvert) {
		super(VarType.Boolean);
		this.toInvert = toInvert;
	}

	@Override
	public Variable getValue(MultiScopeVariableManager scope) {
		VarBool calculatedToInvert = (VarBool) toInvert.getValue(scope);
		
		return new VarBool(!calculatedToInvert.getValue());
	}

}
