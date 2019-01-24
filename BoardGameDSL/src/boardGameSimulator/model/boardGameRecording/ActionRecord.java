package boardGameSimulator.model.boardGameRecording;

public class ActionRecord implements Record {

	private String actionName;
	private String[] arguments;
	private String player;

	/**
	 * A record of a single action
	 * @param ar		The name of the Action that is to be performed, as action
	 * @param arguments	The arguments that go with the action, as Variables
	 */
	public ActionRecord(String player, String actionName, String[] arguments) {
		this.player = player;
		this.actionName = actionName;
		this.arguments = arguments;

	}
	
	public String getActionName(){
		return actionName;
	}
	
	public String[] getArguments(){
		return arguments;
	}

	public String getPlayer(){
		return player;
	}

	public String recordToString() {
		String action = "A " + player + " ";
		action += actionName;
		for(String a : arguments){
			action += " " + a;
		}
		return action;
	}

	public String recordToShortString() {
		String action = player + " ";
		action += actionName;
		for(String a : arguments){
			action += " " + a;
		}
		return action;
	}


}
