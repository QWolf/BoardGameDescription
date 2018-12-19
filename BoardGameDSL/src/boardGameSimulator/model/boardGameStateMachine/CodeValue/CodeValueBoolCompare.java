package boardGameSimulator.model.boardGameStateMachine.CodeValue;

import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarBool;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarDouble;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarGameObject;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarInt;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarLocation;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarOwner;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarPlayer;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarType;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarOwner.OwnerType;
import boardGameSimulator.model.boardGameStateMachine.stateModel.GameObjectInstance;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Location;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Owner;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Player;

public class CodeValueBoolCompare extends CodeValue {

	public enum CompareOperator {
		LT, LTE, EQ, NE, GT, GTE;
	}

	private CodeValue par2;
	private CompareOperator co;
	private CodeValue par1;

	public CodeValueBoolCompare(CodeValue par1, CompareOperator co, CodeValue par2) {
		super(VarType.Boolean);
		this.par1 = par1;
		this.co = co;
		this.par2 = par2;

	}

	@Override
	public Variable getValue(MultiScopeVariableManager scope) {
		Variable calculatedPar1 = par1.getValue(scope);
		Variable calculatedPar2 = par2.getValue(scope);
		boolean returnValue = false;
		
//		System.out.println("---------CVBool-----");
//		System.out.println(calculatedPar1);
//		System.out.println(calculatedPar2);
//		System.out.println(co);

		switch (calculatedPar1.getVarType()) {
		case Boolean:
			boolean boolPar1 = ((VarBool) calculatedPar1).getValue();
			boolean boolPar2 = ((VarBool) calculatedPar2).getValue();
			switch (co) {
			case EQ:
				returnValue = boolPar1 == boolPar2;
				break;
			case NE:
				returnValue = boolPar1 != boolPar2;
				break;
			default:
				System.out.println("Invalid compare operator for Booleans!");
				return null;
			}

			break;

		case Double:
			double doublePar1 = ((VarDouble) calculatedPar1).getValue();
			double doublePar2 = 0;
			
			if(calculatedPar2.getVarType() == VarType.Double){
				doublePar2 = ((VarDouble) calculatedPar2).getValue();
			} else if(calculatedPar2.getVarType()==VarType.Int){
				doublePar2 = ((VarInt) calculatedPar2).getValue();
			} else {
				System.out.println("No input for Double (CodeValueBool)");
			}

			returnValue = compareDouble(doublePar1, doublePar2, co);
			break;
		case Int:
			int intPar1 = ((VarInt) calculatedPar1).getValue();
			int intPar2 = 0;

			if(calculatedPar2.getVarType() == VarType.Int){
				intPar2 = ((VarInt) calculatedPar2).getValue();
			} else if(calculatedPar2.getVarType()==VarType.Double){
				intPar2 = (int) Math.round(((VarDouble) calculatedPar2).getValue());
			} else {
				System.out.println("No input for Double (CodeValueBool)");
			}
			
			returnValue = compareDouble((double) intPar1, (double) intPar2, co);
			break;
		// case List:
		// break;
		case Location:
			Location locPar1 = ((VarLocation) calculatedPar1).getValue();
			Location locPar2 = ((VarLocation) calculatedPar2).getValue();
			switch (co) {
			case EQ:
				returnValue = locPar1.getName().equals(locPar2.getName());
				break;
			case NE:
				returnValue = !locPar1.getName().equals(locPar2.getName());
				break;
			default:
				System.out.println("Invalid compare operator for Locations!");
				return null;
			}
			break;
		case Object:
			GameObjectInstance objPar1 = ((VarGameObject) calculatedPar1).getValue();
			GameObjectInstance objPar2 = ((VarGameObject) calculatedPar2).getValue();
			switch (co) {
			case EQ:
				returnValue = objPar1.getName().equals(objPar2.getName());
				break;
			case NE:
				returnValue = !objPar1.getName().equals(objPar2.getName());
				break;
			default:
				System.out.println("Invalid compare operator for Objects!");
				return null;
			}
			break;
		case Owner:
			Owner ownPar1 = ((VarOwner) calculatedPar1).getValue();
			Owner ownPar2 = null;
			
			if(calculatedPar2.getVarType() == VarType.Owner){
				ownPar2 = ((VarOwner) calculatedPar2).getValue();
			} else if(calculatedPar2.getVarType() == VarType.Player){
				ownPar2 = new Owner(((VarPlayer) calculatedPar2).getValue());
			}

			switch (co) {
			case EQ:
				returnValue = ownPar1.isSameOwner(ownPar2);
				break;
			case NE:
				returnValue = !ownPar1.isSameOwner(ownPar2);
				break;
			default:
				System.out.println("Invalid compare operator for Objects!");
				return null;
			}
			break;
		case Player:
			Player playerPar1 = ((VarPlayer) calculatedPar1).getValue();
			Player playerPar2 = null;
			
			if(calculatedPar2.getVarType() == VarType.Player){
				playerPar2 = ((VarPlayer) calculatedPar2).getValue();
			}else if(calculatedPar2.getVarType() == VarType.Owner){
				//If par 2 is owner, get player object out of it, else set null (-> to make it different from par1)
				Owner playerParOwner2 = ((VarOwner)calculatedPar2).getValue();
				if(playerParOwner2.getOwnerType() == OwnerType.Player){
					playerPar2 = playerParOwner2.getPlayerOwner();
				}
			}
			
			switch (co) {
			case EQ:
				returnValue = playerPar1.getName() == playerPar2.getName();
				break;
			case NE:
				returnValue = !(playerPar1.getName() == playerPar2.getName());
				break;
			default:
				System.out.println("Invalid compare operator for Players!");
				return null;
			}
			break;
		// case Round:
		// break;
		// case Unspecified:
		// break;
		default:
			// TODO proper error handling
			System.out.println("Variable type could not be compared!");
			return null;
		}

		
//		System.out.println("CVBoolCompare Result: "+ returnValue);
		return new VarBool(returnValue);

	}

	private boolean compareDouble(double par1, double par2, CompareOperator compop) {
		switch (compop) {
		case EQ:
			return par1 == par2;
		case GT:
			return par1 > par2;
		case GTE:
			return par1 >= par2;
		case LT:
			return par1 < par2;
		case LTE:
			return par1 <= par2;
		case NE:
			return par1 != par2;
		default:
			System.out.println("Invalid compare type for Double!");
			return false;

		}
	}

}
