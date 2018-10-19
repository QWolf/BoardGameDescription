package bgexe.util;

import java.util.HashMap;

public class VariableManager {
	private HashMap<String, String> varTypes;

	private HashMap<String, String> idVariables;
	private HashMap<String, Integer> intVariables;
	private HashMap<String, Boolean> boolVariables;
	private HashMap<String, Double> doubleVariables;

	public VariableManager() {
		varTypes = new HashMap<String, String>();
		idVariables = new HashMap<String, String>();
		boolVariables = new HashMap<String, Boolean>();
		intVariables = new HashMap<String, Integer>();
		doubleVariables = new HashMap<String, Double>();
	}

	public void setVariableType(String id, String type) {
		varTypes.put(id, type);
	}

	public String getVariableType(String id) {
		return varTypes.get(id);
	}

	public void setIDVariable(String id, String val) {
		idVariables.put(id, val);
	}

	public String getIDVarialbe(String id) {
		return idVariables.get(id);
	}

	public void setIntVariable(String id, int val) {
		intVariables.put(id, val);
	}

	public int getIntVarialbe(String id) {
		return intVariables.get(id);
	}

	public void setBoolVariable(String id, boolean val) {
		boolVariables.put(id, val);
	}

	public boolean getBoolVarialbe(String id) {
		return boolVariables.get(id);
	}

	public void setDoubleVariable(String id, double val) {
		doubleVariables.put(id, val);
	}

	public double getDoubleVarialbe(String id) {
		return doubleVariables.get(id);
	}

}
