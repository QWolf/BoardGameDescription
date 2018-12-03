package boardGameStateMachine.CodeLine;

import boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameStateMachine.CodeLine.Return.CodeLineReturnType;
import boardGameStateMachine.Variable.MultiScopeVariableManager;

public abstract class CodeLine {

	public static CodeLineReturn executeCodeBlockUntilReturn(CodeLine[] lines, MultiScopeVariableManager scope) {
		CodeLineReturn clr = new CodeLineReturn(CodeLineReturnType.Empty, false);
		int i = 0;
		
		while(i < lines.length -1 && !clr.isDone()){
			clr = lines[i].execute(scope);
		}
		return clr;
	}
	
	
	public abstract CodeLineReturn execute(MultiScopeVariableManager scope);

}
