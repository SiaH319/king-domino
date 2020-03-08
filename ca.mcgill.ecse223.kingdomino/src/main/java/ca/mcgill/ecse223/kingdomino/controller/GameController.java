package ca.mcgill.ecse223.kingdomino.controller;

import java.util.HashMap;

public class GameController {
    private static HashMap<String, Square[]> grids = new HashMap<>();

    public static void setGrid(String playerName, Square[] gridinput) {
        grids.put(playerName, gridinput);
    }
    public static Square[] getGrid(String playerName){
        return grids.get(playerName);
    }

    public static void clearGrids () {
        grids.clear();
    }

}
