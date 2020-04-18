package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.model.Player;

public class TOPlayer {
	Player player=null;
	
	public TOPlayer(Player p) {
		player=p;
	}
	public int getRank() {
		// TODO Auto-generated method stub
		if(player!=null) {
			return player.getCurrentRanking();
		}
		else {
			return 0;
		}
	}
	public Object getTotalScore() {
		if(player!=null) {
			return player.getTotalScore();
		}
		else {
			return 0;
		}
	}
	public Object getBonusScore() {
		if(player!=null) {
			return player.getBonusScore();
		}
		else {
			return 0;
		}
	}
	public Object getName() {
		if(player!=null) {
			return player.getUser().getName();
		}
		else {
			return 0;
		}
	}

}
