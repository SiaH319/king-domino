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
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom;
import ca.mcgill.ecse223.kingdomino.model.DominoSelection;
import ca.mcgill.ecse223.kingdomino.model.Draft;
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
 *         Created Mar 1, 2020.
 */
public class DiscardDominoController {
	
	
	public static void attempt_discard_selected_domino() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player currentPl = game.getPlayer(0);
		Kingdom kingdom = currentPl.getKingdom();
		Domino domino=currentPl.getDominoSelection().getDomino();
		TerrainType leftTile =domino.getLeftTile();
		TerrainType righTile =domino.getRightTile();
		
	}
	public static boolean canBePlaced(TerrainType tile,Kingdom kingdom) {
		ArrayList<DominoInKingdom> myList = new ArrayList<DominoInKingdom>();
		for(KingdomTerritory kt : kingdom.getTerritories()) {
			if(kt instanceof DominoInKingdom) {
				myList.add((DominoInKingdom) kt);
			}
		}
		
		return false;
	}
}
