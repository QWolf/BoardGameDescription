package boardGameSimulator.model.boardGameStateMachine.stateModel;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.CodeLine;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturnType;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.SingleScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarBool;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarType;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

public class ActionRound {

	// Name of the action
	private String name;
	// Arguments that need to be supported
	private String[] requiredArgumentNames;

	// All requirements must be true for an action to be able to be performed
	private CodeValue[] requirements;
	// Lines of code to execute when this action is chosen
	private CodeLine[] lines;
	private Game game;

	private boolean isAction = true;

	private SingleScopeVariableManager actionVarMan = new SingleScopeVariableManager();
	private MultiScopeVariableManager multiScopeVarMan = null;

	/**
	 * A Round, no requirements given
	 * 
	 * @param name
	 *            Round name
	 * @param g
	 *            Game object
	 * @param body
	 *            Lines to execute
	 * @param argumentNames
	 *            names of arguments
	 */
	public ActionRound(String name, Game g, CodeLine[] body, String[] argumentNames) {
		this(name, g, new CodeValue[0], body, argumentNames);
		this.isAction = false;

	}

	/**
	 * An Action, requirements ARE given
	 * 
	 * @param name
	 *            Round name
	 * @param g
	 *            Game object
	 * @param requirements
	 *            All requirements to be met to execute this action
	 * @param body
	 *            Lines to execute
	 * @param argumentNames
	 *            names of arguments
	 */
	public ActionRound(String name, Game g, CodeValue[] requirements, CodeLine[] body, String[] argumentNames) {
		this.name = name;
		this.game = g;
		this.lines = body;
		this.requirements = requirements;
		this.requiredArgumentNames = argumentNames;
		this.multiScopeVarMan = new MultiScopeVariableManager(game.getVarMan());

		// Check if requirements are actually boolean variables
		for (CodeValue req : requirements) {
			if (req.getType() != VarType.Boolean) {
				// TODO proper error handling
				try {
					throw new Exception("Requirement does not resolve to boolean");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// Construct the Multi-scope variable manager
		multiScopeVarMan.setRoundActionScope(actionVarMan);

	}

	// Executes action and returns the value (if appropriate)
	public CodeLineReturn executeActionRound(Variable[] arguments) {
		if (requiredArgumentNames != null) {
			// TODO: Typechecking arguments (also in parser)
			for (int i = 0; i < requiredArgumentNames.length; i++) {
				if (!actionVarMan.addVariable(requiredArgumentNames[i], arguments[i])) {
					System.out.println(
							"Failed to add to VariableManager: " + requiredArgumentNames[i] + " Name already exists");

				}
				;
			}
		}
		if (isAction && !satisfiesRequirements()) {
			try {
				throw new Exception("Requirement did not resolve to true");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		} else {
			CodeLineReturn clr = new CodeLineReturn(CodeLineReturnType.Repeat, false);
			while (clr.isRepeat()) {
				clr = CodeLine.executeCodeBlockUntilReturn(lines, multiScopeVarMan);
			}
			return clr;
		}
	}

	public CodeLineReturn executeActionRound() {
		return executeActionRound(null);
	}

	public boolean satisfiesRequirements() {
		for (CodeValue req : requirements) {
			// check if each statement resolves to true
			VarBool reqVariable = (VarBool) req.getValue(multiScopeVarMan);

			if (!reqVariable.getValue()) {
				return false;
			}
		}
		return true;
	}

	public String getName() {
		return name;
	}

	public boolean isAction() {
		return isAction;
	}

	public String printNamePars() {
		String returnString = name + "(";
		for (String reqArgname : requiredArgumentNames) {
			returnString += reqArgname + ", ";
		}
		returnString += ")";

		return returnString;
	}
}
