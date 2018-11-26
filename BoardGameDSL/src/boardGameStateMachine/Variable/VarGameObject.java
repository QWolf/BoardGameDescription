package boardGameStateMachine.Variable;

import boardGameStateMachine.stateModel.GameObjectInstance;

public class VarGameObject extends Variable{
	GameObjectInstance value = null;

	public VarGameObject() {
		super(VarType.Object);
	}
	public VarGameObject(GameObjectInstance obj){
		super(VarType.Object);
		value = obj;
	}
	
	public GameObjectInstance getValue(){
		return value;
	}
	
	public void setValue(GameObjectInstance obj){
		value = obj;
	}
	
	@Override
	public SingleScopeVariableManager getGameObjectVariableManager() {
		return value.getVarManager();
	}
}
