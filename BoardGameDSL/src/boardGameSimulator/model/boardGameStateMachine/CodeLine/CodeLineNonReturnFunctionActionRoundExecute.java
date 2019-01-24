package boardGameSimulator.model.boardGameStateMachine.CodeLine;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturnType;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;
import boardGameSimulator.model.boardGameStateMachine.stateModel.ActionRound;
import boardGameSimulator.model.boardGameStateMachine.stateModel.Game;

public class CodeLineNonReturnFunctionActionRoundExecute extends CodeLine{
	
	
	private ActionRound actionround;
	private CodeValue[] arguments;
	
	/**
	 * Executes an action (as a function), it does not care about the return;
	 * 
	 * @param ar		The action which needs to be executed
	 * @param arguments The arguments, expressed in CodeValues
	 */
	public CodeLineNonReturnFunctionActionRoundExecute(ActionRound ar, CodeValue[] arguments, Game g){
		this.actionround = ar;
		this.arguments = arguments;
//		System.out.println("=------ CL NonReturn Funct.ActionExecute");
//		for (Player p : g.getTurnOrder()){
//			System.out.println("Turnorder: " + p.toString());
//		}
//		System.out.println(ar.getName());
//		System.out.println(arguments);
//		for(CodeValue cv : arguments){
//			System.out.println(cv.getType() +"" +  cv);
//		}
//		System.out.println(arguments.length);

	}

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
//		System.out.println("------------CLNonReturnFunctionAction----");
//		System.out.println(actionround);
//		System.out.println(arguments);

		
		Variable[] variables = new Variable[arguments.length];
//		System.out.println(new VarList(variables));
		
		if(actionround.executeActionRound(variables).isInvalidAction()){
			System.out.println("NonReturnAction was invalid!");			
		}
		return new CodeLineReturn(CodeLineReturnType.Empty,false);
	}

}
