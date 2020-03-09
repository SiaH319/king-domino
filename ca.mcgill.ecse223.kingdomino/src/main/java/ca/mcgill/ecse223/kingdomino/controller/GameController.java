package ca.mcgill.ecse223.kingdomino.controller;

import ca.mcgill.ecse223.kingdomino.model.Game;
import ca.mcgill.ecse223.kingdomino.model.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class GameController {
    private static HashMap<String, Square[]> grids = new HashMap<>();
    private static HashMap<String, DisjointSet> sets = new HashMap<>();

    public void createGivenNumberOfPlayer(Game game, int playerNum){
            if(playerNum < 2 || playerNum > 4)
                throw new IllegalArgumentException("Player Number should be between 2 and 4");
            // Create Players
            for(int i = 0; i< playerNum; i++)
               new Player(game);
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

}