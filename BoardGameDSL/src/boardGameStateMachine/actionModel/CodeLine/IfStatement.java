package boardGameStateMachine.actionModel.CodeLine;

import boardGameStateMachine.actionModel.CodeValue.CodeValue;
import boardGameStateMachine.actionModel.CodeValue.CodeValueBool;
import boardGameStateMachine.util.VariableType;

public class IfStatement extends CodeLine {

	private CodeValueBool statement;
	private CodeLine[] linesTrue;

	public IfStatement(CodeLineType t, CodeValue statement, CodeLine[] blockTrue) throws Exception {
		super(t);
		if (!statement.getCodeValueVariable().equals(VariableType.BOOL)) {
			throw new Exception("IfStatementNoBooleanStatement");
		}
		this.statement = (CodeValueBool) statement;
		this.linesTrue = blockTrue;

	}

	@Override
	public void execute() {
		if (statement.getValue() == true) {
			for (CodeLine line : linesTrue) {
				line.execute();
			}
		}
	}
}
