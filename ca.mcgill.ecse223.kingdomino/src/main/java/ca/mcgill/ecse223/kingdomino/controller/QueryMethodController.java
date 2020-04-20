package ca.mcgill.ecse223.kingdomino.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Player;

public class QueryMethodController {

	public static String getWinner() {
		Game game=KingdominoApplication.getKingdomino().getCurrentGame();
		Player winner=null;
		int score =0;
		for(Player p:game.getPlayers()) {
			if(p.getTotalScore()>score) {
				score=p.getTotalScore();
				winner=p;
			}
		}
		return winner.getColor().toString();
	}

	public static List<TOPlayer> getPlayers() {
		ArrayList<TOPlayer> list = new ArrayList<TOPlayer>();
		Game game=KingdominoApplication.getKingdomino().getCurrentGame();
		for(Player p:game.getPlayers()) {
			list.add(new TOPlayer(p.getColor().toString(),p.getCurrentRanking(),p.getBonusScore(),p.getPropertyScore()));
		}
		return list;
		
	}

}
