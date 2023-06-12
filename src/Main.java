public class Main {
    public static void main(String[] args) {
        MultiChiffre multiChiffre = new MultiChiffre();

        // Encrypt the text with a known key
        multiChiffre.setKey(3);
        multiChiffre.verschliessen();
        System.out.println("Encrypted Text:");
        System.out.println(multiChiffre.getTxt().getText());

        // Decrypt the text with the known key
        /*multiChiffre.entschliessen();
        System.out.println("Decrypted Text (Known Key):");
        System.out.println(multiChiffre.getTxt().getText());

        // Encrypt the text with a random key
        multiChiffre.randVerschliessen();
        System.out.println("Encrypted Text (Unknown Key):");
        System.out.println(multiChiffre.getTxt().getText());

        // Decrypt the text with an unknown key using cryptanalysis
        multiChiffre.decryptUnknownKey();
        System.out.println("Decrypted Text (Unknown Key):");
        System.out.println(multiChiffre.getTxt().getText());
    }*/
}
}
