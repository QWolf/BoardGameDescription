package boardGameStateMachine.CodeLine;

import boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameStateMachine.CodeLine.Return.CodeLineReturnType;
import boardGameStateMachine.CodeValue.CodeValue;
import boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameStateMachine.Variable.VarGameObject;
import boardGameStateMachine.Variable.VarLocation;
import boardGameStateMachine.stateModel.GameObjectInstance;
import boardGameStateMachine.stateModel.Location;

public class CodeLineMoveObjectTo extends CodeLine{
	
	private CodeValue obj;
	private CodeValue loc;

	/**
	 * Moves an object to a new location
	 * @param object	Object to be moved
	 * @param location	Target Location
	 */
	public CodeLineMoveObjectTo(CodeValue object, CodeValue location){
		this.obj = object;
		this.loc = location;
	}

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		GameObjectInstance calculatedObject = ((VarGameObject) obj.getValue(scope)).getValue();
		Location calculatedLocation = ((VarLocation) loc.getValue(scope)).getValue();
		calculatedObject.moveTo(calculatedLocation);
		
		return new CodeLineReturn(CodeLineReturnType.Empty, false);
	}

}
