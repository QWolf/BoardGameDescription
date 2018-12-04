package z_deprecated.deprecatedBoardGameStateMachine.actionModel.CodeLine;

import boardGameSimulator.model.boardGameStateMachine.actionModel.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Action;

public class ChooseActionStatement extends CodeLine{
	
	GameRecording gr;

	public ChooseActionStatement(Action a, GameRecording gr) {
		super(CodeLineType.ChooseAction, a);
		this.gr = gr;
	}
	
	
	public CodeValue execute(Action a, CodeValue[] arguments) throws Exception{
		return a.execute(arguments);
	}


	@Override
	public CodeValue execute(){
		System.out.println("No Action given to execute!");

		return null;
	}

}
