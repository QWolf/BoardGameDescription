package boardGameSimulator.model.boardGameStateMachine.CodeLine;

import boardGameSimulator.controller.StateMachineController;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturnType;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarGameObject;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Game;
import boardGameSimulator.model.boardGameStateMachine.stateModel.GameObjectInstance;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Randomizer;

public class CodeLineRandomize extends CodeLine{
	
	private CodeValue object;
	private Game game;

	public CodeLineRandomize(CodeValue object, Game g){
		this.object = object;
		this.game = g;
	}

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		GameObjectInstance calculatedObject = ((VarGameObject) object.getValue(scope)).getValue();
		Randomizer r = calculatedObject.getRandomizer();
		StateMachineController smc = game.getStateMachineController();
		if (smc.usePredeterminedRandomResults()){
			r.setValue(smc.getNextRandomResult());
			
		}else{
			r.Randomize();
		}
		smc.writeRandom(r.getValue());
		
		return new CodeLineReturn(CodeLineReturnType.Empty,false);
	}

}
