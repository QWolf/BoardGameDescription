package boardGameSimulator.model.boardGameStateMachine.CodeLine;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

public class CodeLineSetVariable extends CodeLine{
	
	
	private CodeValue variablename;
	private CodeValue variablevalue;

	public CodeLineSetVariable(CodeValue par1, CodeValue par2){
		this.variablename = par1;
		this.variablevalue = par2;
	}

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		Variable calculatedName = variablename.getValue(scope);
		Variable calculatedVariable = variablevalue.getValue(scope);
		
		scope.getVariable(calculatedName.na)
		return null;
	}

}
