package boardGameStateMachine.Variable;

import boardGameStateMachine.stateModel.Location;

public class VarLocation extends Variable{
	Location value = null;

	public VarLocation() {
		super(VarType.Location);
	}
	public VarLocation(Location loc){
		super(VarType.Location);
		value = loc;
	}
	
	public Location getValue(){
		return value;
	}
	
	public void setValue(Location loc){
		value = loc;
	}
}
