package project;

import java.nio.file.*;

public class Validator {

    public boolean isFileExists(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return false;
        }
        Path path = Paths.get(filePath);
        return Files.exists(path);
    }

    public boolean isValidKey(int key, char[] alphabet) {
        return key >= 0 && key < alphabet.length;
    }
}