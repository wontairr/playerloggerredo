package wontairr.playerlogger;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerLogger implements ModInitializer {
    public static final String MOD_ID = "playerlogger";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("PlayerLogger initialized.");
    }

    public static void logFile(String fileContents, String fileName) {
        try {
            // Specify the subdirectory and file name
            String subdirectory = "player-logs";
            String filePath = subdirectory + File.separator + fileName + ".txt";

            // Create the subdirectory if it doesn't exist
            File directory = new File(subdirectory);
            if (!directory.exists()) {
                directory.mkdirs(); // Create the directory and its parent directories if necessary
            }

            // Use FileWriter constructor with "true" to enable append mode
            FileWriter myWriter = new FileWriter(filePath, true);

            myWriter.write("\n\n" + fileContents);
            myWriter.close();
            System.out.println("Successfully appended to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
