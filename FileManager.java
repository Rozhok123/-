package project;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class FileManager {

    public String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public void writeFile(String content, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write(content);
        }
    }
}