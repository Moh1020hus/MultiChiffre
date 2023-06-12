import java.io.*;
public class Txt {
    private final File file;

     StringBuilder text;


    public Txt(String path, String fileName) {
        this.file = new File(path, fileName);
        this.text = new StringBuilder();


    }


    public void setText(StringBuilder text) {
        this.text = text;
    }

    public StringBuilder getText() {
        return text;
    }

    public boolean reader() {
        try (
                BufferedReader buffReader = new BufferedReader(
                        new InputStreamReader(
                                new BufferedInputStream(
                                        new FileInputStream(this.file)
                                )
                        )
                );
        ) {
            String line = buffReader.readLine();

            while (line != null) {
                this.text.append(line);
                this.text.append("\n");
                line = buffReader.readLine();
            }
            this.text.deleteCharAt(this.text.length() - 1);

        } catch (FileNotFoundException fnfEx) {
            return false;
        } catch (IOException ioEx) {
            ioEx.printStackTrace(System.err);
            return false;
        }
        return true;
    }

    public boolean writer(StringBuilder x) {
        try (
                PrintWriter pw = new PrintWriter(
                        new BufferedWriter(
                                new FileWriter(this.file)
                        )
                )
        ) {
            while (x.length() > 0) {
                if (x.indexOf("\n") >= 0) {
                    pw.println(x.substring(0, x.indexOf("\n")));
                    x.delete(0, x.indexOf("\n") + 1);
                } else {
                    pw.println(x);
                    x.delete(0, x.length());
                }
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace(System.err);
            return false;
        }
        return true;
    }

    public void replaceGermanCharacters(int a) {
        StringBuilder sb = new StringBuilder(this.text);

        if (a == 1) {
            replaceAll(sb, "ä", "ae");
            replaceAll(sb, "ü", "ue");
            replaceAll(sb, "ö", "oe");
            replaceAll(sb, "ß", "ss");
        } else if (a == -1) {
            replaceAll(sb, "ae", "ä");
            replaceAll(sb, "ue", "ü");
            replaceAll(sb, "oe", "ö");
            replaceAll(sb, "ss", "ß");
        }

        this.text = new StringBuilder(sb.toString());
    }

    private void replaceAll(StringBuilder sb, String target, String replacement) {
        int index = sb.indexOf(target);
        while (index != -1) {
            sb.replace(index, index + target.length(), replacement);
            index = sb.indexOf(target, index + replacement.length());
        }
    }

    public void lowerUpperCase(int a) {
        StringBuilder sb = new StringBuilder(this.text);

        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);

            if (a == 1) {
                if (Character.isUpperCase(c)) {
                    char lowerChar = Character.toLowerCase(c);
                    sb.setCharAt(i, lowerChar);
                }
            } else {
                if (Character.isLowerCase(c)) {
                    char upperChar = Character.toUpperCase(c);
                    sb.setCharAt(i, upperChar);
                }
            }
        }

        this.text = new StringBuilder(sb.toString());
    }
}









