package project;

public class StatisticalAnalyzer {

    public int findMostLikelyShift(String encryptedText, char[] alphabet, String representativeText) {
        int[] repFreq = new int[alphabet.length];
        int[] encFreq = new int[alphabet.length];
        int repTotal = 0, encTotal = 0;

        for (char c : representativeText.toCharArray()) {
            int idx = findIndex(c, alphabet);
            if (idx != -1) {
                repFreq[idx]++;
                repTotal++;
            }
        }

        for (char c : encryptedText.toCharArray()) {
            int idx = findIndex(c, alphabet);
            if (idx != -1) {
                encFreq[idx]++;
                encTotal++;
            }
        }

        double[] repPerThousand = new double[alphabet.length];
        double[] encPerThousand = new double[alphabet.length];

        for (int i = 0; i < alphabet.length; i++) {
            if (repTotal > 0) {
                repPerThousand[i] = (double) repFreq[i] / repTotal * 1000;
            }
            if (encTotal > 0) {
                encPerThousand[i] = (double) encFreq[i] / encTotal * 1000;
            }
        }

        int bestShift = 0;
        double bestDeviation = Double.MAX_VALUE;

        for (int shift = 0; shift < alphabet.length; shift++) {
            double deviation = 0;
            for (int i = 0; i < alphabet.length; i++) {
                int shiftedIndex = (i + shift) % alphabet.length;
                double diff = repPerThousand[i] - encPerThousand[shiftedIndex];
                deviation += diff * diff;
            }
            if (deviation < bestDeviation) {
                bestDeviation = deviation;
                bestShift = shift;
            }
        }
        return bestShift;
    }

    private int findIndex(char c, char[] alphabet) {
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == c) {
                return i;
            }
        }
        return -1;
    }
}