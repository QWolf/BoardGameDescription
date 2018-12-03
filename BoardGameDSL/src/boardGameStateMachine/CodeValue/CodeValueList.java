package boardGameStateMachine.CodeValue;

import boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameStateMachine.Variable.VarList;
import boardGameStateMachine.Variable.VarType;
import boardGameStateMachine.Variable.Variable;

public class CodeValueList extends CodeValue{
	
	CodeValue[] list = null;

	/**
	 * CodeValue of a list, expressed in codevalues
	 * @param cvs List of Codevalues
	 */
	public CodeValueList(CodeValue[] cvs) {
		super(VarType.List);
		this.list = cvs;
	}

	@Override
	public Variable getValue(MultiScopeVariableManager scope) {
		//Convert CodeValues to Variables
		Variable[] codeValuesAsVariables = new Variable[list.length];
		
		for(int i = 0; i<list.length -1; i++){
			codeValuesAsVariables[i] = list[i].getValue(scope);
		}
		
		//Convert list of Variables to a VarList
		Variable listVariable = new VarList(codeValuesAsVariables);
		return listVariable;
	}

}
