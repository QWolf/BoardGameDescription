package boardGameSimulator.model.boardGameStateMachine.CodeLine;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturnType;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarList;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarPlayer;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarType;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Game;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Player;

public class CodeLineSetTurnOrder extends CodeLine {

	private CodeValue newTurnOrderList;
	private Game game;

	public CodeLineSetTurnOrder(CodeValue par1, Game g) {
		this.newTurnOrderList = par1;
		this.game = g;
	}

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		Variable calculatedList = newTurnOrderList.getValue(scope);
		VarList calcAsVarList = (VarList) calculatedList;

		for (Variable v : calcAsVarList.getValue()) {
			if (v.getVarType() != VarType.Player) {
				System.err.println("Error: TurnOrder contains non-Player variable!");
			}
		}
		Player[] playerList = new Player[calcAsVarList.getValue().length];
		for (int i = 0; i < playerList.length; i++) {
			playerList[i] = ((VarPlayer) calcAsVarList.getIndex(i)).getValue();
		}

		game.setTurnOrder(playerList);

		return new CodeLineReturn(CodeLineReturnType.Empty, false);
	}

}
