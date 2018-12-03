package boardGameStateMachine.CodeLine;

import boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameStateMachine.CodeLine.Return.CodeLineReturnType;
import boardGameStateMachine.CodeValue.CodeValue;
import boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameStateMachine.Variable.VarPlayer;
import boardGameStateMachine.stateModel.Player;
import boardGameStateMachine.stateModel.PlayerRanking;

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
