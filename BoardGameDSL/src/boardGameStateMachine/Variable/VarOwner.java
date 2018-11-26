package boardGameStateMachine.Variable;

import boardGameStateMachine.stateModel.Owner;

public class VarOwner extends Variable{
	Owner value = null;

	public VarOwner() {
		super(VarType.Owner);
	}
	public VarOwner(Owner own){
		super(VarType.Owner);
		value = own;
	}
	
	public Owner getValue(){
		return value;
	}
	
	public void setValue(Owner own){
		value = own;
	}
}
