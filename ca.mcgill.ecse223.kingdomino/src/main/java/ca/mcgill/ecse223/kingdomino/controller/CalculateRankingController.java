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
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;
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
			p.setBonusScore(10);
			p.setPropertyScore(10);
		}
		getPlayer("green",game).getKingdom().addProperty(new Property(getPlayer("green",game).getKingdom()));
		getPlayer("green",game).getKingdom().getProperty(0).setCrowns(5);
		getPlayer("green",game).getKingdom().getProperty(0).setSize(5);
		getPlayer("blue",game).getKingdom().addProperty(new Property(getPlayer("blue",game).getKingdom()));
		getPlayer("blue",game).getKingdom().getProperty(0).setCrowns(4);
		getPlayer("blue",game).getKingdom().getProperty(0).setSize(4);
		getPlayer("yellow",game).getKingdom().addProperty(new Property(getPlayer("yellow",game).getKingdom()));
		getPlayer("yellow",game).getKingdom().getProperty(0).setCrowns(3);
		getPlayer("yellow",game).getKingdom().getProperty(0).setSize(3);
		getPlayer("pink",game).getKingdom().addProperty(new Property(getPlayer("pink",game).getKingdom()));
		getPlayer("pink",game).getKingdom().getProperty(0).setCrowns(2);
		getPlayer("pink",game).getKingdom().getProperty(0).setSize(2);
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
			System.out.println("enetered the resolve tie");
			
			Collections.sort(ScoreList);
			ArrayList<Player> ChosenI = new ArrayList<Player>();
			for(int i=ScoreList.size()-1;i>-1;i--) {
				ArrayList<Player> ChosenJ = new ArrayList<Player>();
				Player p1 =getPlayer(ScoreList.get(i),ChosenI,game);
				for(int j=i;j>-1;j--) {
					if(i!=j) {
						if(ScoreList.get(i)==ScoreList.get(j)) {						
							Player p2 =getPlayer(ScoreList.get(j),ChosenJ,game);
							if(largestProperty(p1)<largestProperty(p2)) {
								swapStandings(p1,p2);
							}
							else if(largestProperty(p1)==largestProperty(p2)) {
								if(totalCrowns(p1)<totalCrowns(p2)) {
									swapStandings(p1,p2);
								}
							}
						}

					}
				}
			}
			
		}
		

	}
	public static void swapStandings(Player p1, Player p2) {
		int i=p1.getCurrentRanking();
		int j=p2.getCurrentRanking();
		p2.setCurrentRanking(i);
		p1.setCurrentRanking(j);
	}
	public static int largestProperty(Player p) {
		int max=0;
		if(p==null) {
			System.out.println("Player is null");
		}
		for(Property prop:p.getKingdom().getProperties()) {
			if(prop!=null) {
				if(prop.getSize()>max) {
					max=prop.getSize();
				}
			}
		}
		return max;
	}
	public static int totalCrowns(Player p) {
		int total=0;
		if(p==null) {
			System.out.println("Player is null");
		}
		for(Property prop:p.getKingdom().getProperties()) {
			if(prop!=null) {
				total+=prop.getCrowns();
			}
		}
		return total;
	}
	public static Player getPlayer(int score, ArrayList<Player> AlreadyChosen,Game game) {
		for(Player p:game.getPlayers()) {
			if((p.getBonusScore()+p.getPropertyScore())==score) {
				if(AlreadyChosen.contains(p)==false) {
					AlreadyChosen.add(p);
					System.out.println("Return a none null player");
					return p;
				}
			}
		}
		System.out.println("Return a null player");
		
		return null;
		
	}
	private static Player getPlayer(String color,Game game) {
		switch(color) {
		case "green":
			for( Player p :game.getPlayers()) {
				if(p.getColor().equals(PlayerColor.Green)) {
					return p;
				}
			}
		case "pink":
			for( Player p :game.getPlayers()) {
				if(p.getColor().equals(PlayerColor.Pink)) {
					return p;
				}
			}
		case "blue":
			for( Player p :game.getPlayers()) {
				if(p.getColor().equals(PlayerColor.Blue)) {
					return p;
				}
			}
		case "yellow":
			for( Player p :game.getPlayers()) {
				if(p.getColor().equals(PlayerColor.Yellow)) {
					return p;
				}
			}
		case "yelow":
			for( Player p :game.getPlayers()) {
				if(p.getColor().equals(PlayerColor.Yellow)) {
					return p;
				}
			}
		
		default:
			throw new java.lang.IllegalArgumentException("Invalid color: " + color);
		}
	}

}
