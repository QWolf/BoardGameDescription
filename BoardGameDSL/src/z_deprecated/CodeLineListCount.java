package z_deprecated;

import boardGameSimulator.model.boardGameStateMachine.CodeLine.Return.CodeLineReturn;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarInt;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarList;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

public class CodeLineListCount extends CodeLine{

	private CodeValue list;

	public CodeLineListCount(CodeValue codeValue) {
		this.list = codeValue;
	}

	@Override
	public CodeLineReturn execute(MultiScopeVariableManager scope) {
		Variable calculatedList = list.getValue(scope);
		
		Variable varRet =((VarList) calculatedList).getSize();
		return new CodeLineReturn(varRet, false);
	}

}
