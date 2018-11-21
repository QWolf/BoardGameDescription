package boardGameStateMachine.actionModel.CodeLine;

import boardGameStateMachine.actionModel.CodeValue.CodeValue;
import boardGameStateMachine.actionModel.CodeValue.CodeValueBool;
import boardGameStateMachine.util.IDType;

public class IfElseStatement extends CodeLine {

	private CodeValueBool statement;
	private CodeLine[] linesTrue;
	private CodeLine[] linesFalse;

	public IfElseStatement(CodeValue statement, CodeLine[] blockTrue, CodeLine[] blockFalse) throws Exception {
		super(CodeLineType.IfElse);
		if (!statement.getIDType().equals(IDType.Boolean)) {
			throw new Exception("IfStatementNoBooleanStatement");
		}
		this.statement = (CodeValueBool) statement;
		this.linesTrue = blockTrue;
		this.linesFalse = blockFalse;

	}

	@Override
	public Object execute() {
		if (statement.getValue() == true) {
			return executeCodeBlockUntilReturn(linesTrue);
		}else{
			return executeCodeBlockUntilReturn(linesFalse);
		}
	}
}
