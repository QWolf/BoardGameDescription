package boardGameSimulator.controller;

import boardGameSimulator.model.boardGameRecording.GameRecording;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.RawVariable;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.ActionRound;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Game;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Player;
import boardGameSimulator.view.ViewInterface;

public class StateMachineController {

	private boolean usePredeterminedRandomResults = false;
	private boolean usePredeterminedActionsAndParameters = false;
	private GameRecording preRecorded;
	private GameRecording currentRecording = new GameRecording();
	private Game game;
	private ViewInterface view;

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

	public boolean usePredeterminedActionsAndParameters() {
		return usePredeterminedActionsAndParameters;
	}

	public Variable getNextRandomResult() {
		// TODO!!!!
		return null;
	}

	public void writeRandom(Variable value) {
		// TODO Auto-generated method stub

	}

	public ActionRound getNextAction(Player calculatedPlayer) {
		String name;

		if (usePredeterminedActionsAndParameters) {
			name = preRecorded.getNextActionName();

		} else {
			name = view.getActionName();
		}
		ActionRound action = game.getActionRound(name);
		return action;
	}

	public RawVariable[] getNextActionParameters(MultiScopeVariableManager scope) {
		// TODO Auto-generated method stub
		return new RawVariable[0];
	}

	public void writeAction(String ActionRound, String Player, RawVariable[] parameters) {
		// TODO Auto-generated method stub
		// Advance prerecorded, if required

	}

	public GameRecording getCurrentRecording() {
		return currentRecording;
	}

}
