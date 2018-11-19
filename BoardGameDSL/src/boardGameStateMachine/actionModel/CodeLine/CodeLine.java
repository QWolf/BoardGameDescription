package boardGameStateMachine.actionModel.CodeLine;

public abstract class CodeLine {
	
	private CodeLineType type;
	

	public CodeLine (CodeLineType t){
		this.type = t;
	}
	

	public CodeLineType getCodeLineType() {
		return type;
	}

	
	public abstract void execute();

}
