package boardGameSimulator.model.boardGameStateMachine.CodeLine;

import boardGameSimulator.controller.StateMachineController;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarPlayer;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.ActionRound;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Game;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Player;

public class CodeLineChooseAction extends CodeLine{
	
	
	private CodeValue player;
	private Game game;

	
	/**
	 * CodeLine for the Choose Action line
	 * @param cv	The player that has to make the choice, as CodeValue
	 * @param g		The game this line is placed in
	 */
	public CodeLineChooseAction(CodeValue cv, Game g){
		this.player = cv;
		this.game = g;
		
	}

	
	/**
	 * Executes this line of code
	 */
	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		//Calculate player and SMC
		Player calculatedPlayer = ((VarPlayer) player.getValue(scope)).getValue();
		StateMachineController smc = game.getStateMachineController();
		
		//Ask the SMC for a action to perform - either live or from a file
		System.out.println("Asking for Action (CLChooseAction)");
		ActionRound ar = smc.getNextAction(calculatedPlayer);
		Variable[] parameters = smc.getNextActionParameters(scope);
		
//		Variable[] parsAsVars = new Variable[parameters.length];
//		for(int i = 0; i<parsAsVars.length; i++){
//			parsAsVars[i] = parameters[i].getVariable(scope);
//		}
		CodeLineReturn answer = ar.executeActionRound(parameters);
		
		//Write action to the game log
		smc.writeAction(ar.getName(), calculatedPlayer.getName(), parameters);
		
		return answer;
	}

}
