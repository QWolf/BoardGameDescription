package boardGameSimulator.model.boardGameStateMachine.CodeValue;

import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarBool;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarLocation;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarType;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Location;

public class CodeValueLocationFunction extends CodeValue {
	public enum LocationFunction {
		Contains, isConnected, Connections, ValueVisible, ExistVisible;

		public static VarType getVarType(LocationFunction lf) {
			switch (lf) {
			case Connections:
				return VarType.List;
			case Contains:
				return VarType.List;
			case isConnected:
				return VarType.Boolean;
			case ExistVisible:
				return VarType.Boolean;
			case ValueVisible:
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
		case ExistVisible:
			return  calculatedLocation.getExistVisibleList();
		case ValueVisible:
			return calculatedLocation.getValueVisibleList();			
		default:
			return null;

		}

	}

}
