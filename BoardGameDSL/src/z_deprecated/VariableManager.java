package z_deprecated;

import java.util.HashMap;

//Variable Manager for a single 
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

	// public void setVariableType(String id, String type) {
	// varTypes.put(id, type);
	// }

	public String getVariableType(String id) {
		return varTypes.get(id);
	}

	public void setIDVariable(String id, String val) {
		varTypes.put(id, "ID");
		idVariables.put(id, val);
	}

	public String getIDVarialbe(String id) {
		return idVariables.get(id);
	}

	public void setIntVariable(String id, int val) {
		varTypes.put(id, "Int");
		intVariables.put(id, val);
	}

	public int getIntVarialbe(String id) {
		return intVariables.get(id);
	}

	public void setBoolVariable(String id, boolean val) {
		varTypes.put(id, "Bool");
		boolVariables.put(id, val);
	}

	public boolean getBoolVarialbe(String id) {
		return boolVariables.get(id);
	}

	public void setDoubleVariable(String id, double val) {
		varTypes.put(id, "Double");
		doubleVariables.put(id, val);
	}

	public double getDoubleVarialbe(String id) {
		return doubleVariables.get(id);
	}

	public VariableManager getCopy() {
		VariableManager vm = new VariableManager();
		for (String key : idVariables.keySet()) {
			vm.setIDVariable(key, idVariables.get(key));
		}
		for (String key : intVariables.keySet()) {
			vm.setIntVariable(key, intVariables.get(key));
		}
		for (String key : boolVariables.keySet()) {
			vm.setBoolVariable(key, boolVariables.get(key));
		}
		for (String key : doubleVariables.keySet()) {
			vm.setDoubleVariable(key, doubleVariables.get(key));
		}

		return vm;
	}

}
