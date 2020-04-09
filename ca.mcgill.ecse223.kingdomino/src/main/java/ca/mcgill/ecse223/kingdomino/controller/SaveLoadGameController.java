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
     * unsuccessfully
     * @throws IOException
     * @author Violet Wei
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
     *
     * @param filename example: src/test/resources/save_game_test.mov
     * @return true     file exists
     * false    file not exits
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
     *
     * @param filename the save file to be created - src/test/resources/save_game_test.mov
     * @return true     if the named file does not exist and was successfully created
     * false    the named file already exists
     * @author Violet Wei
     */
    public static boolean createFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Helper method to delete a file
     *
     * @param filename the save file to be deleted - src/test/resources/save_game_test.mov
     * @return true     if and only if the file or directory is successfully deleted
     * false    otherwise
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
     * @throws IOException
     * @author Violet Wei
     */
    public static boolean loadGame(String filename) throws IOException {

        //SaveLoadG']ameController.initializeGame();


        File file = new File(filename);
        Game currentGame = KingdominoApplication.getKingdomino().getCurrentGame();

        List<Integer> p1tiles = new ArrayList<>();
        List<Integer> p2tiles = new ArrayList<>();
        List<Integer> p3tiles = new ArrayList<>();
        List<Integer> p4tiles = new ArrayList<>();

        List<Integer> claimedTiles = new ArrayList<>();
        List<Integer> unclaimedTiles = new ArrayList<>();


        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(filename);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String currentLine;
                while ((currentLine = bufferedReader.readLine()) != null) {
                    // Add Claimed Tiles to Current Draft
                    if (currentLine.startsWith("C")) {
                        String newLine = currentLine.substring(3);
                        String[] claimedTile = newLine.split(", ");
                        Draft draft = new Draft(Draft.DraftStatus.Sorted,currentGame);
                        for (int i = 0; i < claimedTile.length; i++) {
                            claimedTiles.add(Integer.parseInt(claimedTile[i]));
//                            Domino domino = getdominoByID(Integer.parseInt(claimedTile[i]));
//                            draft.addIdSortedDomino(domino);
//                            Player player = currentGame.getPlayer(i);
//                            player.setDominoSelection(null);
//                            new DominoSelection(player, domino, draft);
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
                        if (addTilesIntoPlayerKingdomHasProblem(currentLine, currentGame, 0, p1tiles))
                            return false;
                    } else if (currentLine.startsWith("P2")) {
                        if (addTilesIntoPlayerKingdomHasProblem(currentLine, currentGame, 1, p2tiles))
                            return false;
                    } else if (currentLine.startsWith("P3")) {
                        if (addTilesIntoPlayerKingdomHasProblem(currentLine, currentGame, 2, p3tiles))
                            return false;
                    } else if (currentLine.startsWith("P4")) {
                        if (addTilesIntoPlayerKingdomHasProblem(currentLine, currentGame, 3, p4tiles))
                            return false;
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
        String name = game.getPlayer(0).getUser().getName();
        GameController.setGrid(name, new Square[81]);
        Square[] grid = GameController.getGrid(name);
        for (int i = 4; i >= -4; i--)
            for (int j = -4; j <= 4; j++)
                grid[Square.convertPositionToInt(i, j)] = new Square(i, j);
        return game;
    }

    private static boolean addTilesIntoPlayerKingdomHasProblem(String currentLine, Game currentGame, int index, List<Integer> pTiles) {
        String newLine = currentLine.substring(4);
        String[] p1Tile = newLine.split(", ");
        String name = currentGame.getPlayer(index).getUser().getName();
        Square[] grid = GameController.getGrid(name);
        for (String tile : p1Tile) {
            String[] IdAndTileInfo = tile.split("@");
            //Extract domino Id from first two chars
            int dominoId = Integer.parseInt(IdAndTileInfo[0]);

            //Extract posX, posY, dir
            String tileInfo = IdAndTileInfo[1].substring(1, IdAndTileInfo[1].length() - 1);
            String[] x_y_dir = tileInfo.split(",");
            pTiles.add(dominoId);
            Domino dominoToPlace = SaveLoadGameController.getdominoByID(dominoId);
            Kingdom kingdom = currentGame.getPlayer(index).getKingdom();
            int posX = Integer.parseInt(x_y_dir[0]);
            int posY = Integer.parseInt(x_y_dir[1]);
            String direction = x_y_dir[2];
            DirectionKind directionKind;
            switch (direction) {
                case "R":
                    directionKind = DirectionKind.Right;
                    break;
                case "L":
                    directionKind = DirectionKind.Left;
                    break;
                case "U":
                    directionKind = DirectionKind.Up;
                    break;
                default:
                    directionKind = DirectionKind.Down;
                    break;
            }

            try {
                //Create New DIK
                DominoInKingdom domInKingdom = new DominoInKingdom(posX, posY, kingdom, dominoToPlace);
                domInKingdom.setDirection(directionKind);

                //Verify the placement is valid

                if ((VerificationController.verifyNeighborAdjacency(getCastle(kingdom), grid, domInKingdom) ||
                        VerificationController.verifyCastleAdjacency(getCastle(kingdom), domInKingdom)) &&
                        VerificationController.verifyNoOverlapping(getCastle(kingdom), grid, domInKingdom)) {
                    dominoToPlace.setStatus(Domino.DominoStatus.PlacedInKingdom);
                } else {
                    return true;
                }


                int[] pos = Square.splitPlacedDomino(domInKingdom, grid);
                DisjointSet s = GameController.getSet(name);
                Castle castle = getCastle(kingdom);

                if (grid[pos[0]].getTerrain() == grid[pos[1]].getTerrain())
                    s.union(pos[0], pos[1]);
                GameController.unionCurrentSquare(pos[0],
                        VerificationController.getAdjacentSquareIndexesLeft(castle, grid, domInKingdom), s);
                GameController.unionCurrentSquare(pos[1],
                        VerificationController.getAdjacentSquareIndexesRight(castle, grid, domInKingdom), s);
            } catch (Exception e) {
                return true;
            }
        }
        return false;
    }

    private static void addDefaultUsersAndPlayers(Game game) {
        String[] userNames = {"User1", "User2", "User3", "User4"};
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

    private static Castle getCastle(Kingdom kingdom) {
        for (KingdomTerritory territory : kingdom.getTerritories()) {
            if (territory instanceof Castle)
                return (Castle) territory;
        }
        return null;
    }

}