package bgss.model;

import bgss.util.VariableManager;

public class GameObjectTemplate {

	private String objectTypeName;

	private VariableManager varManager;

	public GameObjectTemplate(String objectType) {
		this.objectTypeName = objectType;
		this.varManager = new VariableManager();
		varManager.setIDVariable("Owner", "Public");
	}

	public VariableManager getVarManager() {
		return varManager;
	}

	public String getObjectType() {
		return objectTypeName;
	}

	public GameObjectInstance getNewInstance(String name) {
		GameObjectInstance instance = new GameObjectInstance(name, objectTypeName, varManager.getCopy());

		return instance;
	}
}
