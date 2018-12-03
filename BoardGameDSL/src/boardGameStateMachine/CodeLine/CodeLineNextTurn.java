package boardGameStateMachine.CodeLine;

import boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameStateMachine.CodeLine.Return.CodeLineReturnType;
import boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameStateMachine.stateModel.Game;

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
