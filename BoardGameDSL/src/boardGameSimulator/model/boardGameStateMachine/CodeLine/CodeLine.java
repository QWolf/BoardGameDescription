package boardGameSimulator.model.boardGameStateMachine.CodeLine;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturnType;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;

public abstract class CodeLine {

	public static CodeLineReturn executeCodeBlockUntilReturn(CodeLine[] lines, MultiScopeVariableManager scope) {
		CodeLineReturn clr = new CodeLineReturn(CodeLineReturnType.Empty, false);
		int i = 0;
		
		while(i < lines.length && !clr.isDone()){
			clr = lines[i].execute(scope);
			i++;
		}
		return clr;
	}
	
	
	public abstract CodeLineReturn execute(MultiScopeVariableManager scope);

}
