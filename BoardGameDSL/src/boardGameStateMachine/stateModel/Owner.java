package boardGameStateMachine.stateModel;

import boardGameStateMachine.Variable.VarOwner;

public class Owner {
	
	VarOwner.OwnerType type = null;
	Player owner = null;

	
/**
 * Owner Object for Public or Location
 * @param ot Ownertype Location or Player
 */
	public Owner(VarOwner.OwnerType ot) {
		owner = null;
		type = ot;
	}
	
	public Owner(Player p){
		this.owner = p;
		this.type = VarOwner.OwnerType.Player;
	}
	
	
//TODO VarOwner -> go Fix
}
