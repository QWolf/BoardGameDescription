package boardGameStateMachine.CodeLine;

import boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameStateMachine.CodeValue.CodeValue;
import boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameStateMachine.Variable.Variable;

public class CodeLineReturnLine extends CodeLine {

	private CodeValue par1;
	
	/**
	 * CodeLine which resembles a return statement. Returns a Variable
	 * @param cv
	 */
	public CodeLineReturnLine(CodeValue cv) {
		this.par1 = cv;
	}

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		Variable var = par1.getValue(scope);

		return new CodeLineReturn(var, true);
	}

}
