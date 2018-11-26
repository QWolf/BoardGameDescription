package boardGameStateMachine.actionModel.CodeLine;

import boardGameStateMachine.actionModel.CodeValue.CodeValue;
import boardGameStateMachine.stateModel.Action;
import boardGameStateMachine.stateModel.Game;

public abstract class CodeLine {
	
	private CodeLineType type;
	private Action imbeddedInAction;
	private Game g;
	

	public CodeLine (CodeLineType t, Action a, Game g){
		this.type = t;
		this.imbeddedInAction = a;
	}
	

	public CodeLineType getCodeLineType() {
		return type;
	}
	
	public static CodeValue executeCodeBlockUntilReturn(CodeLine[] lines){
		for(CodeLine line : lines){
			if(line.getCodeLineType() == CodeLineType.Return){
				return line.execute();
			}else{
				line.execute();
			}
		}
		return null;
	}

	
	public abstract CodeValue execute();

}
