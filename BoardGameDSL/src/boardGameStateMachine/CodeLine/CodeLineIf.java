package boardGameStateMachine.CodeLine;

import boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameStateMachine.CodeLine.Return.CodeLineReturnType;
import boardGameStateMachine.CodeValue.CodeValue;
import boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameStateMachine.Variable.VarBool;

public class CodeLineIf extends CodeLine{
	
	
	private CodeLine[] linesfalse;
	private CodeLine[] linestrue;
	private CodeValue condition;

	public CodeLineIf(CodeValue condition, CodeLine[] linestrue){
		this(condition, linestrue, new CodeLine[0]);
	}

	public CodeLineIf(CodeValue condition, CodeLine[] linestrue, CodeLine[] linesfalse) {
		this.condition = condition;
		this.linestrue = linestrue;
		this.linesfalse = linesfalse;
		
	}

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		//Calculate the current value of the condition
		VarBool calculatedCodeValue = (VarBool) condition.getValue(scope);
		
		if(calculatedCodeValue.getValue()){
			//True
			return CodeLine.executeCodeBlockUntilReturn(linestrue, scope);
			
		}else if(linesfalse.length != 0){
			//False
			return CodeLine.executeCodeBlockUntilReturn(linesfalse, scope);

		}else{
			return new CodeLineReturn(CodeLineReturnType.Empty, false);
		}
	}
}
