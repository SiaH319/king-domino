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
	/**
	 * 
	 * Sort the players according to their score,size of largest property and the total number of crowns each one has.
	 *
	 */
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
			ArrayList<Player> Chosen = new ArrayList<Player>();
			System.out.println("enetered the resolve tie");
			Player playerSorted[] = {getPlayer("green",game),getPlayer("pink",game),getPlayer("blue",game),getPlayer("yellow",game)};
			quicksort(playerSorted,0,playerSorted.length-1);
			int position=1;
			for(int i=playerSorted.length-1;i>-1;i--) {
				playerSorted[i].setCurrentRanking(position);
				if(i<3) {
					int j=(i+1);
					if(playersEqual(playerSorted[i],playerSorted[j])) {
						playerSorted[i].setCurrentRanking(playerSorted[j].getCurrentRanking());
					}
				}

				position++;
			}
			

			
		}
		

	}
	/**
	 * 
	 * Helper Method : Tells me if two players are equal
	 *
	 * @param p2:Player 1
	 * @param p1:Player 1
	 * @return true if both have the same scores,crowns total and size of largest property, false otherwise
	 */
	public static boolean playersEqual(Player p1,Player p2) {
		if((p1.getBonusScore()+p1.getPropertyScore())==(p2.getBonusScore()+p2.getPropertyScore())){
			if(largestProperty(p1)==largestProperty(p2)) {
				if(totalCrowns(p1)==totalCrowns(p2)) {
					return true;
				}
			}
		}
		return false;
	}
	public static void swapStandings(Player p1, Player p2) {
		int i=p1.getCurrentRanking();
		int j=p2.getCurrentRanking();
		p2.setCurrentRanking(i);
		p1.setCurrentRanking(j);
	}
	/**
	 * 
	 * Helpler method, gets the size of largets property
	 *
	 * @param p : player
	 * @return the size of the largest property
	 */
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
	/**
	 * 
	 * Helper method: gets the total number of crowns 
	 *
	 * @param p : Player
	 * @return the total number of crowns
	 */
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
					
					return p;
				}
			}
		}

		
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
	
	public static void swap (Player[] arr, int i, int j) {
		Player temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	// Partition using Lomuto partition scheme
	public static int partition(Player[] a, int start, int end)
	{
		// Pick rightmost element as pivot from the array
		Player pivot = a[end];

		// elements less than pivot will be pushed to the left of pIndex
		// elements more than pivot will be pushed to the right of pIndex
		// equal elements can go either way
		int pIndex = start;

		// each time we finds an element less than or equal to pivot,
		// pIndex is incremented and that element would be placed 
		// before the pivot.
		for (int i = start; i < end; i++)
		{
			if ((a[i].getBonusScore()+a[i].getPropertyScore()) < (pivot.getBonusScore()+pivot.getPropertyScore())) {
				swap(a, i, pIndex);
				pIndex++;
			}
			else if((a[i].getBonusScore()+a[i].getPropertyScore()) == (pivot.getBonusScore()+pivot.getPropertyScore())) {
				if(largestProperty(a[i])<largestProperty(pivot)) {
					swap(a, i, pIndex);
					pIndex++;
				}
				else if(largestProperty(a[i])==largestProperty(pivot)) {
					if(totalCrowns(a[i])<=totalCrowns(pivot)) {
						swap(a, i, pIndex);
						pIndex++;
					}
				}
			}
		}

		// swap pIndex with Pivot
		swap(a, end, pIndex);

		// return pIndex (index of pivot element)
		return pIndex;
	}

	// Quicksort routine
	public static void quicksort(Player[] a ,int start, int end)
	{
		// base condition
		if (start >= end) {
			return;
		}

		// rearrange the elements across pivot
		int pivot = partition(a, start, end);

		// recur on sub-array containing elements less than pivot
		quicksort(a, start, pivot - 1);

		// recur on sub-array containing elements more than pivot
		quicksort(a, pivot + 1, end);
	}

}
