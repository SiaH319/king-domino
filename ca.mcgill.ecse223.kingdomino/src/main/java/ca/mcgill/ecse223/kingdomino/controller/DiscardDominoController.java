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
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
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
	
	/**
	 * Feature 18 : Discard Domino
	 * @author Mohamad Dimassi.
	 * @param DominoInKingdom that we want to see if it can be discarded.
	 * @return true if the domino can be placed correctly placed in the kingdom, false otherwise.
	 */
	public static boolean attempt_discard_selected_domino(DominoInKingdom dominoInKingdom) {
		Game game = KingdominoApplication.getKingdomino().getCurrentGame();
		Player currentPl = game.getPlayer(0);
		Kingdom kingdom = currentPl.getKingdom();
		Castle castle = getCastle(kingdom);
		Domino domino=currentPl.getDominoSelection().getDomino();
		TerrainType leftTile =domino.getLeftTile();
		TerrainType righTile =domino.getRightTile();
		

		Square[] grid = GameController.getGrid(currentPl.getUser().getName());

        ArrayList<DominoInKingdom.DirectionKind> directions =new ArrayList<DominoInKingdom.DirectionKind>();
        directions.add(DirectionKind.Down);
        directions.add(DirectionKind.Left);
        directions.add(DirectionKind.Up);
        directions.add(DirectionKind.Right);

        for(int x=-4;x<5;x++) {
        	for(int y=-4;y<5;y++) {
        		for(DirectionKind dir :directions ) {
//        			
        			dominoInKingdom.setDirection(dir);
        			dominoInKingdom.setX(x);
        			dominoInKingdom.setY(y);
        			
        			if((VerificationController.verifyGridSize(currentPl.getKingdom().getTerritories()))  && (VerificationController.verifyNoOverlapping(castle,grid,dominoInKingdom)) && ((VerificationController.verifyCastleAdjacency(castle,dominoInKingdom)) || (VerificationController.verifyNeighborAdjacency(castle,grid,dominoInKingdom)))) {
        				System.out.println("Found a place where we can place the domino with x= "+x+" y="+y+"direction ="+dir);
        				System.out.println("A this x,y there is :"+ grid[Square.convertPositionToInt(x,y)].getTerrain());
        				return true;
        			}
        		}
        	}
        }
        System.out.println("couldnt place the domino anywhere");
        return false;
		
	}
    private static Castle getCastle (Kingdom kingdom) {
        for(KingdomTerritory territory: kingdom.getTerritories()){
            if(territory instanceof Castle )
                return (Castle)territory;
        }
        return null;
    }

}
