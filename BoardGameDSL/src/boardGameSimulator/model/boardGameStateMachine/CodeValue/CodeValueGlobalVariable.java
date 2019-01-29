package boardGameSimulator.model.boardGameStateMachine.CodeValue;

import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarList;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarPlayer;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarType;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Game;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Player;

public class CodeValueGlobalVariable extends CodeValue {
	public enum GlobalFunction {
		TurnOrder;

		public static VarType getVarType(GlobalFunction gf) {
			switch (gf) {
			case TurnOrder:
				return VarType.List;
			default:
				return null;
			}
		}
	}
	
	
	private GlobalFunction function;
	private Game g;
	public CodeValueGlobalVariable(GlobalFunction gf, Game g) {
		super(GlobalFunction.getVarType(gf));
		this.function = gf;
		this.g = g;
	}

	@Override
	public Variable getValue(MultiScopeVariableManager scope) {
		Variable v = null;
		
		switch(function){
		case TurnOrder:
			Player[] turnOrder = g.getTurnOrder();
			Variable[] turnOrderAsVarList = new Variable[turnOrder.length];
			for(int i = 0; i<turnOrder.length; i++){
				turnOrderAsVarList[i] = new VarPlayer(turnOrder[i]);
			}
			
			v = new VarList(turnOrderAsVarList);
			break;
		default:
			System.out.println("Error, Global Variable had no type!");
			break;
		
		}
			
		return v;
	}

}
