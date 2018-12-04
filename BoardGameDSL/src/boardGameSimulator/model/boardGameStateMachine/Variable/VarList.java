package boardGameSimulator.model.boardGameStateMachine.Variable;

public class VarList extends Variable{

	Variable[] value = null;

//	public VarList() {
//		super(VarType.List);
//	}
	public VarList(Variable[] vars){
		super(VarType.List);
		value = vars;
	}
	
	public Variable[] getValue(){
		return value;
	}
	
	public void setValue(Variable[] list){
		value = list;
	}
	
	public Variable getIndex(int i){
		return value[i];
	}

	@Override
	public SingleScopeVariableManager getGameObjectVariableManager() {
		System.out.println("VarList has no VariableScope!");
		return null;
	}
	public Variable getSize() {
		if(value == null){
			return new VarInt(0);
		}else{
			return new VarInt(value.length);
		}
	}
}
