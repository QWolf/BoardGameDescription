package boardGameSimulator.model.boardGameStateMachine.Variable;

import boardGameSimulator.model.boardGameStateMachine.stateModel.Owner;

public class VarOwner extends Variable {
	public enum OwnerType {
		Player, Public, Location;
	}

	Owner value = null;
	// OwnerType type = null;

	public VarOwner(Owner own) {
		super(VarType.Owner);
		value = own;
	}

// Empty constructor will be public owner
	public VarOwner() {
		super(VarType.Owner);
		value = new Owner();

	}

	public Owner getValue() {
		return value;
	}

	public void setValue(OwnerType ot) {
		// type = ot;
		value = null;
	}

	public void setValue(Owner own) {
		value = own;
	}

	@Override
	public SingleScopeVariableManager getGameObjectVariableManager() {

		if (value.getOwnerType() == VarOwner.OwnerType.Player) {

			return value.getPlayerOwner().getVarManager();
		}
		System.out.println("VarOwner has no VariableScope if it is not a Player!");
		return null;
	}
}
