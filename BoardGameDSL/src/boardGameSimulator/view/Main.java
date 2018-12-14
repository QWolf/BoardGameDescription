package boardGameSimulator.view;

import java.util.Arrays;
import java.util.Scanner;

import bgd.parser.Parser;
import boardGameSimulator.controller.StateMachineController;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarBool;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarDouble;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarGameObject;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarInt;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarLocation;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarPlayer;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Game;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Player;

public class Main implements ViewInterface {

	private StateMachineController smc;
	private Parser p = new Parser();
	private Scanner sc = new Scanner(System.in);
	private Game game;

	boolean stayOpen = true;
	private String actionName;
	private String[] actionArguments;

	public void launchProgram() {
		while (stayOpen) {
			parseInput();
		}
		sc.close();
	}
	
	public void parseInput(){
		String[] args = sc.nextLine().split(" ");
		switch (args[0]) {
		case ("ParseGame"):
			loadGame(args);
		
			break;
		case ("Action"):
			parseAction(args);
			break;
		case ("Setup"):
			if (smc.HasStarted()) {
				System.out.println("Game has already started!");
			} else {
				runSetup(args);
			}
			break;

		case ("StartGame"):
			smc.startGame();
			break;
		 case ("Print"):
			 print(args);
		 break;
		// case ("Action"):
		//
		// break;
		// case ("Action"):
		//
		// break;
		default:
			System.out.println("Unkown input");
		}

	}

	private void runSetup(String[] args) {
		// Sets up which players are actually playing
		if (game.getPlayer(args[1]) != null) {
			game.getPlayer(args[1]).setIsPlaying(true);
			game.getPlayer(args[1]).setPlayerName(args[2]);
			Player[] newTurnOrder = new Player[game.getTurnOrder().length + 1];
			System.arraycopy(game.getTurnOrder(), 0, newTurnOrder, 0, game.getTurnOrder().length);
			newTurnOrder[newTurnOrder.length - 1] = game.getPlayer(args[1]);
			game.setTurnOrder(newTurnOrder);
		} else {
			System.out.println(args[1] + " is not a Player!");
		}

	}
	
	public void print(String[] args){
		if(args[1].equals("Turn")){
			System.out.println("It is currently " + game.getCurrentTurn().getPlayerName() + "'s Turn (" + game.getCurrentTurn().getName() + ")");
		} else if (args[1].equals("Game")){
			game.printExtended();
		}
	}

	public void waitForActionInput() {
		parseInput();

	}

	private boolean parseAction(String[] args) {
		if (args[0].equals("Action") && args.length >= 2) {
			this.actionName = args[1];
			this.actionArguments = Arrays.copyOfRange(args, 2, args.length);
			return true;

		} else {
			return false;
		}
	}

	private void loadGame(String[] args) {

		p = new Parser();
		p.compile(args[1]);
		this.smc = new StateMachineController(p.getGame(), this);
		this.game = p.getGame();
		game.setStateMachineController(smc);
	}

	// public String checkForInput() {
	// return null;
	// }

	public static void main(String[] args) {
		new Main().launchProgram();
	}

	@Override
	public String getActionName() {
		System.out.println("D1 Value: "+ game.getGameObjectInstance("D1").getRandomizer().getValue());
		if (this.actionName != null) {
			return actionName;
		} else {
			waitForActionInput();
			return getActionName();
		}
	}

	@Override
	public Variable[] getParameters() {
		Variable[] vars = new Variable[actionArguments.length];

		for (int i = 0; i < vars.length; i++) {
			vars[i] = parseVariable(actionArguments[i]);
		}

		return vars;
	}

	private Variable parseVariable(String arg) {
		Variable v = null;
		if (arg.equals("True")) {
			v = new VarBool(true);
		} else if (arg.equals("False")) {
			v = new VarBool(false);
		} else {
			try {
				v = new VarInt(Integer.parseInt(arg));
			} catch (NumberFormatException e) {
			}
			if (v == null) {
				try {
					v = new VarDouble(Double.parseDouble(arg));
				} catch (NumberFormatException e) {
				}
				if (v == null) {
					if (game.getPlayer(arg) != null) {

						v = new VarPlayer(game.getPlayer(arg));
					} else if (game.getLocation(arg) != null) {
						v = new VarLocation(game.getLocation(arg));
					} else if (game.getGameObjectInstance(arg) != null) {
						v = new VarGameObject(game.getGameObjectInstance(arg));
					}
				}
			}
		}

		return v;
	}

	@Override
	public Variable[] getActionParameters() {
		Variable[] arguments = new Variable[actionArguments.length];
		for(int i = 0; i<arguments.length; i++){
			arguments[i] = parseVariable(actionArguments[i]);
		}
		
		return arguments;
	}

	@Override
	public void clearNextActionName() {
		actionName = null;

		
	}

	@Override
	public void clearNextParameters() {
		actionArguments = null;
		
	}

}
