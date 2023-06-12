import java.util.Random;

public class MultiChiffre {
    private final int randKey;
    private int key;
    private final Txt txt;
    private final char[] buchstaben;

    public MultiChiffre() {
        this.txt = new Txt("C:\\Users\\ckdkcdkccv\\Downloads", "Gedicht.txt");
        this.buchstaben = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        this.randKey = getRandomKey();
    }

    public void setKey(int key) {
        this.key = key;
    }

    private int getRandomKey() {
        int[] keys = {3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
        Random random = new Random();
        int index = random.nextInt(keys.length);
        return keys[index];
    }


    public void verschliessen() {
         txt.text = new StringBuilder();

        txt.reader();
        txt.replaceGermanCharacters(1);
        txt.lowerUpperCase(1);

        for (int i = 0; i < txt.getText().length(); i++) {
            char originalChar = txt.getText().charAt(i);
            int originalIndex = -1;
            for (int j = 0; j < buchstaben.length; j++) {
                if (originalChar == buchstaben[j]) {
                    originalIndex = j;
                    break;
                }
            }

            if (originalIndex >= 0 ) {
                int encryptedIndex = (originalIndex * key) % 26; // Berechnung des verschlüsselten Index
                txt.text.append(encryptedIndex);
                txt.text.append(" ");

            } else {
                // Nicht alphabetische Zeichen bleiben unverschlüsselt
                txt.text.append(originalChar);
            }
        }

        txt.setText(txt.text);
        txt.writer(txt.getText());
    }
     public void randVerschliessen() {
        StringBuilder verschlueserteTxt = new StringBuilder();
        int alphabetSize = buchstaben.length;
        txt.reader();
        txt.replaceGermanCharacters(1);
        txt.lowerUpperCase(1);

        for (int i = 0; i < txt.getText().length(); i++) {
            char originalChar = txt.getText().charAt(i);
            int originalIndex = -1;
            for (int j = 0; j < buchstaben.length; j++) {
                if (originalChar == buchstaben[j]) {
                    originalIndex = j;
                    break;
                }
            }

            if (originalIndex >= 0 ) {
                int encryptedIndex = (originalIndex + randKey) % alphabetSize;
                char encryptedChar = buchstaben[encryptedIndex];
                verschlueserteTxt.append(encryptedChar);
            } else {
                verschlueserteTxt.append(originalChar);
            }
        }

        txt.setText(verschlueserteTxt);
    }    private int modularInverse(int a, int m) {
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
        txt.reader();
        txt.replaceGermanCharacters(-1);
        txt.lowerUpperCase(-1);

        for (int i = 0; i < txt.getText().length(); i++) {
            char encryptedChar = txt.getText().charAt(i);
            int encryptedIndex = -1;
            for (int j = 0; j < buchstaben.length; j++) {
                if (encryptedChar == buchstaben[j]) {
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
        int g = 26;

        String languageFrequencies = "enisratdhulcgmobwfkzpvyjxq";

        // Step 1: Determine the frequency of letters in the ciphertext
        int[] letterFrequencies = new int[26]; // number of letters
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
            int decryptedIndex = (encryptedIndex * modularInverse(key, g)) % g;
            char decryptedChar = buchstaben[decryptedIndex];

            // Check if the decrypted character matches the expected most frequent letter in the language
            if (decryptedChar == languageFrequencies.charAt(0)) {
                setKey(key); // Set the correct key
                entschliessen(); // Decrypt the ciphertext with the known key
                return;
            }
        }

        // No match found using the most frequent letter in the language
        // Try again using the next most frequent letter in the language
        for (int j = 1; j < languageFrequencies.length(); j++) {
            char expectedMostFrequentLetter = languageFrequencies.charAt(j);

            for (int key : keys) {
                int encryptedIndex = mostFrequentLetter - 'a';
                int decryptedIndex = (encryptedIndex * modularInverse(key, g)) % g;
                char decryptedChar = buchstaben[decryptedIndex];

                if (decryptedChar == expectedMostFrequentLetter) {
                    setKey(key);
                    entschliessen();
                    return;
                }
            }
        }
    }    public Txt getTxt(){
        return txt;
    }



}
