package ca.mcgill.ecse223.kingdomino.persistence;

import ca.mcgill.ecse223.kingdomino.model.*;

/**
 * This class implements the save() and load() methods for the Kingdomino Game Application
 * @author Violet Wei
 */
public class KingdominoPersistence {
    
    private static String filename = "src/test/resources/save_game_test.mov";

    /**
     * This method saves the Kingdomino game
     * @param game
     * @author Violet
     */
    public static void save(Game game) {
        PersistenceObjectStream.serialize(game);
    }

    /**
     * This method helps to load the Kingdomino game
     * @return Game
     * @author Violet
     */
    public static Game load() {
        PersistenceObjectStream.setFilename(filename);
        Game game = (Game) PersistenceObjectStream.deserialize();
        // model cannot be loaded - create empty Game
        if (game == null) {
            game = new Game();
        } else {
            game.reinitialize();
        }
        return game;
    }

    /**
     * This method allows to set the filename to the one we want for the Kingdomino Application
     * @param newFileName
     * @author Violet 
     */
    public static void setFilename(String newFileName) {
        filename = newFileName;
    }
}