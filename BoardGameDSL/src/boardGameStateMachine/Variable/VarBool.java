package boardGameStateMachine.Variable;

public class VarBool extends Variable{
	boolean value = false;

	public VarBool() {
		super(VarType.Boolean);
	}
	public VarBool(boolean b){
		super(VarType.Boolean);
		value = b;
	}
	
	public boolean getValue(){
		return value;
	}
	
	public void setValue(boolean b){
		value = b;
	}
	
	@Override
	public SingleScopeVariableManager getGameObjectVariableManager() {
		System.out.println("VarBool has no VariableScope!");
		return null;
	}
}
