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
import java.util.Random;

/**
 * This controller will shuffle the domino in a random way to ensure that all games are different.
 *
 * @author Mohamad.
 *         Created Mar 9, 2020.
 */
public class ShuffleDominoesController {
	/**
	 * 
	 * @author Mohamad
	 * Chose randomly the ID and shuffle the linked list by modifing the linked list
	 *
	 */
	public static void shuffle() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		
		ArrayList<Integer> listOfIDs = new ArrayList<Integer>();
		
		int maxInPile = game.getMaxPileSize();
		
		Random rand = new Random();
		Domino Current = null;
		for(int i=0;i<maxInPile;i++) {
			int randId = rand.nextInt(48);
			randId++;
			while(listOfIDs.contains(randId)) {
				randId=rand.nextInt(48);       // make sure not to get repeated IDs
				randId++;
			}
			
			listOfIDs.add(randId);
			if(i==0) {
				game.setTopDominoInPile(getdominoByID(randId));
				getdominoByID(randId).setStatus(DominoStatus.InPile);   // the first element is a special case because we have to set it as the top of the linked list
				Current=game.getTopDominoInPile();
			}
			else {
				Current.setNextDomino(getdominoByID(randId));
				Current.getNextDomino().setPrevDomino(Current);          // Update the linked list
				Current.getNextDomino().setStatus(DominoStatus.InPile);
				Current=Current.getNextDomino();
			}
		}
		
		for(int i=1;i<49;i++) {
			if(listOfIDs.contains(i)==false) {
				getdominoByID(i).setStatus(DominoStatus.Excluded);
			}
		}
		
		Draft CurrentDraft = new Draft(DraftStatus.FaceDown,game);
		int i =numberOfDominoesPerDraft(game);
		
		for(int j=0;j<i;j++) {
			Domino toBeAdded = game.getTopDominoInPile();
			CurrentDraft.addIdSortedDomino(toBeAdded);
			toBeAdded.setStatus(DominoStatus.InCurrentDraft);                  // add the dominoes in order and make necessary changes to the linked list
			game.setTopDominoInPile(game.getTopDominoInPile().getNextDomino());
		}
		
		game.setCurrentDraft(CurrentDraft);
		game.addAllDraft(CurrentDraft);
		
	}
	
	/**
	 * 
	 * Function that takes an order of ids and make it the one of the pile.
	 * @author Mohamad
	 * @param orderedList : the list we wish to be the order of dominoes in the pile
	 */
	public static void fixedOrder(String orderedList) {
		ArrayList<Integer> ListOfIDs = getListOfIDs(orderedList);
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		game.setTopDominoInPile(getdominoByID(ListOfIDs.get(0)));
		game.getTopDominoInPile().setStatus(DominoStatus.InPile);
		Domino Current=game.getTopDominoInPile();
		for(int i=1;i<ListOfIDs.size();i++) {
			Current.setNextDomino(getdominoByID(ListOfIDs.get(i)));
			Current.getNextDomino().setPrevDomino(Current);
			Current.getNextDomino().setStatus(DominoStatus.InPile);
			Current=Current.getNextDomino();
		}
		for(int i=1;i<49;i++) {
			if(ListOfIDs.contains(i)==false) {
				getdominoByID(i).setStatus(DominoStatus.Excluded);
			}
		}
		Draft CurrentDraft = new Draft(DraftStatus.FaceDown,game);
		int i =numberOfDominoesPerDraft(game); 
		for(int j=0;j<i;j++) {    
			Domino toBeAdded = game.getTopDominoInPile();
			CurrentDraft.addIdSortedDomino(toBeAdded);
			toBeAdded.setStatus(DominoStatus.InCurrentDraft);
			game.setTopDominoInPile(game.getTopDominoInPile().getNextDomino());
		}
		
		game.setCurrentDraft(CurrentDraft);
		game.addAllDraft(CurrentDraft);
		
	}
	
	
	/**
	 * 
	 * takes a string of integers, parse it and returns an arrayList of those integers
	 * @author Mohamad
	 * @param String aListOfIDs
	 * @return ArrayList of Integers
	 */
	private static ArrayList<Integer> getListOfIDs(String aListOfIDs){
		boolean beforeIsDigit =false;
		ArrayList<Integer> myList = new ArrayList<Integer>();
		String [] ids = aListOfIDs.split(", ");
		for(int i=0; i<ids.length;i++) {
			myList.add(Integer.parseInt(ids[i]));
		}

		return myList;
		
	}
	/**
	 * 
	 * returns the number of players per draft depending on the number of players in the game
	 * @author Mohamad
	 * @param game
	 * @return int 
	 */
	private static int numberOfDominoesPerDraft(Game game) {
		switch(game.getNumberOfPlayers()) {
		case 4:
			return 4;
		case 2:
			return 4;
		case 3:
			return 3;
		default:
			throw new java.lang.IllegalArgumentException("Game has invalid number of players: " + game.getNumberOfPlayers());
		}
	}
	/**
	 * 
	 * Given an integer, return the domino with the corresponding integer
	 *
	 * @param int id
	 * @return Domino domino
	 */
	private static Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}

}
