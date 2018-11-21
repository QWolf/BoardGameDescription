package boardGameStateMachine.actionModel.CodeLine;

public abstract class CodeLine {
	
	private CodeLineType type;
	

	public CodeLine (CodeLineType t){
		this.type = t;
	}
	

	public CodeLineType getCodeLineType() {
		return type;
	}
	
	public static Object executeCodeBlockUntilReturn(CodeLine[] lines){
		for(CodeLine line : lines){
			if(line.getCodeLineType() == CodeLineType.Return){
				return line.execute();
			}else{
				line.execute();
			}
		}
		return null;
	}

	
	public abstract Object execute();

}
