package boardGameStateMachine.CodeLine;

import boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameStateMachine.CodeLine.Return.CodeLineReturnType;
import boardGameStateMachine.Variable.MultiScopeVariableManager;

public class CodeLineFinish extends CodeLine{

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		return new CodeLineReturn(CodeLineReturnType.Finished, true);
	}

}
