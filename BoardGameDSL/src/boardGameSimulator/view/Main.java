package boardGameSimulator.view;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

	public void parseInput() {
		String[] args = sc.nextLine().split(" ");
		switch (args[0]) {
		case ("ParseGame"):
			loadGame(args);

			break;
		case ("Action"):
			parseAction(args);
			break;
		case ("Setup"):
			if (smc == null || smc.HasStarted()) {
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

		case ("Record"):
			handleRecord(args);
			break;

		default:
			System.out.println("Unkown input");
		}

	}

	private void handleRecord(String[] args) {
		if (args.length < 1) {
			System.out.println("Unknown command, provide more arguments to 'Record'");
		} else {
			switch (args[1]) {
			case ("Print"):
				if (args.length == 3 && args[2].equals("Sorted")) {
					System.out.println(smc.getGameRecordingSorted());
				} else {
					System.out.println(smc.getGameRecordingUnsorted());
				}
				break;
			case ("Write"):
				if (args.length < 3) {
					System.out.println("Please provide a filename");
					break;
				} else {

					String record = smc.getGameRecordingUnsorted();

					// BufferedWriter wr = null;
					// try {
					// // Open the file for writing, without removing its
					// current content.
					// wr = new BufferedWriter(new FileWriter(new
					// File(args[2])));
					// // Write a sample string to the end of the file.
					// wr.write(record);
					// }
					// catch(IOException e) {
					// //TODO fix errorhandling
					// e.printStackTrace();
					// }
					// finally {
					// // Close the file.
					// try {
					// wr.close();
					// }
					// catch (IOException ex) {
					// //TODO fix errorhandling
					// ex.printStackTrace();
					// }
					// }
					// }

					try {
						FileWriter fileWriter = new FileWriter(args[2]);
						fileWriter.write(record);
						fileWriter.close();
					} catch (IOException e) {
						System.out.println("Could not create file at path " + args[2]);
						System.err.println(e);
					}
				}
				break;

			case ("Load"):
				if (args.length < 3) {
					System.out.println("No file to load specified!");
				} else {
					try {
						FileReader fr = new FileReader(args[2]);
						smc.loadRecord(fr);

					} catch (FileNotFoundException e) {
						System.out.println("File not found, please try again");
					}

				}
				break;

			default:
				System.out.println("Unknown command: " + args[1]);
				break;
			}

		}
	}

	private void runSetup(String[] args) {
		// Sets up which players are actually playing
		if (game.getPlayer(args[1]) != null) {

			System.out.println("Main - runSetup");
			for (Player p : game.getTurnOrder()) {
				System.out.print(p.getName() + ", ");
			}
			System.out.println();
			System.out.println(game.getCurrentTurn());

			game.getPlayer(args[1]).setIsPlaying(true);
			game.getPlayer(args[1]).setPlayerName(args[2]);
			Player[] newTurnOrder = new Player[game.getTurnOrder().length + 1];
			System.arraycopy(game.getTurnOrder(), 0, newTurnOrder, 0, game.getTurnOrder().length);
			newTurnOrder[newTurnOrder.length - 1] = game.getPlayer(args[1]);
			game.setTurnOrder(newTurnOrder);

			System.out.println("After");
			for (Player p : game.getTurnOrder()) {
				System.out.print(p.getName() + ", ");
			}
			System.out.println();
			System.out.println(game.getCurrentTurn());

		} else {
			System.out.println(args[1] + " is not a Player!");
		}

	}

	public void print(String[] args) {
		if (args.length == 1) {
			System.out.println("Please specify whether you want to print the current turn or the gamestate");
			return;
		}
		if (args[1].equals("Turn")) {
			System.out.println("It is currently " + game.getCurrentTurn().getPlayerName() + "'s Turn ("
					+ game.getCurrentTurn().getName() + ")");
		} else if (args[1].equals("Game")) {
			game.printExtended();
		}
	}

	public void waitForActionInput() {
		parseInput();

	}

	private boolean parseAction(String[] args) {

		if (args[0].equals("Action") && args.length >= 2) {
			if (game.getActionRound(args[1]) == null) {
				System.out.println("Actionname not recognized: " + args[1]);
				return false;
			} else if ((args.length - 2) < game.getActionRound(args[1]).argumentNumber()) {
				System.out.println("Not enough arguments supplied!");
				return false;
			} else if (getParameters(Arrays.copyOfRange(args, 2, args.length)) == null) {
				System.out.println("Could not recognize arguments to variables");
				return false;
			} else {
				this.actionName = args[1];
				this.actionArguments = Arrays.copyOfRange(args, 2, args.length);
				return true;
			}

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
		System.out.println("D1 Value: " + game.getGameObjectInstance("D1").getRandomizer().getValue());
		if (this.actionName != null && getParameters() != null) {
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
			vars[i] = parseVariable(game, actionArguments[i]);
			if (vars[i] == null) {
				return null;
			}
		}

		return vars;
	}

	public Variable[] getParameters(String[] pars) {
		Variable[] vars = new Variable[pars.length];

		for (int i = 0; i < vars.length; i++) {
			vars[i] = parseVariable(game, pars[i]);
			if (vars[i] == null) {
				return null;
			}
		}

		return vars;
	}

	public static Variable parseVariable(Game gameInstance, String arg) {
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
					if (gameInstance.getPlayer(arg) != null) {

						v = new VarPlayer(gameInstance.getPlayer(arg));
					} else if (gameInstance.getLocation(arg) != null) {
						v = new VarLocation(gameInstance.getLocation(arg));
					} else if (gameInstance.getGameObjectInstance(arg) != null) {
						v = new VarGameObject(gameInstance.getGameObjectInstance(arg));
					}
				}
			}
		}

		return v;
	}

	@Override
	public Variable[] getActionParameters() {
		Variable[] arguments = new Variable[actionArguments.length];
		for (int i = 0; i < arguments.length; i++) {
			arguments[i] = parseVariable(game, actionArguments[i]);
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
