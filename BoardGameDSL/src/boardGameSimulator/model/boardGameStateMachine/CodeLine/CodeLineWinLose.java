package boardGameSimulator.model.boardGameStateMachine.CodeLine;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturnType;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarPlayer;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Player;
import boardGameSimulator.model.boardGameStateMachine.stateModel.PlayerRanking;

public class CodeLineWinLose extends CodeLine{
	
	
	private CodeValue player;
	private boolean hasWon;

	public CodeLineWinLose(CodeValue player, boolean hasWon){
		this.player = player;
		this.hasWon = hasWon;
	}

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		Player calculatedPlayer = ((VarPlayer) player.getValue(scope)).getValue();
		PlayerRanking rank= calculatedPlayer.getRanking();
		
		if(hasWon){
			rank.setWinner();
		}else{
			rank.setLoser();
		}
		
		return new CodeLineReturn(CodeLineReturnType.Empty,false);
	}
	
	
	

}
