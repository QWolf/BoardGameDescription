package boardGameStateMachine.stateModel;

import boardGameStateMachine.CodeLine.CodeLine;
import boardGameStateMachine.CodeLine.CodeLineReturn.CodeLineReturn;
import boardGameStateMachine.CodeLine.CodeLineReturn.CodeLineReturnType;
import boardGameStateMachine.CodeValue.CodeValue;
import boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameStateMachine.Variable.SingleScopeVariableManager;
import boardGameStateMachine.Variable.VarBool;
import boardGameStateMachine.Variable.VarType;
import boardGameStateMachine.Variable.Variable;

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

	private SingleScopeVariableManager actionVarMan = new SingleScopeVariableManager();
	private MultiScopeVariableManager multiScopeVarMan = new MultiScopeVariableManager(game.getVarMan());

	public ActionRound(String name, Game g, CodeValue[] requirements, CodeLine[] body, String[] argumentNames) {
		this.name = name;
		this.game = g;
		this.lines = body;
		this.requirements = requirements;
		this.requiredArgumentNames = argumentNames;

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
			for (int i = 0; i < requiredArgumentNames.length - 1; i++) {
				if (!actionVarMan.addVariable(requiredArgumentNames[i], arguments[i])) {
					System.out.println(
							"Failed to add to VariableManager: " + requiredArgumentNames[i] + " Name already exists");

				}
				;
			}
		}
		if (!satisfiesRequirements()) {
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
	
	public CodeLineReturn executeActionRound(){
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
}
