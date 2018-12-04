package boardGameSimulator.model.boardGameStateMachine.CodeLine;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturnType;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;

public class CodeLineRepeat extends CodeLine{

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		return new CodeLineReturn(CodeLineReturnType.Repeat, true);
	}

}
