package boardGameSimulator.model.boardGameStateMachine.CodeLine;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturnType;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Game;

public class CodeLineNextTurn extends CodeLine{
	
	private Game game;

	public CodeLineNextTurn(Game g){
		this.game = g;
	}

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		game.advanceTurn(1);
		return new CodeLineReturn(CodeLineReturnType.Empty,false);
	}

}
