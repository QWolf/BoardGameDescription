package bgd.parser;


import boardGameSimulator.model.boardGameStateMachine.CodeLine.CodeLine;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValue;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueAddOp.AddOp;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueBoolCompare.CompareOperator;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueBoolOperator.BoolOperator;
import boardGameSimulator.model.boardGameStateMachine.CodeValue.CodeValueMultOp.MultOp;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

public class ParseReturn {
	public enum ParseReturnValue {
		Human, AI, Place, Supply, Variable, IDVar, CodeValue, CodeLine, CodeBlock, CodeValueList, BoolOp, AddOp, CompOp, MultOp;
	}

	public ParseReturn(ParseReturnValue prv) {
		this.parseReturn = prv;
	}

	private ParseReturnValue parseReturn;
	private Variable var = null;
	private String IDVarValue = null;
	private CodeValue codeValue = null;
	private CodeValue[] codeValueList = null;
	private CodeLine codeLine = null;
	private CodeLine[] codeBlock = null;
	private BoolOperator boolOp = null;
	private AddOp addOp = null;
	private CompareOperator compOp = null;
	private MultOp multOp = null;
	

	public Variable getVar() {
		return var;
	}

	public void setVar(Variable v) {
		this.var = v;
	}

	public String getIDVar() {
		return IDVarValue;
	}

	public void setIDVar(String iDVarValue) {
		IDVarValue = iDVarValue;
	}

	public ParseReturnValue getParseReturn() {
		return parseReturn;
	}

	public void setParseReturn(ParseReturnValue parseReturn) {
		this.parseReturn = parseReturn;
	}

	public CodeValue getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(CodeValue codeValue) {
		this.codeValue = codeValue;
	}

	public CodeLine getCodeLine() {
		return codeLine;
	}

	public void setCodeLine(CodeLine codeLine) {
		this.codeLine = codeLine;
	}

	public CodeLine[] getCodeBlock() {
		return codeBlock;
	}

	public void setCodeBlock(CodeLine[] codeBlock) {
		this.codeBlock = codeBlock;
	}

	public CodeValue[] getCodeValueList() {
		return codeValueList;
	}

	public void setCodeValueList(CodeValue[] codeValueList) {
		this.codeValueList = codeValueList;
	}

	public BoolOperator getBoolOp() {
		return boolOp;
	}

	public void setBoolOp(BoolOperator boolOp) {
		this.boolOp = boolOp;
	}

	public AddOp getAddOp() {
		return addOp;
	}

	public void setAddOp(AddOp addOp) {
		this.addOp = addOp;
	}

	public CompareOperator getCompOp() {
		return compOp;
	}

	public void setCompOp(CompareOperator compOp) {
		this.compOp = compOp;
	}

	public MultOp getMultOp() {
		return multOp;
	}

	public void setMultOp(MultOp multOp) {
		this.multOp = multOp;
	}

}
