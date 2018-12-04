package boardGameSimulator.model.boardGameRecording;

import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.ActionRound;

public class ActionRecord {

	private ActionRound a;
	private Variable[] arguments;

	public ActionRecord(ActionRound ar, Variable[] arguments) {
		this.a = ar;
		this.arguments = arguments;

	}

	public Variable executeAction() {
		if (arguments == null) {
			return a.getValue(new Variable[0]);
		} else {
			return a.getValue(arguments);
		}

	}

}
