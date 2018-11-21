package boardGameStateMachine.stateModel;

import boardGameStateMachine.util.IDManager;
import boardGameStateMachine.util.IDType;

public class GameObjectTemplate {

	private String objectTypeName;

	private IDManager idManager;
	private Game game;
	private String generatorName;
	private int generatorcounter = 1;

	public GameObjectTemplate(String objectType, Game g) {
		this(objectType, g, objectType);
	}
	
	public GameObjectTemplate(String objectType, Game g, String generatorName){
		this.generatorName = generatorName;
		this.objectTypeName = objectType;
		this.idManager = new IDManager();
		this.game = g;
		idManager.addID("Owner", IDType.Player, "Public");
	}

	public IDManager getIDManager() {
		return idManager;
	}

	public String getObjectType() {
		return objectTypeName;
	}

	public GameObjectInstance getNewInstance(String name, Location loc) {
		GameObjectInstance instance = new GameObjectInstance(name, objectTypeName, idManager.getCopy(),game,loc);

		return instance;
	}
	
	public GameObjectInstance getNewInstance(Location loc){
		String name = generatorName + String.valueOf(generatorcounter);
		generatorcounter++;
		return getNewInstance(name, loc);
		
	}
}
