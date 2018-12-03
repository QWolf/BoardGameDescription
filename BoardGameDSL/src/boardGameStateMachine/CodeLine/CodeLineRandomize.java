package boardGameStateMachine.CodeLine;

import boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameStateMachine.CodeValue.CodeValue;
import boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameStateMachine.Variable.VarLocation;
import boardGameStateMachine.stateModel.Location;

public class CodeLineRandomize extends CodeLine{
	
	private CodeValue location;

	public CodeLineRandomize(CodeValue location){
		this.location = location;
	}

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		Location calculatedLocation = ((VarLocation) location.getValue(scope)).getValue();
		Randomizer
		
		return null;
	}

}
