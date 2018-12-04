package boardGameSimulator.model.boardGameStateMachine.CodeLine;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturnType;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarBool;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarDouble;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarGameObject;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarInt;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarList;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarLocation;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarOwner;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarPlayer;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

public class CodeLineSetVariable extends CodeLine{
	
	
	private CodeValue variablename;
	private CodeValue variablevalue;

	public CodeLineSetVariable(CodeValue par1, CodeValue par2){
		this.variablename = par1;
		this.variablevalue = par2;
	}

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		Variable calculatedVariable = variablename.getValue(scope);
		Variable calculatedNewValue = variablevalue.getValue(scope);
		
		switch(calculatedVariable.getVarType()){
		case Boolean:
			((VarBool) calculatedVariable).setValue(((VarBool) calculatedNewValue).getValue());
			break;
		case Double:
			((VarDouble) calculatedVariable).setValue(((VarDouble) calculatedNewValue).getValue());
			break;
		case Int:
			((VarInt) calculatedVariable).setValue(((VarInt) calculatedNewValue).getValue());
			break;
		case List:
			((VarList) calculatedVariable).setValue(((VarList) calculatedNewValue).getValue());
			break;
		case Location:
			((VarLocation) calculatedVariable).setValue(((VarLocation) calculatedNewValue).getValue());
			break;
		case Object:
			((VarGameObject) calculatedVariable).setValue(((VarGameObject) calculatedNewValue).getValue());
			break;
		case Owner:
			((VarOwner) calculatedVariable).setValue(((VarOwner) calculatedNewValue).getValue());
			break;
		case Player:
			((VarPlayer) calculatedVariable).setValue(((VarPlayer) calculatedNewValue).getValue());
			break;
//		case Round:
//			break;
//		case Unspecified:
//			break;
		default:
			System.out.println("Could not assign to this type, invalid type!");
			break;	
		}

		return new CodeLineReturn(CodeLineReturnType.Empty,false);
	}

}
