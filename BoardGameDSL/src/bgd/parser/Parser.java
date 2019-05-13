package bgd.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import bgd.grammar.BGDLexer;
import bgd.grammar.BGDParser;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarInt;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarList;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarLocation;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Game;
import boardGameSimulator.model.boardGameStateMachine.stateModel.GameObjectInstance;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Player;

public class Parser {
	private BGDChecker checker = new BGDChecker(this);
	private BGDVariableChecker varChecker;
	private final CustomErrorListener errorListener = new CustomErrorListener();
	private Game game;

	private Lexer lexer;
	public boolean hasError = false;

	public void compile(String file) {
		CharStream chars = null;

		try {
			FileReader filereader = new FileReader(file);
			chars = new ANTLRInputStream(filereader);
			lexer = new BGDLexer(chars);
			lexer.removeErrorListeners();
			lexer.addErrorListener(errorListener);
			TokenStream tokens = new CommonTokenStream(lexer);
			BGDParser parser = new BGDParser(tokens);
			parser.removeErrorListeners();
			parser.addErrorListener(errorListener);
			System.out.println("Started parsing file");
			ParseTree tree = parser.program();
			System.out.println("Finished parsing File");

			if (errorListener.hasErrors()) {
				System.err.println(String.format("%d errors found in parse phase: ", errorListener.getErrors().size()));
				for (String error : errorListener.getErrors()) {
					System.err.println(error);
				}
			} else {
				System.out.println("Succesful, generating tree");
				walk(tree);
				varChecker = new BGDVariableChecker(this);
				varCheck(tree);

				// TODO make tree
			}
			hasError = false;

		} catch (FileNotFoundException e) {
			System.out.println("File not found, please try again");
			e.printStackTrace();
			hasError=true;
		} catch (IOException e) {
			e.printStackTrace();
			hasError=true;
		}
	}

	public void walk(ParseTree tree) {
		System.out.println("Starting typechecking");
		checker.visit(tree);

	}

	public void varCheck(ParseTree tree) {
		System.out.println("Starting varchecking");
		varChecker.visit(tree);
		System.out.println("Finished varchecking");
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public static void main(String[] args) {
		String file = "GameDescriptions/testDocument_MEJN.bgd";
		// String file = "GameDescriptions/spelverloop";
		// String file = "GameDescriptions/mens-erger-je-niet_old.bgd";

		Parser p = new Parser();
		p.compile(file);
		System.out.println("Done!");
		System.out.println("Objects at NoLoc: "
				+ ((VarInt) ((VarList) p.getGame().getLocation("NoLoc").getInventory()).getSize()).getValue());

		GameObjectInstance dice = p.getGame().getGameObjectInstance("D1");
		System.out.println("DiceFavPawn: " + dice.getVarManager().getVariable("FavPawn"));

		VarInt i = (VarInt) ((VarList) dice.getRandomizer().getVariableList()).getSize();
		System.out.println("Randomizer Size: " + i.getValue());
		System.out.println("Randomizer Value: " + ((VarInt)dice.getRandomizer().getValue()).getValue());
//		System.out.println("Randomizer side 3: " + ((VarInt)((VarList)dice.getRandomizer().getVariableList()).getValue()[2]).getValue());
//		System.out.println()
		
		Player blue = p.getGame().getPlayer("B");
		System.out.println(blue.getVarManager().getVariable("StartLoc"));
		System.out.println(blue.getVarManager().getVariable("SupplyLoc"));
		System.out.println(blue.getVarManager().getVariable("GoalLocs"));
		System.out.println(p.getGame().getVarMan().getVariable("NoLoc"));
		System.out.println(p.getGame().getVarMan().getVariable("D1"));
		
		
		Player ct = p.getGame().getPlayer("B");
		System.out.println(ct.getVarManager().getVariable("StartLoc"));
		System.out.println(((VarLocation) ct.getVarManager().getVariable("StartLoc")).getValue().getInventory());
		
		
		System.out.println(p.getGame().getActionRound("MovePawn"));
		System.out.println(p.getGame().getActionRound("CheckIsFinished").argumentNumber());
		System.out.println(p.getGame().getActionRound("CheckIsFinished").getArguments()[0]);
		System.out.println(ct.getVarManager().getVariable("GoalLocs"));
	}
}
