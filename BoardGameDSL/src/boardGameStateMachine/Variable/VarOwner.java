package boardGameStateMachine.Variable;

import boardGameStateMachine.stateModel.Owner;

public class VarOwner extends Variable {
	public enum OwnerType {
		Player, Public, Location;
	}

	Owner value = null;
	OwnerType type = null;

	public VarOwner() {
		super(VarType.Owner);
	}

	public VarOwner(Owner own) {
		super(VarType.Owner);
		value = own;
	}

	// boolean true will result in "Public"
	public VarOwner(OwnerType ot) {
		super(VarType.Owner);
		value = new Owner(ot);

	}

	public Owner getValue() {
		return value;
	}

	public void setValue(OwnerType ot) {
		type = ot;
	}

	public void setValue(Owner own) {
		value = own;
	}

	@Override
	public SingleScopeVariableManager getGameObjectVariableManager() {
		// TODO Owner != Player
		System.out.println("VarOwner has no VariableScope!");
		return null;
	}
}
