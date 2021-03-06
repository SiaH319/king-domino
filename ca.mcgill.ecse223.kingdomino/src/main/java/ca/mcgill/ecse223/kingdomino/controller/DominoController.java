package ca.mcgill.ecse223.kingdomino.controller;

import java.io.*;
import java.util.*;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.Domino.DominoStatus;
import ca.mcgill.ecse223.kingdomino.model.Draft.DraftStatus;
import ca.mcgill.ecse223.kingdomino.model.Player.PlayerColor;

/**
 * This class implements the controller methods for Domino
 * @author Violet
 */
public class DominoController {
    /////////////////////////////        //////
    ////////////QueryMethods////        //////
    ///////////////////////////        //////
    static TerrainType getTerrainType(String terrain) {
        switch (terrain) {
            case "W":
                return TerrainType.WheatField;
            case "F":
                return TerrainType.Forest;
            case "M":
                return TerrainType.Mountain;
            case "G":
                return TerrainType.Grass;
            case "S":
                return TerrainType.Swamp;
            case "L":
                return TerrainType.Lake;
            default:
                throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
        }
    }

    public static Domino getdominoByID(int id) {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        for (Domino domino : game.getAllDominos()) {
            if (domino.getId() == id) {
                return domino;
            }
        }
        throw new java.lang.IllegalArgumentException("Domino with ID " + id + " not found.");
    }

    public static Draft getNextDraft(String nextDraft, Game game) {
        Draft draft = new Draft(DraftStatus.Sorted, game);
        String[] dominoids = nextDraft.split(",");
        for (int i = 0; i < dominoids.length; i++) {
            draft.addIdSortedDomino(getdominoByID(Integer.parseInt(dominoids[i])));
        }
        draft.setDraftStatus(DraftStatus.Sorted);
        return draft;
    }

    public static List<Domino> getNextDraftDominos(Draft draft) {
        List<Domino> nextDraftDominos = draft.getIdSortedDominos();
        return nextDraftDominos;
    }

    public static List<DominoSelection> getDominoSelection(Draft draft) {
        List<DominoSelection> dominoSelection = draft.getSelections();
        return dominoSelection;
    }

    public static Domino getDominobyId(Integer id) {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        Domino result = null;
        for(Domino domino: game.getAllDominos()) {
            if(domino.getId() == id)
                result = domino;
        }
        return result;
    }

    public static List<Domino> getAllDominobyleftTtile(TerrainType left) {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        dominosL = null;
        for(Domino domino: game.getAllDominos()) {
            if(domino.getLeftTile() == left)
                dominosL.add(domino);
        }
        return dominosL;
    }

    public static List<Domino> getAllDominobyRightTile(TerrainType right) {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        dominosR = null;
        for(Domino domino: game.getAllDominos()) {
            if(domino.getRightTile() == right)
                dominosR.add(domino);
        }
        return dominosR;
    }

    public static List<Domino> getAllDominobyTerrainType(String terrain) {
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        dominos = null;
        for(Domino domino: game.getAllDominos()) {
            if((domino.getRightTile()).toString().equalsIgnoreCase(terrain) || (domino.getLeftTile()).toString().equalsIgnoreCase(terrain))
                dominos.add(domino);
        }
        return dominos;
    }
    public static int getDominoTotalCrown(Domino domino) {
        return domino.getLeftCrown() + domino.getRightCrown();
    }

    public static String getTerrainTypeString(TerrainType terrain) {
        switch (terrain) {
            case WheatField:
                return "wheat";
            case Forest:
                return "forest";
            case Mountain:
                return "mountain";
            case Grass:
                return "grass";
            case Swamp:
                return "swamp";
            case Lake:
                return "lake";
            default:
                throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
        }
    }
    static Player currentPlayer;
	private static List<Domino> dominosL;
	private static List<Domino> dominosR;
	private static List<Domino> dominos;


    /////////////////////////////        //////
    ///// ///Feature Methods////        //////
    ///////////////////////////        //////
    /**
     * Controller implemented for Feature 10: Choose Next Domino
     * @param game, current game
     * @param dominoId, domino id
     * @return true if selection for next draft is different
     *         false if selection for next draft remains same
     * @author Violet Wei
     */
    public static boolean chooseNextDomino(Game game,int dominoId) {
        Draft cDraft = game.getNextDraft();
        if(GameplayController.statemachine.getGamestatus() == Gameplay.Gamestatus.Initializing)
            cDraft = game.getCurrentDraft();
        for(DominoSelection dominoSelection: cDraft.getSelections()){
            if(dominoSelection.getDomino().getId() == dominoId)
                return false;
        }
        Player p = game.getNextPlayer();
        if(p.getDominoSelection()!=null){
            p.getDominoSelection().delete();
        }

        Domino domino = getDominobyId(dominoId);
        new DominoSelection(p, domino, cDraft);
        return true;
    }

    /**
     * Feature 11: Move Current Domino
     * As a player, I wish to evaluate a provisional placement of my current
     * domino by moving the domino around into my kingdom (up, down, left, right).
     * @param player
     * @param dominoId
     * @author Violet Wei
     */
    public static void initialMoveDominoToKingdom(Player player, int dominoId){
        Kingdom kingdom = player.getKingdom();
        DominoInKingdom dik = KingdomController.getDominoInKingdomByDominoId(dominoId, kingdom);
        if(dik != null){
            dik.setY(0);
            dik.setX(0);
        }
    }

    /**
     * Feature 11: Move Current Domino
     * As a player, I wish to evaluate a provisional placement of my current
     * domino by moving the domino around into my kingdom (up, down, left, right).
     * up 0, right 1, down 2, left 3
     * @param player
     * @param dominoId
     * @param movement, expressed by int
     * @author Violet Wei
     * @return true if successful or false if fail
     */
    public static boolean moveCurrentDomino(Player player, int dominoId, String movement) {
        Kingdom kingdom = player.getKingdom();
        DominoInKingdom dik = KingdomController.getDominoInKingdomByDominoId(dominoId, kingdom);
        int oldx =dik.getX();
        int oldy = dik.getY();
    	

    	
        int newx = -1; int newy = -1;
        int mov = convertMovementStringToInt(movement);
        Domino domino;
        if(dik != null){
            domino = dik.getDomino();
            switch(mov){
            	case -1:
            		newx=oldx;
            		newy=oldy;
            		break;
                case 0:
                    newx = oldx;
                    newy = oldy + 1;
                    break;
                case 1:
                    newx = oldx + 1;
                    newy = oldy;
                    break;
                case 2:
                    newx = oldx;
                    newy = oldy - 1;
                    break;
                case 3:
                    newx = oldx - 1;
                    newy = oldy;
                    break;
            }
            
            dik.setX(newx); dik.setY(newy);
            Castle castle = KingdomController.getCastle(kingdom);
            Square[] grid = GameController.getGrid(player.getUser().getName());
            if(domino.getStatus() == DominoStatus.InCurrentDraft || VerificationController.verifyGridSize(kingdom.getTerritories())){
                if(VerificationController.verifyNoOverlapping(castle, grid, dik) &&
                        (VerificationController.verifyNeighborAdjacency(castle,grid,dik) || VerificationController.verifyCastleAdjacency(castle,dik)))
                    domino.setStatus(DominoStatus.CorrectlyPreplaced);
                else
                    domino.setStatus(DominoStatus.ErroneouslyPreplaced);
            }else{
                dik.setX(oldx); dik.setY(oldy);
                return false;
            }
        }
        return true;
    }

    /**
     * Feature 12: As a player, I wish to evaluate a provisional placement of my current domino in my kingdom
     * by rotating it (clockwise or counter-clockwise).
     * rotationDir 1 for clockwise, -1 for anticlockwise
     * @author Cecilia Jiang
     * @param castle, the castle
     * @param grid, the square[] grid
     * @param territories, territories under current player's control
     * @param dominoInKingdom, currently selected domino
     * @param rotationDir, rotation direction as String
     */
    public static void rotateExistingDomino(Castle castle, Square[] grid, List<KingdomTerritory> territories,
                                            DominoInKingdom dominoInKingdom, int rotationDir){
        DominoInKingdom.DirectionKind oldDir = dominoInKingdom.getDirection();
        DominoInKingdom.DirectionKind newDir = findDirAfterRotation(rotationDir,oldDir);
        dominoInKingdom.setDirection(newDir);

        if(VerificationController.verifyGridSize(territories) || dominoInKingdom.getDomino().getStatus() == DominoStatus.InCurrentDraft){
            if(VerificationController.verifyNoOverlapping(castle,grid,dominoInKingdom) &&
                    (VerificationController.verifyCastleAdjacency(castle,dominoInKingdom) ||
                            VerificationController.verifyNeighborAdjacency(castle,grid,dominoInKingdom)))
                dominoInKingdom.getDomino().setStatus(DominoStatus.CorrectlyPreplaced);
            else
                dominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
        } else{
            dominoInKingdom.setDirection(oldDir);
        }
    }

    /**
     * Feature 13: As a player, I wish to place my selected domino to my kingdom. If I am satisfied with its placement,
     * and its current position respects the adjacency rules, I wish to finalize the placement.
     * (Actual checks of adjacency conditions are implemented as separate features)
     * @param player, the current player
     * @param id which is id of the domino to place
     * @author: Cecilia Jiang
     */
    public static void placeDomino(Player player, int id) throws java.lang.IllegalArgumentException{
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        DominoInKingdom dominoInKingdom=null;
        Kingdom kingdom=null;
        if(player==null) {
        	player=game.getNextPlayer();
        }
        if(id==0) {
    		kingdom = game.getNextPlayer().getKingdom();
            dominoInKingdom=(DominoInKingdom) kingdom.getTerritory(kingdom.getTerritories().size()-1);

        	id=dominoInKingdom.getDomino().getId();
        }
        Domino domino = player.getDominoSelection().getDomino();
        if(domino.getId() == id && domino.getStatus() == DominoStatus.CorrectlyPreplaced){
    		kingdom = game.getNextPlayer().getKingdom();
            domino.setStatus(DominoStatus.PlacedInKingdom);
            dominoInKingdom=(DominoInKingdom) kingdom.getTerritory(kingdom.getTerritories().size()-1);
            String player0Name = (game.getPlayer(0).getUser().getName());
            Square[] grid = GameController.getGrid(player0Name);
            int[] pos = Square.splitPlacedDomino(dominoInKingdom, grid);
            DisjointSet s = GameController.getSet(player0Name);
            Castle castle = getCastle(kingdom);
            if (grid[pos[0]].getTerrain() == grid[pos[1]].getTerrain())
                s.union(pos[0], pos[1]);
            GameController.unionCurrentSquare(pos[0],
                    VerificationController.getAdjacentSquareIndexesLeft(castle, grid, dominoInKingdom), s);
            GameController.unionCurrentSquare(pos[1],
                    VerificationController.getAdjacentSquareIndexesRight(castle, grid, dominoInKingdom), s);
            System.out.println("added the domino to the kingdom with id: "+id+" at position: "+dominoInKingdom.getX()+":"+dominoInKingdom.getY());
            for(int i = 4; i >=-4; i-- ){
                for(int j = -4 ; j <= 4; j++){
                    int cur = Square.convertPositionToInt(j,i);
                    char c = (grid[cur].getTerrain() == null) ?'/':printTerrain(grid[cur].getTerrain());
                    System.out.print(""+cur+""+c+" ");
                }
                System.out.println();
            }
            System.out.println("Disjoint Set");
            System.out.println(GameController.getSet(player0Name).toString(grid));
        } else
            throw new java.lang.IllegalArgumentException("The current domino placement does not respect the " +
                    "adjacency rules");
    }

    /**
     * Feature 18 : Discard Domino
     * Checks if a Domino can be placed in the player's kingdoms
     * @author Mohamad Dimassi.
     * @param DominoInKingdom that we want to see if it can be discarded.
     * @return true if the domino can be placed correctly placed in the kingdom, false otherwise.
     */
    public static boolean attemptDiscardSelectedDomino(DominoInKingdom dominoInKingdom) throws java.lang.IllegalArgumentException{
        Game game = KingdominoApplication.getKingdomino().getCurrentGame();
        Player currentPl = game.getPlayer(0);
        Kingdom kingdom = currentPl.getKingdom();
        if(dominoInKingdom==null) {
        	throw new java.lang.IllegalArgumentException("DominoInKingdom is not created");
        }
        Castle castle = getCastle(kingdom);        
        Square[] grid = GameController.getGrid(currentPl.getUser().getName());
        ArrayList<DominoInKingdom.DirectionKind> directions =new ArrayList<DominoInKingdom.DirectionKind>();
        directions.add(DominoInKingdom.DirectionKind.Down);
        directions.add(DominoInKingdom.DirectionKind.Left);
        directions.add(DominoInKingdom.DirectionKind.Up);
        directions.add(DominoInKingdom.DirectionKind.Right);
        for(int x=-4;x<5;x++) {// brute force, try every combination of x,y,direction
            for(int y=-4;y<5;y++) {
                for(DominoInKingdom.DirectionKind dir :directions ) {
                    dominoInKingdom.setDirection(dir);
                    dominoInKingdom.setX(x);
                    dominoInKingdom.setY(y);

                    if((VerificationController.verifyGridSize(currentPl.getKingdom().getTerritories()))  && (VerificationController.verifyNoOverlapping(castle,grid,dominoInKingdom)) && ((VerificationController.verifyCastleAdjacency(castle,dominoInKingdom)) || (VerificationController.verifyNeighborAdjacency(castle,grid,dominoInKingdom)))) {
                        //the above is the verification if the placement is possible
                        dominoInKingdom.getDomino().setStatus(DominoStatus.ErroneouslyPreplaced);
                        return true;
                    }
                }
            }
        }
        // if couldn't find a position to place the domino
        dominoInKingdom.getDomino().setStatus(DominoStatus.Discarded);
        return false;

    }

    /////////////////////////////        //////
    ///// ///Helper Methods/////        //////
    ///////////////////////////        //////
    private int[] rightTilePositionAfterRotation(int x_right, int y_right, int rotationDir, DominoInKingdom.DirectionKind oldDir){
        int[] pos = new int[2]; //x,y
        switch(oldDir){
            case Left:
                if(rotationDir == 1){
                    pos[0] = x_right + 1;
                    pos[1] = y_right + 1;
                }else if(rotationDir == -1){
                    pos[0] = x_right + 1;
                    pos[1] = y_right - 1;
                }
                break;
            case Up:
                if(rotationDir == 1){
                    pos[0] = x_right + 1;
                    pos[1] = y_right - 1;
                }else if(rotationDir == -1){
                    pos[0] = x_right - 1;
                    pos[1] = y_right - 1;
                }
                break;
            case Down:
                if(rotationDir == 1){
                    pos[0] = x_right - 1;
                    pos[1] = y_right + 1;
                }else if(rotationDir == -1){
                    pos[0] = x_right + 1;
                    pos[1] = y_right - 1;
                }
                break;
            case Right:
                if(rotationDir == 1){
                    pos[0] = x_right - 1;
                    pos[1] = y_right - 1;
                }else if(rotationDir == -1){
                    pos[0] = x_right - 1;
                    pos[1] = y_right + 1;
                }
                break;
        }
        return pos;
    }

    public static int convertMovementStringToInt(String movement){
        int mov = -1;

        if(movement==null) {
        	return -1;
        }
        if(movement.equals("up") || movement.equals("Up") ) {
            mov = 0;
        } else if(movement.equals("right") || movement.equals("Right") ) {
            mov = 1;
        }else if(movement.equals("down") || movement.equals("Down") ) {
            mov = 2;
        }if(movement.equals("left") || movement.equals("Left") ) {
            mov = 3;
        }
        return mov;
    }

    private static DominoInKingdom.DirectionKind findDirAfterRotation(int rotationDir, DominoInKingdom.DirectionKind oldDir){
        DominoInKingdom.DirectionKind dir = DominoInKingdom.DirectionKind.Up;
        switch(oldDir){
            case Left:
                if(rotationDir == 1){
                    dir = DominoInKingdom.DirectionKind.Up;
                }else if(rotationDir == -1){
                    dir = DominoInKingdom.DirectionKind.Down;
                }
                break;
            case Up:
                if(rotationDir == 1){
                    dir = DominoInKingdom.DirectionKind.Right;
                }else if(rotationDir == -1){
                    dir = DominoInKingdom.DirectionKind.Left;
                }
                break;
            case Down:
                if(rotationDir == 1){
                    dir = DominoInKingdom.DirectionKind.Left;
                }else if(rotationDir == -1){
                    dir = DominoInKingdom.DirectionKind.Right;
                }
                break;
            case Right:
                if(rotationDir == 1){
                    dir = DominoInKingdom.DirectionKind.Down;
                }else if(rotationDir == -1){
                    dir = DominoInKingdom.DirectionKind.Up;
                }
                break;
        }
        return dir;
    }
    private static char printTerrain(TerrainType terrainType){
        char c;
        switch(terrainType){
            case WheatField:
                c = 'W';
                break;
            case Mountain:
                c = 'M';
                break;
            case Lake:
                c = 'L';
                break;
            case Forest:
                c = 'F';
                break;
            case Grass:
                c = 'G';
                break;
            case Swamp:
                c = 'S';
                break;
            default:
                c = '/';
                break;
        }
        return c;
    }

    private static Castle getCastle (Kingdom kingdom) {
        for(KingdomTerritory territory: kingdom.getTerritories()){
            if(territory instanceof Castle )
                return (Castle)territory;
        }
        return null;
    }

}
