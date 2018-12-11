package z_deprecated;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarGameObject;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarLocation;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.GameObjectInstance;

public class CodeLineObjectFunctionLocation extends CodeLine {
	CodeValue object;

	public CodeLineObjectFunctionLocation(CodeValue object) {
		this.object = object;
	}

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		GameObjectInstance calculatedObject = ((VarGameObject) object.getValue(scope)).getValue();
		Variable returnvar = new VarLocation(calculatedObject.getLocation());

		return new CodeLineReturn(returnvar, false);
	}

}
