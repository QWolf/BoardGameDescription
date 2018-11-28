package boardGameStateMachine.CodeValue;

import boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameStateMachine.Variable.VarBool;
import boardGameStateMachine.Variable.VarLocation;
import boardGameStateMachine.Variable.VarType;
import boardGameStateMachine.Variable.Variable;
import boardGameStateMachine.stateModel.Location;

public class CodeValueLocationFunction extends CodeValue {
	public enum LocationFunction {
		Contains, isConnected, Connections;

		public static VarType getVarType(LocationFunction lf) {
			switch (lf) {
			case Connections:
				return VarType.List;
			case Contains:
				return VarType.List;
			case isConnected:
				return VarType.Boolean;
			default:
				return null;

			}
		}
	}

	private LocationFunction function;
	private CodeValue par1;
	private CodeValue location;

	/**
	 * CodeValue for the standard functions for a location object
	 * 
	 * @param lf
	 *            Function represented by this codevalue
	 * @param location
	 *            The location the function is executed on, as codeValue
	 * @param par1
	 *            First parameter of the function, if applicable, as codeValue
	 */
	public CodeValueLocationFunction(LocationFunction lf, CodeValue location, CodeValue par1) {
		super(LocationFunction.getVarType(lf));
		this.function = lf;
		this.location = location;
		this.par1 = par1;
		// this.game = g;
	}

	@Override
	public Variable getValue(MultiScopeVariableManager scope) {
		Location calculatedLocation = ((VarLocation) location.getValue(scope)).getValue();
		switch (function) {
		case Connections:
			return calculatedLocation.getConnections();
		case Contains:
			return calculatedLocation.getInventory();
		case isConnected:
			Location calculatedPar1 = ((VarLocation) par1.getValue(scope)).getValue();
			return new VarBool(calculatedLocation.isConnectedTo(calculatedPar1));
		default:
			return null;

		}

	}

}
