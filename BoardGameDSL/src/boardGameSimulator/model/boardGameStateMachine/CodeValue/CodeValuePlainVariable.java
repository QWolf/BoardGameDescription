package boardGameSimulator.model.boardGameStateMachine.CodeValue;

import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarBool;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarDouble;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarInt;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarLocation;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarPlayer;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarType;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Game;

/**
 * Mangages a CodeValue which is base level
 * 
 * @author Peter
 *
 */

public class CodeValuePlainVariable extends CodeValue {

	private Variable storedVariable = null;
	public String idName = null;
	private Game game;

	public CodeValuePlainVariable(int i) {
		super(VarType.Int);
		storedVariable = new VarInt(i);
	}

	public CodeValuePlainVariable(double d) {
		super(VarType.Double);
		storedVariable = new VarDouble(d);
	}

	public CodeValuePlainVariable(boolean b) {
		super(VarType.Boolean);
		storedVariable = new VarBool(b);
	}

	public CodeValuePlainVariable(VarType vt, String name, Game g, String rawSTring) {
		super(vt, rawSTring);
		this.idName = name;
		this.game = g;
	}

	@Override
	public Variable getValue(MultiScopeVariableManager scope) {
		if ((idName == null)) {
			return storedVariable;
		} else if (idName.equals("CurrentTurn")) {
//			System.out.println("CurrentTurn in CVPlain!");
//			System.out.println(game.getCurrentTurn());
			return new VarPlayer(game.getCurrentTurn());
		} else if (scope.getVariable(idName) != null) {
			return scope.getVariable(idName);
		} else if (game.getPlayer(idName) != null) {
			return new VarPlayer(game.getPlayer(idName));
		} else if (game.getLocation(idName) != null) {
			return new VarLocation(game.getLocation(idName));
		}
//		System.out.println("-!-!CodeValuePlainVariable could not be resolved!");
//		System.out.println(scope.getRoundActionScope().getVariable(idName));
//		System.out.println(idName);
//		System.out.println(scope.getRoundActionScope());
//		System.out.println(scope.getRoundActionScope().getVariable("p"));
//		System.out.println("-!-!~~~~");
		return null;

	}

	public String toString() {
		String output = "";
		output += "Stored Variable: " + storedVariable + " Stored IDName: " + idName;
		return output;
	}

}
