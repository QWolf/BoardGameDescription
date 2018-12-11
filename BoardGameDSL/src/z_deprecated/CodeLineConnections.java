package z_deprecated;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarLocation;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Location;

public class CodeLineConnections extends CodeLine{
	private CodeValue location;
	public CodeLineConnections(CodeValue loc){
		this.location = loc;
	}
	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		Location calculatedLocation = ((VarLocation) location.getValue(scope)).getValue();
		Variable returnvar = calculatedLocation.getConnections();
		
		return new CodeLineReturn(returnvar, false);
	}

}

