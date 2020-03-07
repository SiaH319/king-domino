package ca.mcgill.ecse223.kingdomino.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import ca.mcgill.ecse223.kingdomino.model.Kingdomino;
import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.BonusOption;
import ca.mcgill.ecse223.kingdomino.model.Castle;
import ca.mcgill.ecse223.kingdomino.model.Domino;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.KingdomTerritory;
import ca.mcgill.ecse223.kingdomino.model.User;
import ca.mcgill.ecse223.kingdomino.model.Player;
import ca.mcgill.ecse223.kingdomino.model.Property;
import ca.mcgill.ecse223.kingdomino.model.TerrainType;
import ca.mcgill.ecse223.kingdomino.model.Kingdom;
/**
 * TODO Put here a description of what this class does.
 *
 * @author Mohamad.
 *         Created Mar 7, 2020.
 */
public class CalculateRankingController {
	public static void calculateRanking() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		ArrayList<Integer> ScoreList = new ArrayList<Integer>();
		for( Player p :game.getPlayers()) {
			ScoreList.add((p.getBonusScore()+p.getPropertyScore()));
		}
		boolean noTie=true;
		for(Player p1 : game.getPlayers()) {
			for(Player p2:game.getPlayers()) {
				if(p1.getColor()!=p2.getColor()) {
					if((p2.getBonusScore()+p2.getPropertyScore())==(p1.getBonusScore()+p1.getPropertyScore())) {
						noTie=false;
					}
				}
					
			}
		}
		
		if(noTie) {
			Collections.sort(ScoreList);
			ArrayList<Player> Chosen = new ArrayList<Player>();
			for(int i=0;i<ScoreList.size();i++) {
				int currentScore = ScoreList.get(i);
				Player player =getPlayer(currentScore,Chosen,game);
				player.setCurrentRanking(ScoreList.size()-i);
			}
		}
		else {
			
		}
		

	}
	
	public static Player getPlayer(int score, ArrayList<Player> AlreadyChosen,Game game) {
		for(Player p:game.getPlayers()) {
			if((p.getBonusScore()+p.getPropertyScore())==score) {
				if(AlreadyChosen.contains(p)==false) {
					AlreadyChosen.add(p);
					return p;
				}
			}
		}
		return null;
		
	}

}
