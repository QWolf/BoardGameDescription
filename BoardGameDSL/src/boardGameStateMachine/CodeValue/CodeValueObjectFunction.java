package boardGameStateMachine.CodeValue;

import boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameStateMachine.Variable.VarGameObject;
import boardGameStateMachine.Variable.VarLocation;
import boardGameStateMachine.Variable.VarType;
import boardGameStateMachine.Variable.Variable;
import boardGameStateMachine.stateModel.GameObjectInstance;

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
		GameObjectInstance calculatedObject = ((VarGameObject) object.getValue(scope)).getValue();
		switch (function) {
		case Location:
			return new VarLocation(calculatedObject.getLocation());
		case Owner:
			return calculatedObject.getVarManager().getVariable("Owner");
		case Value:
			Variable value = calculatedObject.getVarManager().getVariable("Value");
			varType = value.getVarType();
			return value;
			
		default:
			return null;
		}

	}

}
