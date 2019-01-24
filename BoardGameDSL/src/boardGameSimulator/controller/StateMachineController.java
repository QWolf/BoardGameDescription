package boardGameSimulator.controller;

import java.io.FileReader;

import boardGameSimulator.model.boardGameRecording.GameRecording;
import boardGameSimulator.model.boardGameRecording.RecordingParser;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.ActionRound;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Game;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Player;
import boardGameSimulator.view.Main;
import boardGameSimulator.view.ViewInterface;


public class StateMachineController {

	private boolean usePredeterminedRandomResults = false;
	private boolean usePredeterminedActionsAndParameters = false;
	private GameRecording preRecorded;
	private GameRecording currentRecording = new GameRecording();
	private Game game;
	private ViewInterface view;
	private boolean hasStarted = false;

	public StateMachineController(GameRecording record, Game g, ViewInterface vi) {
		this.preRecorded = record;
		this.usePredeterminedActionsAndParameters = true;
		this.usePredeterminedRandomResults = true;
		this.game = g;
		this.view = vi;
	}

	public StateMachineController(Game g, ViewInterface vi) {
		// No record
		this.game = g;
		this.view = vi;
	}

	public boolean usePredeterminedRandomResults() {
		return usePredeterminedRandomResults;
	}
	
	public void loadRecord(FileReader fr){
		this.preRecorded = RecordingParser.parseFile(fr);
		if(preRecorded == null){
			return;
		}
		usePredeterminedActionsAndParameters = true;
		usePredeterminedRandomResults = true;
	}
	
	public boolean usePredeterminedActionsAndParameters() {
		return usePredeterminedActionsAndParameters;
	}

	public int getNextRandomResult() {
		return preRecorded.getNextRandomVariable();

	}

	public void writeRandom(int i, Variable value) {
		currentRecording.writeRandom(i, value);
		if(usePredeterminedRandomResults){
			preRecorded.advanceRandom();
		}

	}

	public ActionRound getNextAction(Player calculatedPlayer) {
		String name;

		if (usePredeterminedActionsAndParameters) {
			name = preRecorded.getNextActionName();

		} else {
			System.out.println("Choose Action Player: " + game.getCurrentTurn().getName());
			name = view.getActionName();
			view.clearNextActionName();
		}
		ActionRound action = game.getActionRound(name);
		return action;
	}

	public Variable[] getNextActionParameters(MultiScopeVariableManager scope) {
		Variable [] parameters;
		if (usePredeterminedActionsAndParameters) {
			String[] stringParameters = preRecorded.getNextActionParameters();
			parameters = new Variable[stringParameters.length];
			for(int i = 0; i<parameters.length; i++){
				parameters[i] = Main.parseVariable(game, stringParameters[i]);
			}

		} else {
			parameters = view.getActionParameters();
			
		}

		return parameters;	}
	
	public String[] getNextActionParametersAsString(MultiScopeVariableManager scope){
		Variable[] pars =  getNextActionParameters(scope);
		String[] parAsString = new String[pars.length];
		for(int i = 0; i<pars.length; i++){
			parAsString[i] = pars[i].toString();
		}
		return parAsString;
	}

	public void writeAction(String actionround, String player, String[] parameters) {
		currentRecording.writeAction(actionround, player, parameters);
		if(usePredeterminedActionsAndParameters()){
			preRecorded.advanceActions();
		}
		

	}

	public GameRecording getCurrentRecording() {
		return currentRecording;
	}

	public boolean HasStarted() {
		return hasStarted;
	}

	public void startGame() {
		this.hasStarted = true;
		game.getActionRound("Main").executeActionRound();
		game.printWinnersAndLosers();
	}

	public String getGameRecordingSorted() {
		return currentRecording.printGameRecordSorted();
		
	}

	public String getGameRecordingUnsorted() {
		return currentRecording.printGameRecordUnsorted();
		
	}
}
