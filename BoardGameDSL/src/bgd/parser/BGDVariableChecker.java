package bgd.parser;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.TerminalNode;

import bgd.grammar.BGDBaseVisitor;
import bgd.grammar.BGDParser;
import bgd.grammar.BGDParser.LocVariableContext;
import bgd.grammar.BGDParser.ObjectInstanceContext;
import bgd.grammar.BGDParser.VariableContext;
import bgd.parser.ParseReturn.ParseReturnValue;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarBool;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarDouble;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarGameObject;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarInt;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarList;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarLocation;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarOwner;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarPlayer;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Game;
import boardGameSimulator.model.boardGameStateMachine.stateModel.GameObjectInstance;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Location;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Owner;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Player;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Randomizer;

/**
 * Second pass over the AST, now initializing all variables as the objects are
 * already created
 * 
 * @author Peter
 *
 */
public class BGDVariableChecker extends BGDBaseVisitor<ParseReturn> {

	private Parser parser;
	private Game game;

	public BGDVariableChecker(Parser p) {
		this.parser = p;
		this.game = p.getGame();
	}

	private boolean currentPublicnessIsPublic = false;

	@Override
	public ParseReturn visitPlayerVarList(@NotNull BGDParser.PlayerVarListContext ctx) {
		Player playerObject = game.getPlayer(ctx.ID().getText());

		for (VariableContext variablectx : ctx.varList().variable()) {

			playerObject.getVarManager().addVariable(variablectx.ID().getText(),
					parseReturnToVariable(visit(variablectx)));
		}

		return null;
	}

	/*
	 * ############################# # Locations #############################
	 */

	private Location currentLocation = null;

	@Override
	public ParseReturn visitLocationNormal(@NotNull BGDParser.LocationNormalContext ctx) {
		currentLocation = game.getLocation(ctx.ID().getText());
		visit(ctx.locationVariables());
		if (ctx.startingInv() != null) {
			visit(ctx.startingInv());
		}
		// TODO Supplies!

		return null;
	}

	@Override
	public ParseReturn visitLocationVariables(@NotNull BGDParser.LocationVariablesContext ctx) {
		for (LocVariableContext locctx : ctx.locVariable()) {
			visit(locctx);
		}
		return null;
	}

	@Override
	public ParseReturn visitLocVariable(@NotNull BGDParser.LocVariableContext ctx) {
		if (ctx.variable() != null) {
			Variable v = parseReturnToVariable(visit(ctx.variable()));
			currentLocation.getVarManager().addVariable(ctx.variable().ID().getText(), v);
		} else {
			visit(ctx.getChild(0));
		}
		return null;
	}

	@Override
	public ParseReturn visitLocOwnerPublic(@NotNull BGDParser.LocOwnerPublicContext ctx) {
		currentLocation.getVarManager().addVariable("Owner", new VarOwner());
		currentLocation.setPublicValueVisibility(true);
		return null;
	}

	@Override
	public ParseReturn visitLocOwnerID(@NotNull BGDParser.LocOwnerIDContext ctx) {
		currentLocation.getVarManager().addVariable("Owner",
				new VarOwner(new Owner(game.getPlayer(ctx.ID().getText()))));
		return null;
	}

	@Override
	public ParseReturn visitVisible1Public(@NotNull BGDParser.Visible1PublicContext ctx) {
		currentLocation.setPublicExistanceVisibility(true);
		return null;
	}

	@Override
	public ParseReturn visitVisible1SingleName(@NotNull BGDParser.Visible1SingleNameContext ctx) {
		currentLocation.setPublicExistanceVisibility(false);
		currentLocation.addExistanceVisiblePlayer(game.getPlayer(ctx.ID().getText()));
		return null;
	}

	@Override
	public ParseReturn visitVisible1NameList(@NotNull BGDParser.Visible1NameListContext ctx) {
		currentLocation.setPublicExistanceVisibility(false);

		for (TerminalNode namectx : ctx.nameList().ID()) {
			currentLocation.addExistanceVisiblePlayer(game.getPlayer(namectx.getText()));

		}

		return null;
	}

	@Override
	public ParseReturn visitVisible1None(@NotNull BGDParser.Visible1NoneContext ctx) {
		currentLocation.setPublicExistanceVisibility(false);
		return null;
	}

	@Override
	public ParseReturn visitVisible2Public(@NotNull BGDParser.Visible2PublicContext ctx) {
		currentLocation.setPublicValueVisibility(true);
		return null;
	}

	@Override
	public ParseReturn visitVisible2None(@NotNull BGDParser.Visible2NoneContext ctx) {
		currentLocation.setPublicValueVisibility(false);

		return null;
	}

	@Override
	public ParseReturn visitVisible2NameList(@NotNull BGDParser.Visible2NameListContext ctx) {
		currentLocation.setPublicValueVisibility(false);

		for (TerminalNode namectx : ctx.nameList().ID()) {
			currentLocation.addValueVisiblePlayer(game.getPlayer(namectx.getText()));

		}

		return null;
	}

	@Override
	public ParseReturn visitVisible2SingleName(@NotNull BGDParser.Visible2SingleNameContext ctx) {
		currentLocation.setPublicValueVisibility(false);
		currentLocation.addValueVisiblePlayer(game.getPlayer(ctx.ID().getText()));

		return null;
	}

	@Override
	public ParseReturn visitStartingInv(@NotNull BGDParser.StartingInvContext ctx) {
		for (ObjectInstanceContext ctxs : ctx.objectInstance()) {
			visit(ctxs);
		}
		return null;
	}

	private GameObjectInstance currentObject = null;

	/*
	 *ObjectInstance 
	*/
	
	@Override
	public ParseReturn visitObjectInstance(@NotNull BGDParser.ObjectInstanceContext ctx) {
		GameObjectInstance object = game.getGameObjectTemplate(ctx.ID(0).getText()).getNewInstance(ctx.ID(1).getText(),
				currentLocation);
		currentObject = object;
		if (ctx.objectVariableHelp().size() != 0) {
			for (int i = 0; i < ctx.objectVariableHelp().size(); i++) {
				ParseReturn ret = visit(ctx.objectVariableHelp(i).objectVariable());
				object.getVarManager().addVariable(ret.getIDVar(), ret.getVar());
			}
		}

		return null;
	}

	@Override
	public ParseReturn visitObjectOwnerLocation(@NotNull BGDParser.ObjectOwnerLocationContext ctx) {

		ParseReturn pr = new ParseReturn(ParseReturnValue.Variable);
		pr.setVar(new VarOwner(new Owner(currentObject.getLocation())));
		pr.setIDVar("Owner");
		return pr;
	}

	@Override
	public ParseReturn visitObjectOwnerID(@NotNull BGDParser.ObjectOwnerIDContext ctx) {
		ParseReturn pr = new ParseReturn(ParseReturnValue.Variable);
		pr.setVar(new VarOwner(new Owner(game.getPlayer(ctx.ID().getText()))));
		pr.setIDVar("Owner");
		return pr;
	}

	@Override
	public ParseReturn visitObjectOwnerPublic(@NotNull BGDParser.ObjectOwnerPublicContext ctx) {
		ParseReturn pr = new ParseReturn(ParseReturnValue.Variable);
		pr.setVar(new VarOwner());
		pr.setIDVar("Owner");
		return pr;
	}

	private Variable[] currentRandomizerVariableList = {};

	@Override
	public ParseReturn visitObjectRandomizer(@NotNull BGDParser.ObjectRandomizerContext ctx) {
		visit(ctx.randomizerValueList());

		currentObject.setRandomizer(new Randomizer(currentRandomizerVariableList));

		ParseReturn pr = new ParseReturn(ParseReturnValue.Variable);
		pr.setVar(new VarBool(true));
		pr.setIDVar("Randomizer");
		return pr;
	}

	public ParseReturn visitRandomizerValueList(@NotNull BGDParser.RandomizerValueListContext ctx) {
		currentRandomizerVariableList = new Variable[ctx.value().size()];
		for (int i = 0; i < ctx.value().size(); i++) {
			ParseReturn ret = visit(ctx.value(i));

			Variable v = parseReturnToVariable(ret);
			currentRandomizerVariableList[i] = v;
		}

		return null;

	}

	/*
	 * ################################ # Object ###############################
	 */
	
	@Override
	public ParseReturn visitObjects(@NotNull BGDParser.ObjectsContext ctx){
		//All checks have been performed already
		return null;
	}
	
	@Override
	public ParseReturn visitVariable(@NotNull BGDParser.VariableContext ctx) {
		ParseReturn ret = visit(ctx.value());
		ParseReturn pr = new ParseReturn(ParseReturnValue.Variable);
		pr.setVar(parseReturnToVariable(ret));
		pr.setIDVar(ctx.ID().getText());
		return pr;
	}

	@Override
	public ParseReturn visitObjectVariableHelp(@NotNull BGDParser.ObjectVariableHelpContext ctx) {
		currentPublicnessIsPublic = ctx.publicness().PUBLIC() != null;

		return visit(ctx.objectVariable());
	}

	@Override
	public ParseReturn visitObjectVariableVariable(@NotNull BGDParser.ObjectVariableVariableContext ctx) {
		ParseReturn variable = visit(ctx.variable().value());

		Variable v = parseReturnToVariable(variable);

		ParseReturn pr = new ParseReturn(ParseReturnValue.Variable);
		pr.setVar(v);

		return pr;
	}

	/*
	 * ###################################### 
	 * # Variable                           #
	 * ######################################
	 */
	@Override
	public ParseReturn visitValueBool(@NotNull BGDParser.ValueBoolContext ctx) {

		return visit(ctx.bool());
	}

	@Override
	public ParseReturn visitValueNumber(@NotNull BGDParser.ValueNumberContext ctx) {
		return visit(ctx.number());
	}

	@Override
	public ParseReturn visitValueID(@NotNull BGDParser.ValueIDContext ctx) {

		ParseReturn ret = new ParseReturn(ParseReturn.ParseReturnValue.IDVar);
		ret.setIDVar(ctx.ID().getText());
		return ret;
	}

	@Override
	public ParseReturn visitValueList(@NotNull BGDParser.ValueListContext ctx) {

		
		ParseReturn pr = visit(ctx.list());
		VarList vl = (VarList) pr.getVar();
		
		return pr;

	}

	@Override
	public ParseReturn visitNonListValueBool(@NotNull BGDParser.NonListValueBoolContext ctx) {

		return visit(ctx.bool());
	}

	@Override
	public ParseReturn visitNonListValueNumber(@NotNull BGDParser.NonListValueNumberContext ctx) {
		return visit(ctx.number());
	}

	@Override
	public ParseReturn visitNonListValueID(@NotNull BGDParser.NonListValueIDContext ctx) {

		ParseReturn ret = new ParseReturn(ParseReturnValue.IDVar);
		ret.setIDVar(ctx.ID().getText());
		return ret;
	}

	@Override
	public ParseReturn visitListEmpty(@NotNull BGDParser.ListEmptyContext ctx) {
		Variable[] list = new Variable[0];

		ParseReturn pr = new ParseReturn(ParseReturnValue.Variable);
		pr.setVar(new VarList(list));
		return pr;
	}

	@Override
	public ParseReturn visitListFilled(@NotNull BGDParser.ListFilledContext ctx) {
		Variable[] list = new Variable[ctx.nonListValue().size()];

		for (int i = 0; i < list.length; i++) {
			list[i] = parseReturnToVariable(visit(ctx.nonListValue(i)));
		}

		ParseReturn pr = new ParseReturn(ParseReturnValue.Variable);
		pr.setVar(new VarList(list));

		return pr;
	}

	@Override
	public ParseReturn visitBool(@NotNull BGDParser.BoolContext ctx) {
		boolean truefalse = ctx.TRUE() != null;

		ParseReturn pr = new ParseReturn(ParseReturnValue.Variable);
		pr.setVar(new VarBool(truefalse));
		return pr;
	}

	@Override
	public ParseReturn visitNumberInt(@NotNull BGDParser.NumberIntContext ctx) {

		return visit(ctx.integer());
	}

	@Override
	public ParseReturn visitNumberDouble(@NotNull BGDParser.NumberDoubleContext ctx) {

		return visit(ctx.doublenum());
	}

	@Override
	public ParseReturn visitInteger(@NotNull BGDParser.IntegerContext ctx) {

		ParseReturn pr = new ParseReturn(ParseReturnValue.Variable);
		pr.setVar(new VarInt(Integer.parseInt(ctx.NUM().getText())));
		return pr;
	}

	@Override
	public ParseReturn visitDoublenum(@NotNull BGDParser.DoublenumContext ctx) {

		ParseReturn pr = new ParseReturn(ParseReturnValue.Variable);
		String raw = ctx.NUM(0).getText() + "." + ctx.NUM(1).getText();
		pr.setVar(new VarDouble(Double.parseDouble(raw)));
		return pr;
	}

	// Help Functions

	public Variable parseReturnToVariable(ParseReturn ret) {
		Variable v = null;
		if (ret.getVar() != null) {
			v = ret.getVar();
		} else if (ret.getCodeValue() != null) {
			v = ret.getCodeValue().getValue(null);
		} else if (game.getPlayer(ret.getIDVar()) != null) {

			v = new VarPlayer(game.getPlayer(ret.getIDVar()));
		} else if (game.getLocation(ret.getIDVar()) != null) {
			v = new VarLocation(game.getLocation(ret.getIDVar()));
		} else if (game.getGameObjectInstance(ret.getIDVar()) != null) {
			v = new VarGameObject(game.getGameObjectInstance(ret.getIDVar()));
		}
		return v;
	}
}
