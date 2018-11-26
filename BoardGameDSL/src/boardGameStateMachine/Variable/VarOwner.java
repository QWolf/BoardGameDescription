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
	
	//boolean true will result in "Public"
	public VarOwner(boolean b) {
		super(VarType.Owner);
		value = new Owner(b);

	}
	
	
	public Owner getValue(){
		return value;
	}
	
	public void setValue(Owner own){
		value = own;
	}
	@Override
	public SingleScopeVariableManager getGameObjectVariableManager() {
		//TODO Owner != Player
		System.out.println("VarOwner has no VariableScope!");
		return null;
	}
}
