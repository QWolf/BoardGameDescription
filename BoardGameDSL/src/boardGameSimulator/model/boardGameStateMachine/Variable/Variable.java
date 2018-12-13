package boardGameSimulator.model.boardGameStateMachine.Variable;

public abstract class Variable {
	
	private VarType varType;
	
	public Variable(VarType vt){
		this.varType = vt;
	}
	
	public VarType getVarType(){
		return varType;
	}
	
	public abstract SingleScopeVariableManager getGameObjectVariableManager();
	
	
	public String toString(){
		
		String buildString = "";
		switch(varType){
		case Boolean:
			buildString = "Bool: "+ ((VarBool) this).getValue();
			break;
		case Double:
			buildString = "Double: "+ ((VarDouble) this).getValue();
			break;
		case Int:
			buildString = "Int: "+ ((VarInt) this).getValue();
			break;
		case List:
			Variable[] list = ((VarList) this).getValue();
			buildString = "List: [";
			
			for(Variable v : list){
				if(v == null){
					buildString+= null + ", ";

				}else{
				buildString+= v.toString() + ", ";
				}
			}
			buildString += "]";
			break;
		case Location:
			buildString = "Location: "+ ((VarLocation) this).getValue().getName();
			break;
		case Object:
			buildString = "Object: "+ ((VarGameObject) this).getValue().getName();
			break;
		case Owner:
			buildString = "Owner: "+ ((VarOwner) this).getValue().toString();
			break;
		case Player:
			buildString = "Player: "+ ((VarPlayer) this).getValue().getName();
			break;
		case Unspecified:
			buildString = "UnspecifiedType";
			break;
		default:
			break;
		
		}
		return buildString;

	}
	
	
//	public static void main(String[] args){
//		Variable[] list = {new VarBool(true), new VarBool(false)};
//		VarList v = new VarList(list);
//		System.out.println(v);
//	}
	


}
