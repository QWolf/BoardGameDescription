package boardGameSimulator.controller;

import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.ActionRound;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Player;

public class StateMachineController {
	
	private boolean usePredeterminedRandomResults = false;
	private boolean usePredeterminedActionsAndParameters = false;
	
	
	
	public boolean usePredeterminedRandomResults(){
		return usePredeterminedRandomResults;
	}
	
	public boolean usePredeterminedActionsAndParameters(){
		return usePredeterminedActionsAndParameters();
	}
	
	public Variable getNextRandomResult(){
		//TODO!!!!
		return null;
	}

	public void writeRandom(Variable value) {
		// TODO Auto-generated method stub
		
	}
	
	public ActionRound getNextAction(Player calculatedPlayer){
		
	}

	public Variable[] getNextActionParameters(MultiScopeVariableManager scope) {
		// TODO Auto-generated method stub
		return new Variable[0];
	}

	public void writeAction(String name, String string, Variable[] parameters) {
		// TODO Auto-generated method stub
		
	}



}
