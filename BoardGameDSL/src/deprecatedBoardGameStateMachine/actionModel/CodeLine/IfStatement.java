package boardGameStateMachine.actionModel.CodeLine;

import boardGameStateMachine.actionModel.CodeValue.CodeValue;
import boardGameStateMachine.actionModel.CodeValue.CodeValueBool;
import boardGameStateMachine.stateModel.Action;
import boardGameStateMachine.util.IDType;

public class IfStatement extends CodeLine {

	private CodeValueBool statement;
	private CodeLine[] linesTrue;

	public IfStatement(Action a, CodeValue statement, CodeLine[] blockTrue) throws Exception {
		super(CodeLineType.If, a);
		if (!statement.getIDType().equals(IDType.Boolean)) {
			throw new Exception("IfStatementNoBooleanStatement");
		}
		this.statement = (CodeValueBool) statement;
		this.linesTrue = blockTrue;

	}

	@Override
	public Object execute() {
		if (statement.getValue() == true) {
			return executeCodeBlockUntilReturn(linesTrue);
		}
		return null;
	}
}
