package boardGameSimulator.model.boardGameStateMachine.CodeLine.Return;

import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

public class CodeLineReturn {

	private CodeLineReturnType returnType;
	private Variable variable = null;

	/**
	 * Signifies if more CodeLines after this should be considered. If true, no
	 * more lines should be checked, as a Return, Repeat or FinishGame has been hit
	 */
	private boolean isDone = false;
	

	public CodeLineReturn(CodeLineReturnType type, boolean isDone) {
		this.returnType = type;
		this.isDone = isDone;
	}

	public CodeLineReturn(Variable v, boolean isDone) {
		this.returnType = CodeLineReturnType.Variable;
		this.variable = v;
		this.isDone = isDone;
	}

	public boolean isEmpty() {
		return returnType == CodeLineReturnType.Empty;
	}

	public boolean isRepeat() {
		return returnType == CodeLineReturnType.Repeat;
	}

	public boolean isVariable() {
		return returnType == CodeLineReturnType.Variable;
	}

	public boolean isFinished() {
		return returnType == CodeLineReturnType.Finished;
	}
	public boolean isInvalidAction() {
		return returnType == CodeLineReturnType.ActionInvalid;
	}
	public Variable getVariable() {
		return variable;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

}
