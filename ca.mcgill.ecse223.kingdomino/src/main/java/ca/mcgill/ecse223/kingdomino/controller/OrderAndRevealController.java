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
 *         Created Mar 8, 2020.
 */
public class OrderAndRevealController {
	/**
	 * 
	 * Method that orders the dominoes in the nextDraft.
	 *
	 */
	public static void orderInitiated() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft nextDraft =game.getNextDraft();
		ArrayList<Integer> listIDs = new ArrayList<Integer>();
		for(Domino domino : nextDraft.getIdSortedDominos()) {
			
			listIDs.add(domino.getId());
		}
		Collections.sort(listIDs);
		
		ArrayList<Domino> newIdSorted = new ArrayList<Domino>();
		for(Integer id : listIDs) {
			
			newIdSorted.add(getdominoByID(id));
		}
		nextDraft.setIdSortedDominos(newIdSorted.get(0),newIdSorted.get(1),newIdSorted.get(2),newIdSorted.get(3));
		nextDraft.setDraftStatus(DraftStatus.Sorted);
		game.setNextDraft(nextDraft);
	}
	
	private static Domino getdominoByID(int id) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		for (Domino domino : game.getAllDominos()) {
			if (domino.getId() == id) {
				return domino;
			}
		}
		throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
	}
	/**
	 * 
	 * Reveal the draft flips the dominoes up
	 *
	 */
	public static void revealInitiated() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Draft nextDraft =game.getNextDraft();
		nextDraft.setDraftStatus(DraftStatus.FaceUp);
	}

}
