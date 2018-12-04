package boardGameSimulator.model.boardGameStateMachine.Variable;

import boardGameSimulator.model.util.StringUtil;
import boardGameSimulator.model.boardGameStateMachine.Variable.Variable;

/**
 * raw Variable, a Variable expressed in plain text, as ID's cannot be evaluated to an 
 * 
 * @author Peter
 *
 */
public class RawVariable {

	private Variable staticValue = null;
	private String id = null;
	private RawVariable[] list = null;

	private boolean isValue = false;
	private boolean isID = false;
	private boolean isList = false;

	public enum RawVariableType {
		Boolean, Int, Double, List, ID;
	}

	public RawVariable(String input) {
		parseString(input);
	}

	// input as a list
	public RawVariable(String[] inputs) {
		parseList(inputs);
	}

	private void parseList(String[] inputs) {
		this.isList = true;
		this.list = new RawVariable[inputs.length];
		for (int i = 0; i < inputs.length; i++) {
			this.list[i] = new RawVariable(inputs[i]);
		}
	}

	private void parseString(String s) {
		String sanitizedString = s.replace(" ", "");
		if (!s.startsWith("[")) {
			if (sanitizedString == "True") {
				staticValue = new VarBool(true);
				isValue = true;
			} else if (sanitizedString == "False") {
				staticValue = new VarBool(false);
				isValue = true;
			} else if (StringUtil.isInteger(sanitizedString)) {
				staticValue = new VarInt(Integer.parseInt(sanitizedString));
				isValue = true;
			} else if (StringUtil.isDouble(sanitizedString)) {
				staticValue = new VarDouble(Double.parseDouble(sanitizedString));
				isValue = true;
			} else {
				// IsID
				id = sanitizedString;
				isID = true;
			}
			return;
		} else {
			// TODO Lists within list unsupported currently
			String[] list = s.split(",");
			list[list.length - 1] = list[list.length - 1].replaceAll("\\]", "");

			parseList(list);

		}

	}

	public Variable getVariable(MultiScopeVariableManager scope) {
		if (isValue) {
			return staticValue;
		} else if (isList) {
			Variable[] varList = new Variable[list.length];
			for (int i = 0; i < list.length;i++) {
				varList[i] = list[i].getVariable(scope);
			}				
			Variable outputVariable = new VarList(varList);
			return outputVariable;
		}else if(isID){
			return scope.getVariable(id);
		}else{
			System.out.println("Unknown RawVariableType");
			return null;
		}

	}

	public boolean isValue() {
		return isValue;
	}

	public static void main(String[] args) {
		
		int[] array = {0,1,2,3};
		for (int i = 0; i < array.length;i++) {
			System.out.println(array[i]);
		}

	}
}
