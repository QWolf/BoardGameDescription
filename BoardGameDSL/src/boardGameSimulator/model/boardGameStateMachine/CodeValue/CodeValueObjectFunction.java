package boardGameSimulator.model.boardGameStateMachine.CodeValue;

import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarGameObject;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarLocation;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarOwner;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarType;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.GameObjectInstance;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Location;

public class CodeValueObjectFunction extends CodeValue {

	public enum ObjectFunction {
		Location, Owner, Value;

		public static VarType getVarType(ObjectFunction of) {
			switch (of) {
			case Location:
				return VarType.Location;
			case Owner:
				return VarType.Owner;
			case Value:
				//TODO no current way of properly handling this one
				return VarType.Unspecified;
			default:
				break;
			}
			return null;
		}
	}

	private ObjectFunction function;
	private CodeValue object;

	/**
	 * A codevalue which supports the standard built-in functions for objects
	 * 
	 * @param of
	 *            ObjectFunction that is represented
	 * @param g
	 *            The Game object
	 * @param o 
	 * 			The Object the function is called on, as CodeValue
	 */
	public CodeValueObjectFunction(ObjectFunction of, CodeValue o) {
		super(ObjectFunction.getVarType(of));
		this.function = of;
		this.object = o;
	}

	@Override
	public Variable getValue(MultiScopeVariableManager scope) {

//		System.out.println(scope.getGlobalScope());
//		System.out.println(scope.getRoundActionScope());
//		System.out.println(scope.getObjectScope());
//		System.out.println("ASDF"+scope.getRoundActionScope().getVariable("l"));
//		System.out.println(object.varType);
//		System.out.println("TheObject! " + object.getValue(scope));
		
		
		//BGD has a bug where the owner function is called here, even if it is an location. Quick workaround
		Variable objOrLoc = object.getValue(scope);
//		System.out.println("ObjOrLoc" + objOrLoc);
		if(objOrLoc.getVarType() == VarType.Location){
			if(function.equals(ObjectFunction.Owner)){
				Location loc = ((VarLocation) objOrLoc).getValue();
				
				return loc.getVarManager().getVariable("Owner");
			}
		}
		
		GameObjectInstance calculatedObject = ((VarGameObject) object.getValue(scope)).getValue();
		
		
		switch (function) {
		case Location:
			return new VarLocation(calculatedObject.getLocation());
		case Owner:
			return calculatedObject.getVarManager().getVariable("Owner");
		case Value:
			Variable value = calculatedObject.getRandomizer().getValue();
			varType = value.getVarType();
			return value;
			
		default:
			System.out.println("CodeValueObjectFunction was null!");
			return null;
		}

	}

}
