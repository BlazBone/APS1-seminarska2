import java.io.*;
import java.util.Arrays;

public class Naloga6 {

    public static void natisniOperand(char a) {
        switch (a) {
            case '*':
                end += "AND" + ",";
                // System.out.print("AND ");
                break;
            case '+':
                // System.out.print("OR ");
                end += "OR" + ",";

                break;
            case '|':
                // System.out.print("NOT ");
                end += "NOT" + ",";

                break;
            default:
                break;
        }
    }

    public static boolean odvecniParOklepajev(String izraz2) {
        // odstrani oklepaje naprimer ((2+4))-->2+4
        char[] izraz = izraz2.toCharArray();
        if (izraz.length == 0) {
            // System.out.println("HWJJJJJJJJ");
            // System.out.println(izraz.toString());
            // System.out.println(izraz2);
            // System.out.println(Arrays.toString(izraz));
            return false;
        }
        if (izraz[0] == '(' && izraz[izraz.length - 1] == ')') {
            int stPrvih = 0;
            int stDrugih = 0;
            for (int i = 0; i < izraz.length; i++) {
                if (izraz[i] == '(') {
                    stPrvih++;
                } else if (izraz[i] == ')') {
                    stDrugih++;
                }

                if (stDrugih == stPrvih) {
                    if (i == (izraz.length - 1)) {
                        return true;
                    }

                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static int najboljDesniNajsibkejsiOperator(String izraz) {
        int bilanca = 0;
        int zadnjiPM = -1;
        int zadnjiKD = -1;
        int zadnjiNEG = -1;

        for (int i = 0; i < izraz.length(); i++) {
            char znak = izraz.charAt(i);
            if (znak == '(' || znak == ')') {
                bilanca += (znak == '(') ? 1 : (-1);
            } else if (bilanca == 0) {
                if (znak == '+') {
                    zadnjiPM = i;
                } else if (znak == '*') {
                    zadnjiKD = i;
                } else if (znak == '|') {
                    zadnjiNEG = i;
                }

            }

        }

        if (zadnjiPM >= 0) {
            return zadnjiPM;
        } else if (zadnjiKD >= 0) {
            return zadnjiKD;
        } else {
            return zadnjiNEG;
        }

        // return (zadnjiPM >= 0) ? zadnjiPM : zadnjiKD;

    }

    public static void izpisi(String izraz) {
        // if (izraz.length() == 0) {
        // // System.out.println("WE HAVE A PROBLEM");
        // }
        //
        while (odvecniParOklepajev(izraz)) {
            izraz = izraz.substring(1, izraz.length() - 1);
        }
        if (left + right + 1 > MAX_DEPTH) {
            MAX_DEPTH = left + right + 1;
        }
        // System.out.println(izraz);

        int polozaj = najboljDesniNajsibkejsiOperator(izraz);
        // ce je polozaj 0 pomeni, da nimamo operatorjev in samo printamo kar imamo na
        // vhodu
        if (polozaj > 0) {
            natisniOperand(izraz.charAt(polozaj));
            // System.out.println(izraz);
            // razdelimo na dva dela in poklicemo rekurzivno
            // levidel
            if (izraz.substring(0, polozaj).length() == 0) {
                // System.out.println("PROBLEM PRI levemu");
                // System.out.println(izraz);
                // System.out.println();
            }
            left++;

            izpisi(izraz.substring(0, polozaj));
            left--;
            // desnidel
            if (izraz.substring(polozaj + 1).length() == 0) {
                // System.out.println("PROBLEM PRI DESNEMU");
                // System.out.println(izraz);
                // System.out.println();
            } else {

                right++;
                izpisi(izraz.substring(polozaj + 1));
                right--;
            }

            return;
        } else if (polozaj == 0) {
            natisniOperand(izraz.charAt(polozaj));

            // ce je polozaj 0 imamo samo desni del
            if (izraz.substring(polozaj + 1).length() == 0) {
                // System.out.println("PROBLEM PRI NEGIRANJU");
                // System.out.println("---------->" + izraz);
                // System.out.println();
                right++;
            } else {

                right++;
                izpisi(izraz.substring(polozaj + 1));
                right--;
            }
        } else {

            if (izraz.equals("T")) {
                end += "TRUE" + ",";

            } else if (izraz.equals("F")) {
                end += "FALSE" + ",";

            } else {

                end += izraz + ",";
            }
            // System.out.print(izraz + " ");
            if (left + right + 1 > MAX_DEPTH) {
                MAX_DEPTH = left + right + 1;
            }
        }

    }

    public static int left = 0;
    public static int right = 0;
    public static int MAX_DEPTH = 0;
    public static String end = "";

    public static void main(String[] args) throws IOException {
        BufferedReader tok = new BufferedReader(new FileReader(args[0]));
        PrintWriter p = new PrintWriter(new FileWriter(args[1]));

        String vnosIzraz = tok.readLine().replace("AND", "*").replace("OR", "+").replace(" ", "")
                .replace("NOT", "|").replace("TRUE", "T").replace("FALSE", "F");
        // String vnosIzraz = tok.readLine().strip().replace("AND", "*").replace("OR",
        // "+").replace(" ", "")
        // .replace("NOT", "|").replace("TRUE", "T").replace("FALSE", "F");
        // System.out.println(vnosIzraz);

        izpisi(vnosIzraz);

        p.println(end.substring(0, end.length() - 1));
        p.println(MAX_DEPTH);
        // System.out.println(end.substring(0, end.length() - 1));
        // System.out.println(MAX_DEPTH);
        p.close();
        tok.close();

    }

}