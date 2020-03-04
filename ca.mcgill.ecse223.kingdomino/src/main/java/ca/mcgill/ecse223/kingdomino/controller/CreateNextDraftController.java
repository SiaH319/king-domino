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
 *         Created Mar 3, 2020.
 */
public class CreateNextDraftController {
	public static void createNewDraftIsInitiated() {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		if(game.getNextDraft()==null) {
			System.out.println(game.getAllDrafts().size()-1);
			game.setNextDraft(game.getAllDraft(game.getAllDrafts().size()-1));
		}
		game.setCurrentDraft(game.getNextDraft());
		
		if(thereCanBeMoreDrafts(game)) {
			Draft newNextDraft = new Draft(DraftStatus.FaceDown,game);
			
			for(int i=0;i<newNextDraft.maximumNumberOfIdSortedDominos();i++) {
				Domino Top =game.getTopDominoInPile();
				newNextDraft.addIdSortedDomino(Top);
				Top.setStatus(DominoStatus.InNextDraft);
				game.setTopDominoInPile(Top.getNextDomino());
			}
			game.getCurrentDraft().setDraftStatus(DraftStatus.FaceUp);
			game.addAllDraft(newNextDraft);
			game.setNextDraft(newNextDraft);
		}
		else {
			game.setNextDraft(null);
			game.setTopDominoInPile(null);
		}
		
	}
	public static boolean thereCanBeMoreDrafts(Game game) {
		int numberOfDraftsCreated=game.getAllDrafts().size();
		int numberOfPlayers=game.getNumberOfPlayers();
		if(((numberOfDraftsCreated==12) && (numberOfPlayers==3 || numberOfPlayers==4)) || ((numberOfDraftsCreated==6) && (numberOfPlayers==2))) {
			return false;
		}
		else {
			return true;
		}
	}

}
