package boardGameRecording;

import boardGameStateMachine.actionModel.CodeValue.CodeValue;
import boardGameStateMachine.stateModel.Action;

public class ActionRecord {

	private Action a;
	private CodeValue[] arguments;

	public CodeValue executeAction() {
		if (arguments == null) {
			return a.getValue(null);
		} else {
			Object[] calculatedArguments = (Object[]) arguments;
			for (int i = 0; i < arguments.length -1; i++){
				calculatedArguments[i] = arguments[i].g
			}
			
		}

		return a.getValue(arguments);
	}

}
