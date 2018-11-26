package boardGameStateMachine.actionModel.CodeValue;

import boardGameStateMachine.stateModel.Variable.Variable;
import boardGameStateMachine.util.CompareType;
import boardGameStateMachine.util.IDManager;
import boardGameStateMachine.util.IDType;

public class CodeValueBoolCompare extends CodeValue_old{

	private String variable1;
	private CompareType compareType;
	private String variable2;

	public CodeValueBoolCompare(IDType vt, String var1, CompareType ct, String var2) {
		super(IDType.Boolean);
		this.variable1 = var1;
		this.compareType = ct;
		this.variable2 = var2;
	}

	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Variable getValue(IDManager idman){
		Variable var1 = idman.getVariable(var1);
		Variable var2 = idman.getVariable(var2);
		
		boolean returnAnswer = true;
		switch(compareType){
		case EQ:
			returnAnswer = var1.getValue() == var2.getValue();
			break;
		case GT:
			returnAnswer = (double) var1.getValue() > (double) var2.getValue();
			break;
		case GTE:
			returnAnswer = (double) var1.getValue >= (double) var2.getValue();
			break;
		case LT:
			returnAnswer = (double) var1.getValue < (double) var2.getValue();
			break;
		case LTE:
			returnAnswer = (double) var1.getValue <= (double) var2.getValue();
			break;
		case NEQ:
			returnAnswer = var1.getValue != var2.getValue();
			break;
		default:
			returnAnswer = false;
		
		}
		
		return new VarBool(IDType.Boolean, returnAnswer);
		
	}

}
