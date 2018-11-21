package boardGameStateMachine.stateModel;

import boardGameStateMachine.actionModel.CodeLine.CodeLine;
import boardGameStateMachine.actionModel.CodeValue.CodeValue;
import boardGameStateMachine.actionModel.CodeValue.CodeValueBool;
import boardGameStateMachine.util.IDManager;
import boardGameStateMachine.util.IDType;

public class Action extends CodeValue{

	// Name of the action
	private String name;
	//Arguments that need to be supported 
	private String[] requiredArgumentNames;
	private IDType[] requiredArgumentTypes;

	// All requirements must be true for an action to be able to be performed
	private CodeValue[] requirements;
	// Lines of code to execute when this action is chosen
	private CodeLine[] lines;
	private Game game;
	
	private IDManager actionIDM = new IDManager();
	 

	public Action(String name, Game g, CodeValue[] requirements, CodeLine[] cl, String[] argumentNames, IDType[] argumentTypes, IDType returnType) throws Exception {
		super(returnType);
		this.name = name;
		this.game = g;
		this.lines = cl;
		this.requirements = requirements;
		for (CodeValue req : requirements) {
			if (req.getIDType() != IDType.Boolean) {
				//TODO proper error handling
				throw new Exception("Requirement does not resolve to boolean");
			}
		}
		this.requiredArgumentNames = argumentNames;
		this.requiredArgumentTypes = argumentTypes;
	}
	
	//Executes action and returns the value (if appropriate)
	public Object execute(Object[] arguments) throws Exception{
		if(requiredArgumentNames != null){
			for (int i = 0; i < requiredArgumentNames.length-1; i++){
				//TODO check if arguments are of proper type
				if(!actionIDM.addID(requiredArgumentNames[i], requiredArgumentTypes[i], arguments[i])){
					System.out.println("Failed to add to IDManager: "+requiredArgumentNames[i]);
				}
			}
		}
		
		
		if(!mayExecute()){
			throw new Exception("Requirement did not resolve to true");
		} else {
			
			return CodeLine.executeCodeBlockUntilReturn(lines);
		}
		

	}
	
	public boolean mayExecute(){
		for(CodeValue req : requirements){
			//check if each statement resolves to true
			if(! ((CodeValueBool) req).getValue()){
				return false;
			}
		}
		return true;
	}
}
