package project;

import java.util.*;

public class BruteForce {

    public String decryptByBruteForce(String encryptedText, char[] alphabet, String sampleText) {
        String bestResult = "";
        int bestScore = -1;
        Cipher cipher = new Cipher(alphabet);

        for (int key = 1; key < alphabet.length; key++) {
            String decrypted = cipher.decrypt(encryptedText, key);
        int score = 0;

            if (sampleText != null) {
                // Считаем сколько слов совпало
                String[] decryptedWords = decrypted.split(" ");
                String[] sampleWords = sampleText.split(" ");
                for (String dw : decryptedWords) {
                    for (String sw : sampleWords) {
                        if (dw.equals(sw) && dw.length() > 2) score++;
                    }
                }
            } else {
                // Проверяем пробелы и пунктуацию
                for (char c : decrypted.toCharArray()) {
                    if (c == ' ' || c == '.' || c == ',' || c == '!' || c == '?') score++;
                }
            }

            if (score > bestScore) {
                bestScore = score;
                bestResult = decrypted;
            }
        }
        return bestResult;
    }
}
