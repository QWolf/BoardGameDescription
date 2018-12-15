package boardGameSimulator.model.boardGameStateMachine.CodeValue;

import boardGameSimulator.model.boardGameStateMachine.Variable.MultiScopeVariableManager;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarInt;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarList;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarType;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

public class CodeValueListIndex extends CodeValue{

	private CodeValue listcv;
	private CodeValue intcv;

	public CodeValueListIndex(CodeValue listcv, CodeValue intcv) {
		super(VarType.Unspecified);
		this.listcv = listcv;
		this.intcv = intcv;
	}

	@Override
	public Variable getValue(MultiScopeVariableManager scope) {
		VarList calculatedList = (VarList) listcv.getValue(scope);
		VarInt calculatedIndex = (VarInt) intcv.getValue(scope);
		Variable returnVariable = calculatedList.getIndex(calculatedIndex.getValue());
		varType = returnVariable.getVarType();
		return returnVariable;
	}

}
