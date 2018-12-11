package z_deprecated;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarGameObject;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.GameObjectInstance;

public class CodeLineObjectFunctionOwner extends CodeLine{
	CodeValue object;

	public CodeLineObjectFunctionOwner(CodeValue object) {
		this.object = object;
	}

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		GameObjectInstance calculatedObject = ((VarGameObject) object.getValue(scope)).getValue();
		Variable returnvar = calculatedObject.getVarManager().getVariable("Owner");

		return new CodeLineReturn(returnvar, false);
	}
}
