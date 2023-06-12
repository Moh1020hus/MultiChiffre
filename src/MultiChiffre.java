import java.util.Random;

public class MultiChiffre {
    private final int randKey;
    private int key;
    private final Txt txt;
    private final char[] buchstaben;

    public MultiChiffre() {
        this.txt = new Txt("C:\\Users\\ckdkcdkccv\\Downloads\\Gedicht.txt", "Gedicht.txt");
        this.buchstaben = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        this.randKey = getRandomKey();
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    private int getRandomKey() {
        int[] keys = {3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
        Random random = new Random();
        int index = random.nextInt(keys.length);
        return keys[index];
    }

    public void verschliessen() {
        StringBuilder verschlueserteTxt = new StringBuilder();
        int alphabetSize = buchstaben.length;

        for (int i = 0; i < txt.getText().length(); i++) {
            char originalChar = txt.getText().charAt(i);
            int originalIndex = originalChar - 'a'; // Index des Originalbuchstabens im Alphabet

            if (originalIndex >= 0 && originalIndex < alphabetSize) {
                int encryptedIndex = (originalIndex * key) % alphabetSize; // Berechnung des verschlüsselten Index
                char encryptedChar = buchstaben[encryptedIndex]; // Verschlüsselter Buchstabe
                verschlueserteTxt.append(encryptedChar);
            } else {
                // Nicht alphabetische Zeichen bleiben unverschlüsselt
                verschlueserteTxt.append(originalChar);
            }
        }

        txt.setText(verschlueserteTxt);
    }
    public void randVerschliessen() {
        StringBuilder verschlueserteTxt = new StringBuilder();
        int alphabetSize = buchstaben.length;

        for (int i = 0; i < txt.getText().length(); i++) {
            char originalChar = txt.getText().charAt(i);

            if (Character.isLetter(originalChar)) {
                int originalIndex = originalChar - 'a';

                if (originalIndex >= 0 && originalIndex < alphabetSize) {
                    int encryptedIndex = (originalIndex + randKey) % alphabetSize;
                    char encryptedChar = buchstaben[encryptedIndex];
                    verschlueserteTxt.append(encryptedChar);
                }
            } else {
                verschlueserteTxt.append(originalChar);
            }
        }

        txt.setText(verschlueserteTxt);
    }
    private int modularInverse(int a, int m) {
        int m0 = m;
        int y = 0, x = 1;

        if (m == 1) {
            return 0;
        }

        while (a > 1) {
            int q = a / m;
            int t = m;

            m = a % m;
            a = t;
            t = y;

            y = x - q * y;
            x = t;
        }

        if (x < 0) {
            x += m0;
        }

        return x;
    }

    public void entschliessen() {
        StringBuilder entschlueserteTxt = new StringBuilder();
        int alphabetSize = buchstaben.length;

        for (int i = 0; i < txt.getText().length(); i++) {
            char encryptedChar = txt.getText().charAt(i);
            int encryptedIndex = -1;

            // Find the index of the encrypted character in the alphabet
            for (int j = 0; j < alphabetSize; j++) {
                if (buchstaben[j] == encryptedChar) {
                    encryptedIndex = j;
                    break;
                }
            }

            if (encryptedIndex >= 0) {
                // Calculate the original index using the known key
                int originalIndex = (encryptedIndex * modularInverse(key, alphabetSize)) % alphabetSize;
                char originalChar = buchstaben[originalIndex];
                entschlueserteTxt.append(originalChar);
            } else {
                // Non-alphabetical characters remain unchanged
                entschlueserteTxt.append(encryptedChar);
            }
        }

        txt.setText(entschlueserteTxt);
    }
    public void decryptUnknownKey() {
        int[] keys = {3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
        int alphabetSize = buchstaben.length;

        // Step 1: Determine the frequency of letters in the ciphertext
        int[] letterFrequencies = new int[alphabetSize];
        for (int i = 0; i < txt.getText().length(); i++) {
            char c = txt.getText().charAt(i);
            if (Character.isLetter(c)) {
                int index = c - 'a';
                letterFrequencies[index]++;
            }
        }

        // Step 2: Find the most frequent letter in the ciphertext
        int maxFrequency = 0;
        char mostFrequentLetter = 'a';
        for (int i = 0; i < letterFrequencies.length; i++) {
            if (letterFrequencies[i] > maxFrequency) {
                maxFrequency = letterFrequencies[i];
                mostFrequentLetter = (char) ('a' + i);
            }
        }

        // Step 3: Attempt decryption with each possible key
        for (int key : keys) {
            int encryptedIndex = mostFrequentLetter - 'a';
            int decryptedIndex = (encryptedIndex * modularInverse(key, alphabetSize)) % alphabetSize;
            char decryptedChar = buchstaben[decryptedIndex];

            // Check if the decrypted character matches the expected most frequent letter in the language
            if (decryptedChar == 'e') {
                setKey(key); // Set the correct key
                entschliessen(); // Decrypt the ciphertext with the known key
                return;
            }
        }
    }
    public Txt getTxt(){
        return txt;
    }



}
