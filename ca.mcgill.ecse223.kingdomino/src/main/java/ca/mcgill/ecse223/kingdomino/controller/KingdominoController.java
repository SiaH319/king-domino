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
 * This class corresponds to the Kingdomino controller methods
 * 
 * @author Violet Wei & Cecilia Jiang
 */
public class KingdominoController {
    private static Square[] grid;
    public KingdominoController() {

    }

    public void createBonusOption() {

    }

    public void createUser(String name, Kingdomino kingdomino) {

    }

    public void createGame() {

    }

    public void createPlayer() {

    }

    public void createDomino() {

    }
    public void createDraft() {

    }

    public void createDominoKingdom() {

    }

    public void createCastle() {

    }

    public void createProperty() {

    }

    public void createDominoSelection() {

    }

    public static void setGrid(Square[] gridinput) {
        grid = gridinput;
    }
    public static Square[] getGrid(){
        return grid;
    }
}
