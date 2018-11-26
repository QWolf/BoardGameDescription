package boardGameStateMachine.Variable;

public class VarInt extends Variable{
	int value = 0;

	public VarInt() {
		super(VarType.Int);
	}
	public VarInt(int i){
		super(VarType.Int);
		value = i;
	}
	
	public int getValue(){
		return value;
	}
	
	public void setValue(int i){
		value = i;
	}
}
