package ca.mcgill.ecse223.kingdomino.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class implements the serialize() and deserialize() method for saving and loading game file
 * @author Violet Wei
 */
public class PersistenceObjectStream {

    private static String filename = "src/test/resources/save_game_test.mov";

    /**
     * This method can be used to serialize a Game object
     * @param object
     * @author Violet
     */
    public static void serialize(Object object) {
        FileOutputStream fileOutput;
        try {
            fileOutput = new FileOutputStream(filename);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutput);
            outputStream.writeObject(object);
            outputStream.close();
            fileOutput.close();
        } catch (Exception e) {
            throw new RuntimeException("Could not save the game data to file '" + filename + "'.");
        }

    }

    /**
     * This method can be used to deserialize a Game object
     * @return an object e.g. Game
     * @author Violet
     */
    public static Object deserialize() {
        Object object = null;
        ObjectInputStream inputStream;
        try {
            FileInputStream fileInput = new FileInputStream(filename);
            inputStream = new ObjectInputStream(fileInput);
            object = inputStream.readObject();
            inputStream.close();
            fileInput.close();
        } catch (Exception e) {
            object = null;
        }
        return object;
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