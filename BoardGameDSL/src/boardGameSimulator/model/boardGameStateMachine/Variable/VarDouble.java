package boardGameSimulator.model.boardGameStateMachine.Variable;

public class VarDouble extends Variable{
	double value = 0;

	public VarDouble() {
		super(VarType.Double);
	}
	public VarDouble(double d){
		super(VarType.Double);
		value = d;
	}
	
	public double getValue(){
		return value;
	}
	
	public void setValue(double d){
		value = d;
	}
	@Override
	public SingleScopeVariableManager getGameObjectVariableManager() {
		System.out.println("VarDouble has no VariableScope!");
		return null;
	}
}
