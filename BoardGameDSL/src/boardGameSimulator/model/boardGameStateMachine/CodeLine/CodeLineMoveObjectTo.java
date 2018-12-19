package boardGameSimulator.model.boardGameStateMachine.CodeLine;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturnType;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarGameObject;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarLocation;
import boardGameSimulator.model.boardGameStateMachine.stateModel.GameObjectInstance;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Location;

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
		
//		System.out.println("CodeLineMOve------------------------------");
//		System.out.println(object);
//		System.out.println(location);
	}

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
//		System.out.println("CodeLineMOve------------------------------");
//		System.out.println(obj);
//		System.out.println(loc);		
		GameObjectInstance calculatedObject = ((VarGameObject) obj.getValue(scope)).getValue();
		Location calculatedLocation = ((VarLocation) loc.getValue(scope)).getValue();
		
//		System.out.println("CALCOBJ " + calculatedObject);
//		System.out.println("CALCLOC "+ calculatedLocation);
		
		calculatedObject.moveTo(calculatedLocation);
		
		return new CodeLineReturn(CodeLineReturnType.Empty, false);
	}

}
