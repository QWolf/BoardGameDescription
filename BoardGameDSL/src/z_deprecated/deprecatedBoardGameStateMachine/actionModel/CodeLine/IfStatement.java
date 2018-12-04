package z_deprecated.deprecatedBoardGameStateMachine.actionModel.CodeLine;

import boardGameSimulator.model.boardGameStateMachine.actionModel.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.actionModel.CodeValue.CodeValueBool;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Action;
import boardGameSimulator.model.boardGameStateMachine.util.IDType;

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
