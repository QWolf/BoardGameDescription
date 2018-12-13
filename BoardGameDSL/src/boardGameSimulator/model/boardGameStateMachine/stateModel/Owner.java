package boardGameSimulator.model.boardGameStateMachine.stateModel;

import boardGameSimulator.model.boardGameStateMachine.Variable.VarOwner;
import boardGameSimulator.model.boardGameStateMachine.Variable.VarOwner.OwnerType;

public class Owner {

	private VarOwner.OwnerType type = null;
	private Player owner = null;
	private Location locationOwner = null;

	/**
	 * Owner Object for Public or Location
	 * 

	 */
	public Owner() {
		this.owner = null;
		this.type = VarOwner.OwnerType.Public;
		this.locationOwner = null;
	}

	public Owner(Player p) {
		this.owner = p;
		this.type = VarOwner.OwnerType.Player;
		this.locationOwner = null;
	}

	public Owner(Location l) {
		this.owner = null;
		this.type = VarOwner.OwnerType.Location;
		this.locationOwner = l;
	}

	public boolean isSameOwner(Owner o) {
		OwnerType owner1 = type;
		OwnerType owner2 = o.getOwnerType();
		Player player1 = owner;
		Player player2 = o.getPlayerOwner();

		//Resolve Location Owner Type of this class to Public or Player
		if (owner1 == VarOwner.OwnerType.Location) {
			VarOwner own = (VarOwner) locationOwner.getVarManager().getVariable("Owner");
			owner1 = own.getValue().getOwnerType();
			if (owner1 == VarOwner.OwnerType.Player) {
				player1 = own.getValue().getPlayerOwner();
			}
		}
		//Resolve Location Owner Type of other class
		if (owner1 == VarOwner.OwnerType.Location) {
			VarOwner own = (VarOwner) locationOwner.getVarManager().getVariable("Owner");
			owner1 = own.getValue().getOwnerType();
			if (owner1 == VarOwner.OwnerType.Player) {
				player1 = own.getValue().getPlayerOwner();
			}
		}


		if (owner1 != owner2) {
			// Different type -> Public vs player -> not the same
			return false;
		} else if (type == VarOwner.OwnerType.Public && o.type == VarOwner.OwnerType.Public) {
			// Both public
			return true;
		}else if(player1.getName() == player2.getName()){
			//Both players, check if same
			return true;
		}else{
			return false;
		}
	}

	private void voidOwner() {
		this.owner = null;
		this.locationOwner = null;
		this.type = null;
	}

	public void setOwner(Player p) {
		voidOwner();
		this.owner = p;
		this.type = VarOwner.OwnerType.Player;
	}

	public void setOwnerPublic() {
		voidOwner();
		this.type = VarOwner.OwnerType.Public;
	}

	public void setOwner(Location l) {
		voidOwner();
		this.type = VarOwner.OwnerType.Location;
		this.locationOwner = l;
	}

	public VarOwner.OwnerType getOwnerType() {
		return type;
	}

	public Player getPlayerOwner() {
		return owner;
	}

	public Location getLocationOwner() {
		return locationOwner;
	}
	
	public String toString(){
		switch(type){
		case Location:
			return "Location";
		case Player:
			return owner.getName();
		case Public:
			return "Public";
		default:
			break;
		
		}
		return null;
	}
}
