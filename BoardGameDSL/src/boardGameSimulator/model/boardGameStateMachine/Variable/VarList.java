package boardGameSimulator.model.boardGameStateMachine.Variable;

public class VarList extends Variable{

	Variable[] value = null;

//	public VarList() {
//		super(VarType.List);
//	}
	public VarList(Variable[] vars){
		super(VarType.List);
		value = vars;
		if(value == null){
			System.out.println("VarList is null!!!!");
		}
	}
	
	public Variable[] getValue(){
		return value;
	}
	
	public void setValue(Variable[] list){
		value = list;
	}
	
	public Variable getIndex(int i){
		System.out.println("VarList");
		System.out.println(value);
		System.out.println(value.length);
		for(Variable v : value){
			System.out.println(v);
		}
		
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
