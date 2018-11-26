package boardGameStateMachine.actionModel.CodeLine;

import boardGameStateMachine.actionModel.CodeValue.CodeValue;
import boardGameStateMachine.actionModel.CodeValue.CodeValueBool;
import boardGameStateMachine.stateModel.Action;
import boardGameStateMachine.util.IDType;

public class IfElseStatement extends CodeLine {

	private CodeValueBool statement;
	private CodeLine[] linesTrue;
	private CodeLine[] linesFalse;

	public IfElseStatement(Action a, CodeValue statement, CodeLine[] blockTrue, CodeLine[] blockFalse) throws Exception {
		super(CodeLineType.IfElse, a);
		if (!statement.getIDType().equals(IDType.Boolean)) {
			throw new Exception("IfStatementNoBooleanStatement");
		}
		this.statement = (CodeValueBool) statement;
		this.linesTrue = blockTrue;
		this.linesFalse = blockFalse;

	}

	@Override
	public CodeValue execute() {
		if (statement.getValue() == true) {
			return executeCodeBlockUntilReturn(linesTrue);
		}else{
			return executeCodeBlockUntilReturn(linesFalse);
		}
	}
}
