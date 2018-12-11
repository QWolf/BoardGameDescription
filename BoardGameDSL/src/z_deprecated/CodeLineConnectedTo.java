package z_deprecated;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarBool;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarLocation;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Game;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Location;

public class CodeLineConnectedTo extends CodeLine{
	private CodeValue location;
	private String id;
	private Game game;
	public CodeLineConnectedTo(CodeValue loc, String id, Game g){
		this.location = loc;
		this.id = id;
		this.game = g;
	}
	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		Location calculatedLocation = ((VarLocation) location.getValue(scope)).getValue();
		Location calculatedTargetLocation = game.getLocation(id);
		boolean isConnected = calculatedLocation.isConnectedTo(calculatedTargetLocation);
		Variable returnvar = new VarBool(isConnected);
		return new CodeLineReturn(returnvar, false);
	}

}

