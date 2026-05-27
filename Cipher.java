package project;

public class Cipher {
    private char[] alphabet;

    public Cipher(char[] alphabet) {
        this.alphabet = alphabet;
    }

    public String encrypt(String text, int shift) {
        shift = shift % alphabet.length;
        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            int index = findIndex(c);
            if (index != -1) {
                int newIndex = (index + shift) % alphabet.length;
                result.append(alphabet[newIndex]);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public String decrypt(String encryptedText, int shift) {
        shift = shift % alphabet.length;
        StringBuilder result = new StringBuilder();

        for (char c : encryptedText.toCharArray()) {
            int index = findIndex(c);
            if (index != -1) {
                int newIndex = (index - shift + alphabet.length) % alphabet.length;
                result.append(alphabet[newIndex]);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    private int findIndex(char c) {
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == c) {
                return i;
            }
        }
        return -1;
    }
}