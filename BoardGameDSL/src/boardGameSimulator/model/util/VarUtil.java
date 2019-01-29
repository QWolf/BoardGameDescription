package boardGameSimulator.model.util;

import boardGameSimulator.model.boardGameStateMachine.Variable.VarList;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarPlayer;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Player;

public class VarUtil {

	public static VarList playerArrayToVarList(Player[] array) {
		Variable[] vars = new Variable[array.length];
		for(int i = 0; i<vars.length; i++){
			vars[i] = new VarPlayer(array[i]);
		}
		
		return new VarList(vars);
		
	}

}
