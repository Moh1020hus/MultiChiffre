public class Main {
    public static void main(String[] args) {
        Txt txt = new Txt("C:\\Users\\ckdkcdkccv\\Downloads", "Gedicht.txt");
        MultiChiffre multiChiffre = new MultiChiffre();


        txt.reader();              // Read the contents of the text file
        multiChiffre.setKey(3);


        multiChiffre.verschliessen();
                                        // Decrypt the text with the known key
        System.out.println("Decrypted Text (Known Key):");
        txt.writer(txt.getText());
}
}
