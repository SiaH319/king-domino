package ca.mcgill.ecse223.kingdomino.controller;

import java.util.ArrayList;
import java.util.HashMap;

public class GameController {
    private static HashMap<String, Square[]> grids = new HashMap<>();
    private static HashMap<String, DisjointSet> sets = new HashMap<>();

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
