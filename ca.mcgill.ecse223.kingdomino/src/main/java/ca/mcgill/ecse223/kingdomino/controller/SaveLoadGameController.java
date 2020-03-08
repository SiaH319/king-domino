package ca.mcgill.ecse223.kingdomino.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;
import ca.mcgill.ecse223.kingdomino.model.DominoInKingdom.DirectionKind;
import ca.mcgill.ecse223.kingdomino.persistence.KingdominoPersistence;

/**
 * This class corresponds to the controller methods that will be used for
 * the Load Game feature and the Save Game feature for the Kingdomino Game
 * 
 * @author Violet Wei
 */
public class SaveLoadGameController {

    public static final boolean isValid = true;

	/**
     * Controller implemented for Feature 7: Save Game Save the current game as a
     * file
     * 
     * @param filename example: src/test/resources/save_game_test.mov
     * @return true the game is saved successfully false the game is saved
     *         unsuccessfully
     * @author Violet Wei
     * @throws IOException
     */
    public static boolean saveGame(String filename) throws IOException {
        // check if the system contains file named "<filename>"
        if (!fileExists(filename)) {
            createFile(filename);
        } else {
            // if the file exists, overwrite and update the existing file named "<filename>"
            deleteFile(filename);
            createFile(filename);
        }
        File file = new File(filename);
        Game currentGame = KingdominoApplication.getKingdomino().getCurrentGame();
        List<Player> players = currentGame.getPlayers();
        List<Domino> allDominos = currentGame.getAllDominos();
        List<Draft> allDrafts = currentGame.getAllDrafts();
        Player nextPlayer = currentGame.getNextPlayer();
        Draft currentDraft = currentGame.getCurrentDraft();
        Draft nextDraft = currentGame.getNextDraft();
        Domino topDominoInPile = currentGame.getTopDominoInPile();
        List<BonusOption> selectedBonusOptions = currentGame.getSelectedBonusOptions();

        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));

        try {
            KingdominoPersistence.save(currentGame);
        } catch (RuntimeException e) {
            throw new IOException(e.getMessage());
        }

        return true;
    }

    /**
     * Check if the file exists in the system
     * @param filename  example: src/test/resources/save_game_test.mov
     * @return true     file exists
     *         false    file not exits
     * @author Violet Wei
     */
    public static boolean fileExists(String filename) {
        if (filename.equals("") || filename == null) {
            return false;
        }
        File file = new File(filename);
        return file.exists();
    }

    /**
     * Create a save file 
     * @param filename  the save file to be created - src/test/resources/save_game_test.mov
     * @return true     if the named file does not exist and was successfully created
     *         false    the named file already exists
     * @author Violet Wei
     */
    public static boolean createFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Helper method to delete a file
     * @param filename  the save file to be deleted - src/test/resources/save_game_test.mov
     * @return true     if and only if the file or directory is successfully deleted
     *         false    otherwise
     * @author Violet Wei
     */
    public static boolean deleteFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return false;
        }
        return file.delete();
    }

    /**
     * Controller implemented for Feature 6: Load Game
     * 
     * @param filename
     * @return
     * @author Violet Wei
     * @throws IOException
     */
    public static boolean loadGame(String filename) throws IOException {

        //SaveLoadGameController.initializeGame();

        File file = new File(filename);
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game currentGame = KingdominoApplication.getKingdomino().getCurrentGame();
        
        List<Integer> p1tiles = new ArrayList<>();
        List<Integer> p2tiles = new ArrayList<>();
        List<Integer> p3tiles = new ArrayList<>();
        List<Integer> p4tiles = new ArrayList<>();

        List<Integer> claimedTiles = new ArrayList<>();
        List<Integer> unclaimedTiles = new ArrayList<>();

        Square[] grid = KingdominoController.getGrid();
        for (int i = 4; i >= -4; i--)
            for (int j = -4; j <= 4; j++)
                grid[Square.convertPositionToInt(i, j)] = new Square(i, j);

        /* // Add the domino to a player's kingdom
            Domino dominoToPlace = getdominoByID(id);
            Kingdom kingdom = game.getPlayer(0).getKingdom();
            DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
            domInKingdom.setDirection(dir);
            dominoToPlace.setStatus(Domino.DominoStatus.PlacedInKingdom);
        */ 

        //Square[] grid = KingdominoController.getGrid();

        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(filename);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String currentLine;
                while ((currentLine = bufferedReader.readLine()) != null) {
                    if (currentLine.startsWith("C")) {
                        String newLine = currentLine.substring(3);
                        if (newLine.contains(",")) {
                            System.out.println(newLine);
                            String[] claimedTile = newLine.split(", ");
                            for (int i = 0; i < claimedTile.length; i++) {
                                claimedTiles.add(Integer.parseInt(claimedTile[i]));
                            }
                        } else {
                            claimedTiles.add(Integer.parseInt(newLine));
                        }
                        
                    } else if (currentLine.startsWith("U")) {
                        String newLine = currentLine.substring(3);
                        if (newLine.contains(",")) {
                            String[] unclaimedTile = newLine.split(", ");
                            for (int i = 0; i < unclaimedTile.length; i++) {
                                unclaimedTiles.add(Integer.parseInt(unclaimedTile[i]));
                            }
                        } else {
                            unclaimedTiles.add(Integer.parseInt(newLine));
                        }
                    } else if (currentLine.startsWith("P1")) {
                        String newLine = currentLine.substring(4);
                        String[] p1Tile = newLine.split(", ");
                        for (int i = 0; i < p1Tile.length; i++) {
                            String tile = p1Tile[i];
                            if (tile.charAt(1) == '@') {
                                int dominoId = Integer.parseInt(tile.substring(0, 1));
                                p1tiles.add(dominoId);
                                Domino dominoToPlace = SaveLoadGameController.getdominoByID(dominoId);
                                Kingdom kingdom = currentGame.getPlayer(0).getKingdom();
                                String posInfo = tile.substring(3, tile.length()-1);
                                String[] info = posInfo.split(",");
                                int posx = Integer.parseInt(info[0]);
                                int posy = Integer.parseInt(info[1]);
                                String direction = info[2];
                                DirectionKind directionKind;
                                if (direction.equals("R")) {
                                    directionKind = DirectionKind.Right;
                                } else if (direction.equals("L")) {
                                    directionKind = DirectionKind.Left;
                                } else if (direction.equals("U")) {
                                    directionKind = DirectionKind.Up;
                                } else {
                                    directionKind = DirectionKind.Down;
                                }
                                DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
                                if (VerificationController.verifyNeighborAdjacency(getCastle(kingdom), grid, domInKingdom) &&
                                    VerificationController.verifyCastleAdjacency(getCastle(kingdom), domInKingdom) &&
                                    VerificationController.verifyNoOverlapping(getCastle(kingdom), grid, domInKingdom)) 
                                {
                                    domInKingdom.setDirection(directionKind);
                                    dominoToPlace.setStatus(Domino.DominoStatus.PlacedInKingdom);
                                } else {
                                    return false;
                                }
                            } else {
                                int dominoId = Integer.parseInt(tile.substring(0, 2));
                                p1tiles.add(dominoId);
                                Domino dominoToPlace = SaveLoadGameController.getdominoByID(dominoId);
                                Kingdom kingdom = currentGame.getPlayer(0).getKingdom();
                                String posInfo = tile.substring(4, tile.length()-1);
                                String[] info = posInfo.split(",");
                                int posx = Integer.valueOf(info[0]);
                                int posy = Integer.valueOf(info[1]);
                                String direction = info[2];
                                DirectionKind directionKind;
                                if (direction.equals("R")) {
                                    directionKind = DirectionKind.Right;
                                } else if (direction.equals("L")) {
                                    directionKind = DirectionKind.Left;
                                } else if (direction.equals("U")) {
                                    directionKind = DirectionKind.Up;
                                } else {
                                    directionKind = DirectionKind.Down;
                                }
                                DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
                                if (VerificationController.verifyNeighborAdjacency(getCastle(kingdom), grid, domInKingdom) &&
                                    VerificationController.verifyCastleAdjacency(getCastle(kingdom), domInKingdom) &&
                                    VerificationController.verifyNoOverlapping(getCastle(kingdom), grid, domInKingdom)) 
                                {
                                    domInKingdom.setDirection(directionKind);
                                    dominoToPlace.setStatus(Domino.DominoStatus.PlacedInKingdom);
                                } else {
                                    return false;
                                }
                            }
                        }

                    } else if (currentLine.startsWith("P2")) {
                        String newLine = currentLine.substring(4);
                        String[] p2Tile = newLine.split(", ");
                        for (int i = 0; i < p2Tile.length; i++) {
                            String tile = p2Tile[i];
                            if (tile.charAt(1) == '@') {
                                int dominoId = Integer.parseInt(tile.substring(0, 1));
                                p2tiles.add(dominoId);
                                Domino dominoToPlace = SaveLoadGameController.getdominoByID(dominoId);
                                Kingdom kingdom = currentGame.getPlayer(1).getKingdom();
                                String posInfo = tile.substring(3, tile.length()-1);
                                String[] info = posInfo.split(",");
                                int posx = Integer.parseInt(info[0]);
                                int posy = Integer.parseInt(info[1]);
                                String direction = info[2];
                                DirectionKind directionKind;
                                if (direction.equals("R")) {
                                    directionKind = DirectionKind.Right;
                                } else if (direction.equals("L")) {
                                    directionKind = DirectionKind.Left;
                                } else if (direction.equals("U")) {
                                    directionKind = DirectionKind.Up;
                                } else {
                                    directionKind = DirectionKind.Down;
                                }
                                DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
                                if (VerificationController.verifyNeighborAdjacency(getCastle(kingdom), grid, domInKingdom) &&
                                    VerificationController.verifyCastleAdjacency(getCastle(kingdom), domInKingdom) &&
                                    VerificationController.verifyNoOverlapping(getCastle(kingdom), grid, domInKingdom)) 
                                {
                                    domInKingdom.setDirection(directionKind);
                                    dominoToPlace.setStatus(Domino.DominoStatus.PlacedInKingdom);
                                } else {
                                    return false;
                                }
                            } else {
                                int dominoId = Integer.parseInt(tile.substring(0, 2));
                                p2tiles.add(dominoId);
                                Domino dominoToPlace = SaveLoadGameController.getdominoByID(dominoId);
                                Kingdom kingdom = currentGame.getPlayer(1).getKingdom();
                                String posInfo = tile.substring(4, tile.length()-1);
                                String[] info = posInfo.split(",");
                                int posx = Integer.valueOf(info[0]);
                                int posy = Integer.valueOf(info[1]);
                                String direction = info[2];
                                DirectionKind directionKind;
                                if (direction.equals("R")) {
                                    directionKind = DirectionKind.Right;
                                } else if (direction.equals("L")) {
                                    directionKind = DirectionKind.Left;
                                } else if (direction.equals("U")) {
                                    directionKind = DirectionKind.Up;
                                } else {
                                    directionKind = DirectionKind.Down;
                                }
                                DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
                                if (VerificationController.verifyNeighborAdjacency(getCastle(kingdom), grid, domInKingdom) &&
                                    VerificationController.verifyCastleAdjacency(getCastle(kingdom), domInKingdom) &&
                                    VerificationController.verifyNoOverlapping(getCastle(kingdom), grid, domInKingdom)) 
                                {
                                    domInKingdom.setDirection(directionKind);
                                    dominoToPlace.setStatus(Domino.DominoStatus.PlacedInKingdom);
                                } else {
                                    return false;
                                }
                            }
                        }

                    } else if (currentLine.startsWith("P3")) {
                        String newLine = currentLine.substring(4);
                        String[] p3Tile = newLine.split(", ");
                        for (int i = 0; i < p3Tile.length; i++) {
                            String tile = p3Tile[i];
                            if (tile.charAt(1) == '@') {
                                int dominoId = Integer.parseInt(tile.substring(0, 1));
                                p3tiles.add(dominoId);
                                Domino dominoToPlace = SaveLoadGameController.getdominoByID(dominoId);
                                Kingdom kingdom = currentGame.getPlayer(2).getKingdom();
                                String posInfo = tile.substring(3, tile.length()-1);
                                String[] info = posInfo.split(",");
                                int posx = Integer.parseInt(info[0]);
                                int posy = Integer.parseInt(info[1]);
                                String direction = info[2];
                                DirectionKind directionKind;
                                if (direction.equals("R")) {
                                    directionKind = DirectionKind.Right;
                                } else if (direction.equals("L")) {
                                    directionKind = DirectionKind.Left;
                                } else if (direction.equals("U")) {
                                    directionKind = DirectionKind.Up;
                                } else {
                                    directionKind = DirectionKind.Down;
                                }
                                DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
                                if (VerificationController.verifyNeighborAdjacency(getCastle(kingdom), grid, domInKingdom) &&
                                    VerificationController.verifyCastleAdjacency(getCastle(kingdom), domInKingdom) &&
                                    VerificationController.verifyNoOverlapping(getCastle(kingdom), grid, domInKingdom)) 
                                {
                                    domInKingdom.setDirection(directionKind);
                                    dominoToPlace.setStatus(Domino.DominoStatus.PlacedInKingdom);
                                } else {
                                    return false;
                                }
                            } else {
                                int dominoId = Integer.parseInt(tile.substring(0, 2));
                                p3tiles.add(dominoId);
                                Domino dominoToPlace = SaveLoadGameController.getdominoByID(dominoId);
                                Kingdom kingdom = currentGame.getPlayer(2).getKingdom();
                                String posInfo = tile.substring(4, tile.length()-1);
                                String[] info = posInfo.split(",");
                                int posx = Integer.valueOf(info[0]);
                                int posy = Integer.valueOf(info[1]);
                                String direction = info[2];
                                DirectionKind directionKind;
                                if (direction.equals("R")) {
                                    directionKind = DirectionKind.Right;
                                } else if (direction.equals("L")) {
                                    directionKind = DirectionKind.Left;
                                } else if (direction.equals("U")) {
                                    directionKind = DirectionKind.Up;
                                } else {
                                    directionKind = DirectionKind.Down;
                                }
                                DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
                                if (VerificationController.verifyNeighborAdjacency(getCastle(kingdom), grid, domInKingdom) &&
                                    VerificationController.verifyCastleAdjacency(getCastle(kingdom), domInKingdom) &&
                                    VerificationController.verifyNoOverlapping(getCastle(kingdom), grid, domInKingdom)) 
                                {
                                    domInKingdom.setDirection(directionKind);
                                    dominoToPlace.setStatus(Domino.DominoStatus.PlacedInKingdom);
                                } else {
                                    return false;
                                }
                            }
                        }

                    } else if (currentLine.startsWith("P4")) {
                        String newLine = currentLine.substring(4);
                        String[] p4Tile = newLine.split(", ");
                        for (int i = 0; i < p4Tile.length; i++) {
                            String tile = p4Tile[i];
                            if (tile.charAt(1) == '@') {
                                int dominoId = Integer.parseInt(tile.substring(0, 1));
                                p4tiles.add(dominoId);
                                Domino dominoToPlace = SaveLoadGameController.getdominoByID(dominoId);
                                Kingdom kingdom = currentGame.getPlayer(3).getKingdom();
                                String posInfo = tile.substring(3, tile.length()-1);
                                String[] info = posInfo.split(",");
                                int posx = Integer.parseInt(info[0]);
                                int posy = Integer.parseInt(info[1]);
                                String direction = info[2];
                                DirectionKind directionKind;
                                if (direction.equals("R")) {
                                    directionKind = DirectionKind.Right;
                                } else if (direction.equals("L")) {
                                    directionKind = DirectionKind.Left;
                                } else if (direction.equals("U")) {
                                    directionKind = DirectionKind.Up;
                                } else {
                                    directionKind = DirectionKind.Down;
                                }
                                DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
                                if (VerificationController.verifyNeighborAdjacency(getCastle(kingdom), grid, domInKingdom) &&
                                    VerificationController.verifyCastleAdjacency(getCastle(kingdom), domInKingdom) &&
                                    VerificationController.verifyNoOverlapping(getCastle(kingdom), grid, domInKingdom)) 
                                {
                                    domInKingdom.setDirection(directionKind);
                                    dominoToPlace.setStatus(Domino.DominoStatus.PlacedInKingdom);
                                } else {
                                    return false;
                                }
                            } else {
                                int dominoId = Integer.parseInt(tile.substring(0, 2));
                                p4tiles.add(dominoId);
                                Domino dominoToPlace = SaveLoadGameController.getdominoByID(dominoId);
                                Kingdom kingdom = currentGame.getPlayer(3).getKingdom();
                                String posInfo = tile.substring(4, tile.length()-1);
                                String[] info = posInfo.split(",");
                                int posx = Integer.valueOf(info[0]);
                                int posy = Integer.valueOf(info[1]);
                                String direction = info[2];
                                DirectionKind directionKind;
                                if (direction.equals("R")) {
                                    directionKind = DirectionKind.Right;
                                } else if (direction.equals("L")) {
                                    directionKind = DirectionKind.Left;
                                } else if (direction.equals("U")) {
                                    directionKind = DirectionKind.Up;
                                } else {
                                    directionKind = DirectionKind.Down;
                                }
                                
                                DominoInKingdom domInKingdom = new DominoInKingdom(posx, posy, kingdom, dominoToPlace);
                                if (VerificationController.verifyNeighborAdjacency(getCastle(kingdom), grid, domInKingdom) &&
                                    VerificationController.verifyCastleAdjacency(getCastle(kingdom), domInKingdom) &&
                                    VerificationController.verifyNoOverlapping(getCastle(kingdom), grid, domInKingdom)) 
                                {
                                    domInKingdom.setDirection(directionKind);
                                    dominoToPlace.setStatus(Domino.DominoStatus.PlacedInKingdom);
                                } else {
                                    return false;
                                }
                                
                            }
                        }

                    }
                }
                bufferedReader.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        /*
         * try { KingdominoPersistence.setFilename(filename);
         * KingdominoPersistence.load(); } catch (RuntimeException e) { throw new
         * IOException(e.getMessage()); }
         */

        return true;
    }

    public static List<Integer> getUnclaimedTiles(String line) {
        List<Integer> unclaimedTiles = new ArrayList<>();
        if (line.contains(",")) {
            String[] unclaimedTile = line.split(", ");
            for (int i = 0; i < unclaimedTile.length; i++) {
                unclaimedTiles.add(Integer.parseInt(unclaimedTile[i]));
            }
        } else {
            unclaimedTiles.add(Integer.parseInt(line));
        }
        return unclaimedTiles;
    }

    public static Game initializeGame() {
        // Intialize empty game
        Kingdomino kingdomino = KingdominoApplication.getKingdomino();
        Game game = new Game(48, kingdomino);
        game.setNumberOfPlayers(4);
        kingdomino.setCurrentGame(game);
        // Populate game
        addDefaultUsersAndPlayers(game);
        createAllDominoes(game);
        game.setNextPlayer(game.getPlayer(0));
        KingdominoApplication.setKingdomino(kingdomino);
        KingdominoController.setGrid(new Square[81]);
        Square[] grid = KingdominoController.getGrid();
        for (int i = 4; i >= -4; i--)
            for (int j = -4; j <= 4; j++)
                grid[Square.convertPositionToInt(i, j)] = new Square(i, j);
        return game;
    }

    private static void addDefaultUsersAndPlayers(Game game) {
        String[] userNames = { "User1", "User2", "User3", "User4" };
        for (int i = 0; i < userNames.length; i++) {
            User user = game.getKingdomino().addUser(userNames[i]);
            Player player = new Player(game);
            player.setUser(user);
            player.setColor(Player.PlayerColor.values()[i]);
            Kingdom kingdom = new Kingdom(player);
            new Castle(0, 0, kingdom, player);
        }
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

    private static void createAllDominoes(Game game) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/alldominoes.dat"));
            String line = "";
            String delimiters = "[:\\+()]";
            while ((line = br.readLine()) != null) {
                String[] dominoString = line.split(delimiters); // {id, leftTerrain, rightTerrain, crowns}
                int dominoId = Integer.decode(dominoString[0]);
                TerrainType leftTerrain = getTerrainType(dominoString[1]);
                TerrainType rightTerrain = getTerrainType(dominoString[2]);
                int numCrown = 0;
                if (dominoString.length > 3) {
                    numCrown = Integer.decode(dominoString[3]);
                }
                new Domino(dominoId, leftTerrain, rightTerrain, numCrown, game);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new java.lang.IllegalArgumentException(
                    "Error occured while trying to read alldominoes.dat: " + e.getMessage());
        }
    }

    private static TerrainType getTerrainType(String terrain) {
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

    private static DominoInKingdom.DirectionKind getDirection(String dir) {
        switch (dir) {
            case "up":
                return DominoInKingdom.DirectionKind.Up;
            case "down":
                return DominoInKingdom.DirectionKind.Down;
            case "left":
                return DominoInKingdom.DirectionKind.Left;
            case "right":
                return DominoInKingdom.DirectionKind.Right;
            default:
                throw new java.lang.IllegalArgumentException("Invalid direction: " + dir);
        }
    }

    private static Castle getCastle (Kingdom kingdom) {
        for(KingdomTerritory territory: kingdom.getTerritories()){
            if(territory instanceof Castle )
                return (Castle)territory;
        }
        return null;
    }
    
}