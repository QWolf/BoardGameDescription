package bgd.parser;

import org.antlr.v4.runtime.misc.NotNull;

import bgd.grammar.BGDBaseVisitor;
import bgd.grammar.BGDParser;
import bgd.grammar.BGDParser.ActionContext;
import bgd.grammar.BGDParser.AdditionalRoundContext;
import bgd.grammar.BGDParser.ObjectVariableHelpContext;
import bgd.parser.ParseReturn.ParseReturnValue;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.CodeLine;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.CodeLineChooseAction;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.CodeLineFinish;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.CodeLineIf;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.CodeLineMoveObjectTo;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.CodeLineNextTurn;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.CodeLineNonReturnFunctionActionRoundExecute;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.CodeLineRandomize;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.CodeLineRepeat;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.CodeLineReturnLine;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.CodeLineSetTurnOrder;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.CodeLineSetVariable;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.CodeLineWinLose;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueActionRound;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueAddOp;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueAddOp.AddOp;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueBoolCompare;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueBoolCompare.CompareOperator;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueBoolNot;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueBoolOperator;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueGlobalVariable;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueGlobalVariable.GlobalFunction;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueBoolOperator.BoolOperator;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueIDFromLocation;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueList;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueListIndex;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueLocationFunction;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueLocationFunction.LocationFunction;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueMultOp;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueMultOp.MultOp;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueObjectFunction;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueObjectFunction.ObjectFunction;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValuePlainVariable;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueStandardFunction;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueStandardFunction.StandardFunction;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.SingleScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarGameObject;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarList;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarLocation;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarOwner;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarPlayer;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarType;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.ActionRound;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Game;
import boardGameSimulator.model.boardGameStateMachine.stateModel.GameObjectTemplate;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Location;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Owner;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Player;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Randomizer;

/**
 * Initiates all objects, but does not set variables yet
 * 
 * @author Peter
 *
 */
public class BGDChecker extends BGDBaseVisitor<ParseReturn> {

	private Parser parser;
	private String gamename;
	private int minplayers = 0;
	private int maxplayers = 0;
	private Game game = null;
	MultiScopeVariableManager globalMultiScope = null;

	public BGDChecker(Parser p) {
		this.parser = p;
	}

	@Override
	public ParseReturn visitProgram(@NotNull BGDParser.ProgramContext ctx) {
		visit(ctx.init());

		visit(ctx.playersSection());

		visit(ctx.locations());
		if (ctx.locationconnections() != null) {
			visit(ctx.locationconnections());
		}
		visit(ctx.objects());
		visit(ctx.rounds());
		visit(ctx.actions());
		if (ctx.startState() != null) {
			visit(ctx.startState());
		}

		return null;
	}

	@Override
	public ParseReturn visitInit(@NotNull BGDParser.InitContext ctx) {
		gamename = ctx.getText();
		return null;
	}

	/*
	 * #########################################################################
	 * # Players #
	 * #########################################################################
	 */
	@Override
	public ParseReturn visitPlayersSection(@NotNull BGDParser.PlayersSectionContext ctx) {
		visit(ctx.playernum());
		// Initialize the Game object now we have all required information to do
		// so
		this.game = new Game(gamename, minplayers, maxplayers);
		parser.setGame(game);
		globalMultiScope = new MultiScopeVariableManager(game.getVarMan());

		visit(ctx.players());
		return null;
	}

	@Override
	public ParseReturn visitMinplayers(@NotNull BGDParser.MinplayersContext ctx) {
		minplayers = Integer.parseInt(ctx.NUM().getText());
		return null;
	}

	@Override
	public ParseReturn visitMaxplayers(@NotNull BGDParser.MaxplayersContext ctx) {
		maxplayers = Integer.parseInt(ctx.NUM().getText());
		return null;
	}

	@Override
	public ParseReturn visitPlayerOnlyID(@NotNull BGDParser.PlayerOnlyIDContext ctx) {
		// Is is a human player?
		ParseReturn humOrAI = visit(ctx.humOrAI());
		boolean humOrAIBool = humOrAI.getParseReturn() == ParseReturnValue.Human;
		// Create a new player object
		// All players initialized as non-contenders
		game.addPlayer(new Player(ctx.ID().getText(), humOrAIBool, false, game));

		return null;
	}

	@Override
	public ParseReturn visitPlayerVarList(@NotNull BGDParser.PlayerVarListContext ctx) {
		// Is is a human player?
		ParseReturn humOrAI = visit(ctx.humOrAI());
		boolean humOrAIBool = humOrAI.getParseReturn() == ParseReturnValue.Human;
		// Create a new player object
		// All players initialized as non-contenders
		game.addPlayer(new Player(ctx.ID().getText(), humOrAIBool, false, game));

		return null;
	}

	@Override
	public ParseReturn visitHumOrAIComputer(@NotNull BGDParser.HumOrAIComputerContext ctx) {
		return new ParseReturn(ParseReturnValue.AI);
	}

	@Override
	public ParseReturn visitHumOrAIHuman(@NotNull BGDParser.HumOrAIHumanContext ctx) {
		return new ParseReturn(ParseReturnValue.Human);
	}

	/*
	 * #########################################################################
	 * # Locations #
	 * #########################################################################
	 */

	@Override
	public ParseReturn visitLocationNormal(@NotNull BGDParser.LocationNormalContext ctx) {
		if (ctx.locType() != null && visit(ctx.locType()).getParseReturn() == ParseReturnValue.Supply) {

			// TODO replace null with what is supplies
			game.addLocation(new Location(ctx.ID().getText(), null, game));

		} else {
			// no supply
			game.addLocation(new Location(ctx.ID().getText(), null, game));
		}

		return null;
	}

	@Override
	public ParseReturn visitLocType(@NotNull BGDParser.LocTypeContext ctx) {
		if (ctx.PLACE() != null) {
			return new ParseReturn(ParseReturnValue.Place);
		} else {
			return new ParseReturn(ParseReturnValue.Supply);
		}
	}

	/*
	 * #########################################################################
	 * # Location Connections #
	 * #########################################################################
	 */

	@Override
	public ParseReturn visitLocconnectionUndirected(@NotNull BGDParser.LocconnectionUndirectedContext ctx) {
		Location loc1 = game.getLocation(ctx.locList().ID(0).getText());
		Location loc2 = null;

		// For each pair
		for (int i = 1; i < ctx.locList().ID().size(); i++) {
			loc2 = game.getLocation(ctx.locList().ID(i).getText());
			loc1.addConnections(loc2);
			loc2.addConnections(loc1);

			loc1 = loc2;
		}

		return null;
	}

	@Override
	public ParseReturn visitLocconnectionDirected(@NotNull BGDParser.LocconnectionDirectedContext ctx) {
		Location loc1 = game.getLocation(ctx.locList().ID(0).getText());
		Location loc2 = null;

		// For each pair
		for (int i = 1; i < ctx.locList().ID().size(); i++) {
			loc2 = game.getLocation(ctx.locList().ID(i).getText());
			loc1.addConnections(loc2);

			loc1 = loc2;
		}

		return null;
	}

	/*
	 * #########################################################################
	 * # Location Objects #
	 * #########################################################################
	 */
	private GameObjectTemplate currentTemplate = null;
	private boolean currentPublicnessIsPublic = true;

	@Override
	public ParseReturn visitObject(@NotNull BGDParser.ObjectContext ctx) {
		GameObjectTemplate template = new GameObjectTemplate(ctx.ID().getText(), game);
		game.addGameObjectTemplate(template);
		currentTemplate = template;

		for (ObjectVariableHelpContext ctxs : ctx.objectVariableHelp()) {
			visit(ctxs);
		}

		return null;
	}

	@Override
	public ParseReturn visitObjectVariableHelp(@NotNull BGDParser.ObjectVariableHelpContext ctx) {
		currentPublicnessIsPublic = ctx.publicness().PUBLIC() != null;

		return visit(ctx.objectVariable());
	}

	@Override
	public ParseReturn visitObjectVariableVariable(@NotNull BGDParser.ObjectVariableVariableContext ctx) {
		ParseReturn variable = visit(ctx.variable().value());
		Variable v = parseReturnToVariable(variable, globalMultiScope);
		currentTemplate.setVariable(ctx.variable().ID().getText(), v);
		return null;
	}

	@Override
	public ParseReturn visitObjectOwnerID(@NotNull BGDParser.ObjectOwnerIDContext ctx) {
		Player id = game.getPlayer(ctx.ID().getText());
		currentTemplate.setVariable("Owner", new VarOwner(new Owner(id)));

		return null;
	}

	@Override
	public ParseReturn visitObjectOwnerPublic(@NotNull BGDParser.ObjectOwnerPublicContext ctx) {
		currentTemplate.setVariable("Owner", new VarOwner(new Owner()));

		return null;
	}

	@Override
	public ParseReturn visitObjectOwnerLocation(@NotNull BGDParser.ObjectOwnerLocationContext ctx) {
		currentTemplate.setOwnerIsLocation(true);

		return null;
	}

	private Variable[] currentRandomizerVariableList = {};

	@Override
	public ParseReturn visitObjectRandomizer(@NotNull BGDParser.ObjectRandomizerContext ctx) {
		visit(ctx.randomizerValueList());
		currentTemplate.setRandomizer(new Randomizer(currentRandomizerVariableList));
		return null;
	}

	public ParseReturn visitRandomizerValueList(@NotNull BGDParser.RandomizerValueListContext ctx) {
		currentRandomizerVariableList = new Variable[ctx.value().size()];
		for (int i = 0; i < ctx.value().size(); i++) {
			ParseReturn ret = visit(ctx.value(i));
			Variable v = parseReturnToVariable(ret, globalMultiScope);
			currentRandomizerVariableList[i] = v;
		}

		return null;

	}

	/*
	 * #########################################################################
	 * # Rounds #
	 * #########################################################################
	 */

	// private CodeLine[] currentCodeBlock = new CodeLine[0];
	private String[] currentArguments = new String[0];

	@Override
	public ParseReturn visitRounds(@NotNull BGDParser.RoundsContext ctx) {
		for (AdditionalRoundContext actx : ctx.additionalRound()) {
			visit(actx);
		}
		visit(ctx.main());

		return null;
	}

	@Override
	public ParseReturn visitMain(@NotNull BGDParser.MainContext ctx) {

		CodeLine[] codeBlock = visit(ctx.codeBlock()).getCodeBlock();

		ActionRound main = new ActionRound("Main", game, codeBlock, new String[0]);
		game.addActionRound(main);
		return null;
	}

	@Override
	public ParseReturn visitAdditionalRound(@NotNull BGDParser.AdditionalRoundContext ctx) {
		CodeLine[] codeBlock = visit(ctx.codeBlock()).getCodeBlock();

		if (ctx.arguments() != null) {
			currentArguments = new String[ctx.arguments().argument().size()];
			visit(ctx.arguments());
		} else {
			currentArguments = new String[0];
		}
		ActionRound main = new ActionRound(ctx.ID().getText(), game, codeBlock, currentArguments);
		game.addActionRound(main);
		return null;
	}

	@Override
	public ParseReturn visitArguments(@NotNull BGDParser.ArgumentsContext ctx) {
		for (int i = 0; i < ctx.argument().size(); i++) {
			currentArguments[i] = visit(ctx.argument(i)).getIDVar();
		}

		return null;
	}

	@Override
	public ParseReturn visitArgument(@NotNull BGDParser.ArgumentContext ctx) {
		ParseReturn pr = new ParseReturn(ParseReturnValue.IDVar);
		pr.setIDVar(ctx.LOWID().getText());
		return pr;
	}

	@Override
	public ParseReturn visitCodeBlock(@NotNull BGDParser.CodeBlockContext ctx) {
		CodeLine[] currentCodeBlock = new CodeLine[ctx.codeLine().size()];

		if (ctx.codeLine().size() == 0) {
			return null;
		}

		for (int i = 0; i < ctx.codeLine().size(); i++) {
			currentCodeBlock[i] = visit(ctx.codeLine(i)).getCodeLine();
		}
		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeBlock);
		pr.setCodeBlock(currentCodeBlock);

		return pr;
	}

	/*
	 * ##CodeLine##
	 */
	@Override
	public ParseReturn visitCodeLineNonReturn(@NotNull BGDParser.CodeLineNonReturnContext ctx) {
		return visit(ctx.nonReturnFunction());
	}

	@Override
	public ParseReturn visitCodeLineIf(@NotNull BGDParser.CodeLineIfContext ctx) {

		CodeLine codeLine = new CodeLineIf(visit(ctx.codeValue()).getCodeValue(),
				visit(ctx.codeBlock()).getCodeBlock());

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeLine);
		pr.setCodeLine(codeLine);
		return pr;
	}

	@Override
	public ParseReturn visitCodeLineifElse(@NotNull BGDParser.CodeLineifElseContext ctx) {
		CodeLine codeLine = new CodeLineIf(visit(ctx.codeValue()).getCodeValue(),
				visit(ctx.codeBlock(0)).getCodeBlock(), visit(ctx.codeBlock(1)).getCodeBlock());

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeLine);
		pr.setCodeLine(codeLine);
		return pr;
	}

	@Override
	public ParseReturn visitCodeLineReturn(@NotNull BGDParser.CodeLineReturnContext ctx) {
		CodeLine codeLine = new CodeLineReturnLine(visit(ctx.codeValue()).getCodeValue());

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeLine);
		pr.setCodeLine(codeLine);
		return pr;
	}

	/*
	 * ##NonReturnFunctions
	 */

	@Override
	public ParseReturn visitNonReturnFunctionTakeActionPlayer(
			@NotNull BGDParser.NonReturnFunctionTakeActionPlayerContext ctx) {
		CodeLine codeLine = new CodeLineChooseAction(visit(ctx.codeValue()).getCodeValue(), game);

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeLine);
		pr.setCodeLine(codeLine);
		return pr;
	}

	@Override
	public ParseReturn visitNonReturnFunctionActionRoundNoReturn(
			@NotNull BGDParser.NonReturnFunctionActionRoundNoReturnContext ctx) {
		CodeValue[] arguments = null;
		// System.out.println("-!VisitNOnReturnFunctionActionRoundNoRetunrn");
		// System.out.println(ctx.getText());
		if (ctx.performActionArguments() != null) {
			arguments = new CodeValue[ctx.performActionArguments().codeValue().size()];

			for (int i = 0; i < arguments.length; i++) {
				arguments[i] = visit(ctx.performActionArguments().codeValue(i)).getCodeValue();
			}

		} else {
			arguments = new CodeValue[0];
		}

		CodeLine codeLine = new CodeLineNonReturnFunctionActionRoundExecute(game.getActionRound(ctx.ID().getText()),
				arguments, game);

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeLine);
		pr.setCodeLine(codeLine);
		return pr;
	}

	@Override
	public ParseReturn visitNonReturnFunctionMoveObjectTo(@NotNull BGDParser.NonReturnFunctionMoveObjectToContext ctx) {
		CodeLine codeLine = new CodeLineMoveObjectTo(visit(ctx.codeValue(0)).getCodeValue(),
				visit(ctx.codeValue(1)).getCodeValue());

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeLine);
		pr.setCodeLine(codeLine);
		return pr;
	}

	@Override
	public ParseReturn visitNonReturnFunctionNextTurn(@NotNull BGDParser.NonReturnFunctionNextTurnContext ctx) {
		CodeLine codeLine = new CodeLineNextTurn(game);

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeLine);
		pr.setCodeLine(codeLine);
		return pr;
	}

	@Override
	public ParseReturn visitNonReturnFunctionRandomize(@NotNull BGDParser.NonReturnFunctionRandomizeContext ctx) {
		CodeLine codeLine = new CodeLineRandomize(visit(ctx.codeValue()).getCodeValue(), game);

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeLine);
		pr.setCodeLine(codeLine);
		return pr;
	}

	@Override
	public ParseReturn visitNonReturnFunctionRepeat(@NotNull BGDParser.NonReturnFunctionRepeatContext ctx) {
		CodeLine codeLine = new CodeLineRepeat();

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeLine);
		pr.setCodeLine(codeLine);
		return pr;
	}

	@Override
	public ParseReturn visitNonReturnFunctionWin(@NotNull BGDParser.NonReturnFunctionWinContext ctx) {
		CodeLine codeLine = new CodeLineWinLose(visit(ctx.codeValue()).getCodeValue(), true);

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeLine);
		pr.setCodeLine(codeLine);
		return pr;
	}

	@Override
	public ParseReturn visitNonReturnFunctionLoser(@NotNull BGDParser.NonReturnFunctionLoserContext ctx) {
		CodeLine codeLine = new CodeLineWinLose(visit(ctx.codeValue()).getCodeValue(), false);

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeLine);
		pr.setCodeLine(codeLine);
		return pr;
	}

	@Override
	public ParseReturn visitNonReturnFunctionFinishGame(@NotNull BGDParser.NonReturnFunctionFinishGameContext ctx) {
		CodeLine codeLine = new CodeLineFinish();

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeLine);
		pr.setCodeLine(codeLine);
		return pr;
	}

	@Override
	public ParseReturn visitNonReturnFunctionSetVariable(@NotNull BGDParser.NonReturnFunctionSetVariableContext ctx) {
		CodeLine codeLine = new CodeLineSetVariable(visit(ctx.codeValue(0)).getCodeValue(),
				visit(ctx.codeValue(1)).getCodeValue());

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeLine);
		pr.setCodeLine(codeLine);
		return pr;
	}

	@Override
	public ParseReturn visitNonReturnFunctionSetTurnOrder(@NotNull BGDParser.NonReturnFunctionSetTurnOrderContext ctx) {
		CodeLine codeLine = new CodeLineSetTurnOrder(visit(ctx.codeValue()).getCodeValue(), game);

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeLine);
		pr.setCodeLine(codeLine);
		return pr;
	}

	@Override
	public ParseReturn visitPerformActionArguments(@NotNull BGDParser.PerformActionArgumentsContext ctx) {
		CodeValue[] arguments = new CodeValue[ctx.codeValue().size()];
		for (int i = 0; i < ctx.codeValue().size(); i++) {
			arguments[i] = visit(ctx.codeValue(i)).getCodeValue();
		}

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValueList);
		pr.setCodeValueList(arguments);
		return pr;
	}

	/*
	 * CodeValueHelpers
	 */
	/*
	 * ##StandardFunction
	 */
	@Override
	public ParseReturn visitStandardFunctionListCount(@NotNull BGDParser.StandardFunctionListCountContext ctx) {
		CodeValue codeValue = new CodeValueStandardFunction(StandardFunction.ListCount,
				visit(ctx.codeValue()).getCodeValue());

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(codeValue);
		return pr;
	}

	/*
	 * ##locationFunction
	 */

	private CodeValue currentLocationFunctionLocation = null;

	@Override
	public ParseReturn visitLocFunctionContains(@NotNull BGDParser.LocFunctionContainsContext ctx) {
		CodeValue codeValue = new CodeValueLocationFunction(LocationFunction.Contains, currentLocationFunctionLocation,
				null);

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(codeValue);
		return pr;
	}

	@Override
	public ParseReturn visitLocFunctionConnections(@NotNull BGDParser.LocFunctionConnectionsContext ctx) {
		CodeValue codeValue = new CodeValueLocationFunction(LocationFunction.Connections,
				currentLocationFunctionLocation, null);

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(codeValue);
		return pr;
	}

	@Override
	public ParseReturn visitLocFunctionIsConnectedTo(@NotNull BGDParser.LocFunctionIsConnectedToContext ctx) {
		CodeValue codeValue = new CodeValueLocationFunction(LocationFunction.Contains, currentLocationFunctionLocation,
				visit(ctx.codeValue()).getCodeValue());
		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(codeValue);
		return pr;
	}
	
	@Override 
	public ParseReturn visitLocFunctionValueVisible(@NotNull BGDParser.LocFunctionValueVisibleContext ctx){
		CodeValue codeValue = new CodeValueLocationFunction(LocationFunction.ValueVisible, currentLocationFunctionLocation, null);
		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(codeValue);
		return pr;		
		
	}	
	@Override 
	public ParseReturn visitLocFunctionExistVisible(@NotNull BGDParser.LocFunctionExistVisibleContext ctx){
		CodeValue codeValue = new CodeValueLocationFunction(LocationFunction.ExistVisible, currentLocationFunctionLocation, null);
		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(codeValue);
		return pr;		
		
	}

	/*
	 * ##ObjectFunction##
	 */

	private CodeValue currentObjectFunctionObject = null;

	@Override
	public ParseReturn visitObjectFunctionLocation(@NotNull BGDParser.ObjectFunctionLocationContext ctx) {
		CodeValue codeValue = new CodeValueObjectFunction(ObjectFunction.Location, currentObjectFunctionObject);
		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(codeValue);
		return pr;
	}

	@Override
	public ParseReturn visitObjectFunctionOwner(@NotNull BGDParser.ObjectFunctionOwnerContext ctx) {
		CodeValue codeValue = new CodeValueObjectFunction(ObjectFunction.Owner, currentObjectFunctionObject);
		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(codeValue);
		return pr;
	}

	@Override
	public ParseReturn visitObjectFunctionValue(@NotNull BGDParser.ObjectFunctionValueContext ctx) {
		CodeValue codeValue = new CodeValueObjectFunction(ObjectFunction.Value, currentObjectFunctionObject);

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(codeValue);
		return pr;
	}

	// GlobalVariables
	@Override
	public ParseReturn visitGlobalVariableTurnOrder(@NotNull BGDParser.GlobalVariableTurnOrderContext ctx) {
		CodeValue codeValue = new CodeValueGlobalVariable(GlobalFunction.TurnOrder, game);
		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(codeValue);
		return pr;
	}

	/*
	 * #########################################################################
	 * # CodeValue #
	 * #########################################################################
	 */
	@Override
	public ParseReturn visitCodeValuePlainValue(@NotNull BGDParser.CodeValuePlainValueContext ctx) {

		//
		//
		// ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		// pr.setCodeValue(codeValue);

		return visit(ctx.codeValueValue());
	}

	@Override
	public ParseReturn visitCodeValueIDFromLocation(@NotNull BGDParser.CodeValueIDFromLocationContext ctx) {

		CodeValue cv = visit(ctx.codeValue()).getCodeValue();
		String varName = ctx.getVariable().ID().getText();
		CodeValue codeValue = new CodeValueIDFromLocation(VarType.Unspecified, cv, varName, ctx.getText());

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(codeValue);

		return pr;
	}

	@Override
	public ParseReturn visitCodeValueStandardFunction(@NotNull BGDParser.CodeValueStandardFunctionContext ctx) {
		return visit(ctx.standardFunction());
	}

	@Override
	public ParseReturn visitCodeValuePar(@NotNull BGDParser.CodeValueParContext ctx) {
		return visit(ctx.codeValue());
	}

	@Override
	public ParseReturn visitCodeValueActionOrRound(@NotNull BGDParser.CodeValueActionOrRoundContext ctx) {
		CodeValue[] arguments = new CodeValue[0];
		if (ctx.performActionArguments() != null) {
			arguments = visit(ctx.performActionArguments()).getCodeValueList();
		}

		CodeValue codeValue = new CodeValueActionRound(VarType.Unspecified, game.getActionRound(ctx.ID().getText()),
				arguments, ctx.getText());

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(codeValue);

		return pr;
	}

	@Override
	public ParseReturn visitCodeValueLocationFunction(@NotNull BGDParser.CodeValueLocationFunctionContext ctx) {
		currentLocationFunctionLocation = visit(ctx.codeValue()).getCodeValue();
		return visit(ctx.locationFunction());
	}

	@Override
	public ParseReturn visitCodeValueObjectFunction(@NotNull BGDParser.CodeValueObjectFunctionContext ctx) {
		currentObjectFunctionObject = visit(ctx.codeValue()).getCodeValue();
		return visit(ctx.objectFunction());
	}

	@Override
	public ParseReturn visitCodeValueBoolOperator(@NotNull BGDParser.CodeValueBoolOperatorContext ctx) {

		CodeValue codeValue = new CodeValueBoolOperator(visit(ctx.codeValue(0)).getCodeValue(),
				visit(ctx.boolOp()).getBoolOp(), visit(ctx.codeValue(1)).getCodeValue());

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(codeValue);

		return pr;
	}

	@Override
	public ParseReturn visitCodeValueListIndex(@NotNull BGDParser.CodeValueListIndexContext ctx) {
		CodeValue codeValue = new CodeValueListIndex(visit(ctx.codeValue(0)).getCodeValue(),
				visit(ctx.codeValue(1)).getCodeValue());
		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(codeValue);

		return pr;
	}

	@Override
	public ParseReturn visitCodeValueBoolNot(@NotNull BGDParser.CodeValueBoolNotContext ctx) {
		CodeValue codeValue = new CodeValueBoolNot(visit(ctx.codeValue()).getCodeValue());
		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(codeValue);

		return pr;
	}

	@Override
	public ParseReturn visitCodeValueBoolCompare(@NotNull BGDParser.CodeValueBoolCompareContext ctx) {
		CodeValue codeValue = new CodeValueBoolCompare(visit(ctx.codeValue(0)).getCodeValue(),
				visit(ctx.compareAdd()).getCompOp(), visit(ctx.codeValue(1)).getCodeValue());
		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(codeValue);

		return pr;
	}

	@Override
	public ParseReturn visitCodeValueList(@NotNull BGDParser.CodeValueListContext ctx) {
		CodeValue[] list = new CodeValue[ctx.codeValue().size()];
		for (int i = 0; i < ctx.codeValue().size(); i++) {
			list[i] = visit(ctx.codeValue(i)).getCodeValue();
		}

		CodeValue codeValue = new CodeValueList(list);
		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(codeValue);

		return pr;
	}

	@Override
	public ParseReturn visitCodeValueMultOp(@NotNull BGDParser.CodeValueMultOpContext ctx) {
		CodeValue codeValue = new CodeValueMultOp(visit(ctx.codeValue(0)).getCodeValue(),
				visit(ctx.multOp()).getMultOp(), visit(ctx.codeValue(1)).getCodeValue());

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(codeValue);

		return pr;
	}

	@Override
	public ParseReturn visitCodeValueAddOp(@NotNull BGDParser.CodeValueAddOpContext ctx) {
		CodeValue codeValue = new CodeValueAddOp(visit(ctx.codeValue(0)).getCodeValue(), visit(ctx.addOp()).getAddOp(),
				visit(ctx.codeValue(1)).getCodeValue());

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(codeValue);

		return pr;
	}

	@Override
	public ParseReturn visitCodeValueValue(@NotNull BGDParser.CodeValueValueContext ctx) {

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);

		if (ctx.ID() != null) {
			CodeValuePlainVariable cvpv = new CodeValuePlainVariable(VarType.Unspecified, ctx.ID().getText(), game,
					ctx.getText());
			pr.setCodeValue(cvpv);
			// System.out.println("IDNAME " + cvpv.idName);
		} else if (ctx.LOWID() != null) {
			pr.setCodeValue(
					new CodeValuePlainVariable(VarType.Unspecified, ctx.LOWID().getText(), game, ctx.getText()));
			CodeValuePlainVariable cvpv = new CodeValuePlainVariable(VarType.Unspecified, ctx.LOWID().getText(), game,
					ctx.getText());
			pr.setCodeValue(cvpv);
			// System.out.println("IDNAME " + cvpv.idName);

		} else if (ctx.bool() != null) {
			return visit(ctx.bool());
		} else {
			return visit(ctx.number());
		}
		return pr;
	}

	@Override
	public ParseReturn visitCodeValueGlobalVariable(@NotNull BGDParser.CodeValueGlobalVariableContext ctx) {
		return visit(ctx.globalVariable());

	}

	/*
	 * ######################################## Actions
	 * #########################################
	 */

	@Override
	public ParseReturn visitActions(@NotNull BGDParser.ActionsContext ctx) {
		for (ActionContext ctxs : ctx.action()) {
			visit(ctxs);
		}
		return null;
	}

	@Override
	public ParseReturn visitAction(@NotNull BGDParser.ActionContext ctx) {
		CodeLine[] effect = visit(ctx.effect()).getCodeBlock();
		CodeValue[] requires = visit(ctx.requires()).getCodeValueList();

		if (ctx.arguments() != null) {
			currentArguments = new String[ctx.arguments().argument().size()];
			visit(ctx.arguments());
		} else {
			currentArguments = new String[0];
		}
		ActionRound round = new ActionRound(ctx.ID().getText(), game, requires, effect, currentArguments);
		game.addActionRound(round);
		return null;
	}

	@Override
	public ParseReturn visitRequires(@NotNull BGDParser.RequiresContext ctx) {
		CodeValue[] cvList = new CodeValue[ctx.requireStatement().size()];
		if (cvList.length > 0) {
			for (int i = 0; i < cvList.length; i++) {
				cvList[i] = visit(ctx.requireStatement(i)).getCodeValue();
			}
		}

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValueList);
		pr.setCodeValueList(cvList);

		return pr;
	}

	@Override
	public ParseReturn visitRequireStatement(@NotNull BGDParser.RequireStatementContext ctx) {
		return visit(ctx.codeValue());
	}

	@Override
	public ParseReturn visitEffect(@NotNull BGDParser.EffectContext ctx) {
		CodeLine[] codeBlock = new CodeLine[ctx.effectStatement().size()];
		if (codeBlock.length > 0) {
			for (int i = 0; i < codeBlock.length; i++) {
				codeBlock[i] = visit(ctx.effectStatement(i)).getCodeLine();
			}
		}

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeBlock);
		pr.setCodeBlock(codeBlock);

		return pr;
	}

	@Override
	public ParseReturn visitEffectStatement(@NotNull BGDParser.EffectStatementContext ctx) {
		return visit(ctx.codeLine());
	}

	@Override
	public ParseReturn visitStartState(@NotNull BGDParser.StartStateContext ctx) {

		CodeLine[] codeBlock = visit(ctx.codeBlock()).getCodeBlock();

		ActionRound main = new ActionRound("StartState", game, codeBlock, new String[0]);
		game.addActionRound(main);
		return null;
	}

	/*
	 * #########################################################################
	 * # Values #
	 * #########################################################################
	 */

	// @Override
	// public ParseReturn visitVariable(@NotNull BGDParser.VariableContext ctx){
	// ParseReturn ret = visit(ctx.v)
	// return null;
	// }

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
	public ParseReturn visitListFilled(@NotNull BGDParser.ListFilledContext ctx) {
		Variable[] list = new Variable[ctx.nonListValue().size()];

		for (int i = 0; i < list.length; i++) {
			list[i] = visit(ctx.nonListValue(i)).getVar();
		}

		ParseReturn pr = new ParseReturn(ParseReturnValue.Variable);
		pr.setVar(new VarList(list));
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

		ParseReturn ret = new ParseReturn(ParseReturn.ParseReturnValue.IDVar);
		ret.setIDVar(ctx.ID().getText());
		return ret;
	}

	@Override
	public ParseReturn visitBool(@NotNull BGDParser.BoolContext ctx) {
		boolean truefalse = ctx.TRUE() != null;

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(new CodeValuePlainVariable(truefalse));
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

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		pr.setCodeValue(new CodeValuePlainVariable(Integer.parseInt(ctx.NUM().getText())));
		return pr;
	}

	@Override
	public ParseReturn visitDoublenum(@NotNull BGDParser.DoublenumContext ctx) {

		ParseReturn pr = new ParseReturn(ParseReturnValue.CodeValue);
		String raw = ctx.NUM(0).getText() + "." + ctx.NUM(1).getText();
		pr.setCodeValue(new CodeValuePlainVariable(Double.parseDouble(raw)));
		return pr;
	}

	@Override
	public ParseReturn visitBoolOpAnd(@NotNull BGDParser.BoolOpAndContext ctx) {

		ParseReturn pr = new ParseReturn(ParseReturnValue.BoolOp);
		pr.setBoolOp(BoolOperator.And);
		return pr;
	}

	@Override
	public ParseReturn visitBoolOpOr(@NotNull BGDParser.BoolOpOrContext ctx) {

		ParseReturn pr = new ParseReturn(ParseReturnValue.BoolOp);
		pr.setBoolOp(BoolOperator.Or);
		return pr;
	}

	@Override
	public ParseReturn visitAddOp(@NotNull BGDParser.AddOpContext ctx) {
		AddOp addOp = null;
		if (ctx.PLUS() != null) {
			addOp = AddOp.Add;
		} else {
			addOp = AddOp.Subtract;
		}

		ParseReturn pr = new ParseReturn(ParseReturnValue.AddOp);
		pr.setAddOp(addOp);
		return pr;
	}

	@Override
	public ParseReturn visitCompareAdd(@NotNull BGDParser.CompareAddContext ctx) {
		CompareOperator compOp = null;
		if (ctx.EQ() != null) {
			compOp = CompareOperator.EQ;
		} else if (ctx.NE() != null) {
			compOp = CompareOperator.NE;
		} else if (ctx.GT() != null) {
			compOp = CompareOperator.GT;
		} else if (ctx.LT() != null) {
			compOp = CompareOperator.LT;
		} else if (ctx.GE() != null) {
			compOp = CompareOperator.GTE;
		} else if (ctx.LE() != null) {
			compOp = CompareOperator.LTE;
		}

		ParseReturn pr = new ParseReturn(ParseReturnValue.CompOp);
		pr.setCompOp(compOp);
		return pr;
	}

	@Override
	public ParseReturn visitMultOp(@NotNull BGDParser.MultOpContext ctx) {
		MultOp multOp = null;
		if (ctx.STAR() != null) {
			multOp = MultOp.Mult;
		} else if (ctx.SLASH() != null) {
			multOp = MultOp.Div;
		}
		// else if (ctx.PERCENT() != null) {
		// multOp = MultOp.Mod;
		// }

		ParseReturn pr = new ParseReturn(ParseReturnValue.MultOp);
		pr.setMultOp(multOp);
		return pr;
	}

	// Help Functions

	public Variable parseReturnToVariable(ParseReturn ret) {
		Variable v = null;
		if (ret.getVar() != null) {
			v = ret.getVar();
		} else if (game.getPlayer(ret.getIDVar()) != null) {
			v = new VarPlayer(game.getPlayer(ret.getIDVar()));
		} else if (game.getLocation(ret.getIDVar()) != null) {
			v = new VarLocation(game.getLocation(ret.getIDVar()));
		} else if (game.getGameObjectInstance(ret.getIDVar()) != null) {
			v = new VarGameObject(game.getGameObjectInstance(ret.getIDVar()));
		}
		return v;
	}

	public Variable parseReturnToVariable(ParseReturn ret, MultiScopeVariableManager ssvm) {
		Variable v = null;
		if (ret.getVar() != null) {
			v = ret.getVar();
		} else if (ret.getCodeValue() != null) {
			v = ret.getCodeValue().getValue(ssvm);
		} else if (game.getPlayer(ret.getIDVar()) != null) {
			v = new VarPlayer(game.getPlayer(ret.getIDVar()));
		} else if (game.getLocation(ret.getIDVar()) != null) {
			v = new VarLocation(game.getLocation(ret.getIDVar()));
		} else if (game.getGameObjectInstance(ret.getIDVar()) != null) {
			v = new VarGameObject(game.getGameObjectInstance(ret.getIDVar()));
		} else {
			System.out.println("Error, parsereturn could not be converted to Variable");
		}
		return v;
	}
}
