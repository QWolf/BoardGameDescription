package boardGameSimulator.model.boardGameStateMachine.CodeLine;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

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
