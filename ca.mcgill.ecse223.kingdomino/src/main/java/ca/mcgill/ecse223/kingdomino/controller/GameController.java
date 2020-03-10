package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.KingdominoApplication;
import ca.mcgill.ecse223.kingdomino.model.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/***
*
*
*@author Cecilia
***/

public class GameController {
    private static HashMap<String, Square[]> grids = new HashMap<>();
    private static HashMap<String, DisjointSet> sets = new HashMap<>();

    /////////////////////////////        //////
    ////////////QueryMethods////        //////
    ///////////////////////////        //////
    public static void createGivenNumberOfPlayer(Game game, int playerNum){
            if(playerNum < 2 || playerNum > 4)
                throw new IllegalArgumentException("Player Number should be between 2 and 4");
            // Create Players
            for(int i = 0; i< playerNum; i++)
               new Player(game);
    }

    public static Draft findDraftByDominoId(Game game, int id){
        List<Draft> drafts = game.getAllDrafts();
        for(Draft draft: drafts){
            for(Domino domino: draft.getIdSortedDominos())
                if(domino.getId() == id)
                    return draft;
        }
        return null;
    }

    public static List<Domino> getAllDominobyTerrainType(String terrainString, Game game) {
        List<Domino> dominoList = new ArrayList<>();
        TerrainType terrain = getTerrainType(terrainString);
        for(Domino domino: game.getAllDominos()) {
            if((domino.getRightTile()).equals(terrain) || (domino.getLeftTile()).equals(terrain))
                dominoList.add(domino);
        }
        return dominoList;
    }


    /**
     * Combine current square and all adjacent square into one set
     * @param curIndex
     * @param adjacentIndexes
     * @param s
     */
    public static void unionCurrentSquare(int curIndex, ArrayList<Integer> adjacentIndexes, DisjointSet s){
        for (int i: adjacentIndexes)
            s.union(curIndex, i);
    }
    public static void setSet(String playerName, DisjointSet s) {
        sets.put(playerName, s);
    }

    public static DisjointSet getSet(String playerName) {
        return sets.get(playerName);
    }

    public static void setGrid(String playerName, Square[] gridinput) {
        grids.put(playerName, gridinput);
    }
    public static Square[] getGrid(String playerName){
        return grids.get(playerName);
    }
    public static void clearSets () {
        grids.clear();
    }
    public static void clearGrids () {
        grids.clear();
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
            case "wheat":
                return TerrainType.WheatField;
            case "forest":
                return TerrainType.Forest;
            case "mountain":
                return TerrainType.Mountain;
            case "grass":
                return TerrainType.Grass;
            case "swamp":
                return TerrainType.Swamp;
            case "lake":
                return TerrainType.Lake;
            default:
                throw new java.lang.IllegalArgumentException("Invalid terrain type: " + terrain);
        }
    }

}
