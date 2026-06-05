import java.io.*;
import java.util.*;

public class CribDragger {

    public static void main(String[] args) throws IOException {
        String ciphertext1 = "889e18c32d084fb44c49558a97d3c5f7a694325f36ac45752de9a423ea428161";
        String ciphertext2 = "98974d9a2f1408b857071c91c390cdffb794215f79ab09732cfbe066b9428161";

        byte[] c1 = hexStringToByteArray(ciphertext1);
        byte[] c2 = hexStringToByteArray(ciphertext2);

        byte[] xorPlaintexts = new byte[c1.length];
        for (int i = 0; i < c1.length; i++) {
            xorPlaintexts[i] = (byte) (c1[i] ^ c2[i]);
        }

        List<String> wordlist = loadWordlist("wordlist.txt");

        for (String crib : wordlist) {
            byte[] cribBytes = crib.getBytes();

            for (int i = 0; i <= xorPlaintexts.length - cribBytes.length; i++) {
                byte[] possiblePattern = new byte[cribBytes.length];
                for (int j = 0; j < cribBytes.length; j++) {
                    possiblePattern[j] = (byte) (xorPlaintexts[i + j] ^ cribBytes[j]);
                }
                if (isPrintable(new String(possiblePattern))) {
                    System.out.printf("Match at position %d:\n", i);
                    System.out.printf("Crib for P1: '%s'\n", crib);
                    System.out.printf("Crib given on P2: '%s'\n\n", new String(possiblePattern));
                }
            }
        }
    }

    private static boolean isPrintable(String s) {
        for (char c : s.toCharArray()) {
            if (c < 32 || c > 126) return false;
        }
        return true;
    }

    private static byte[] hexStringToByteArray(String hex) {
        int len = hex.length();
        byte[] output = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            output[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                            + Character.digit(hex.charAt(i + 1), 16));
        }
        return output;
    }

    private static List<String> loadWordlist(String filename) throws IOException {
        List<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line.trim());
            }
        }
        return list;
    }
}
