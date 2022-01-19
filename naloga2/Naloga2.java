import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Naloga2 {

    public static long stPovedi(String a) {

        long pike = a.chars().filter(ch -> ch == '.').count();
        long vprasaj = a.chars().filter(ch -> ch == '?').count();
        long klicaj = a.chars().filter(ch -> ch == '!').count();

        return pike + klicaj + vprasaj;
    }

    public static String[] razcleniVPovediOdZadi(String str, int stPovedi) {

        return null;

    }

    public static String[] razcleniVPovedi(String str, int stPovedi) {
        String[] povedi = new String[stPovedi];
        // dve metodi lhko gremo na konc in pole razzclenimo alpa prej samo pazi kr
        // velike zacetnice nas lhko zajebejo
        StringBuilder temp = new StringBuilder();
        String[] besede = str.split(" ");
        int j = stPovedi - 1;
        for (int i = 0; i < besede.length; i++) {
            if (besede[i].contains(".") || besede[i].contains("?") || besede[i].contains("!")) {
                if (i < besede.length - 2) {

                    if (!besede[i + 2].equals(besede[i + 2].toLowerCase())) {
                        temp.append(besede[i]);
                        povedi[j] = temp.toString();
                        j--;
                        temp.setLength(0);
                    } else {
                        temp.append(besede[i] + " " + besede[i + 1]);
                        i++;
                        povedi[j] = temp.toString();
                        j--;
                        temp.setLength(0);
                    }

                } else {
                    temp.append(besede[i] + " ");
                }

            } else {
                temp.append(besede[i] + " ");

            }
        }
        povedi[j] = temp.toString();
        // System.out.println(Arrays.deepToString(povedi));
        return povedi;

    }

    public static String obrniBesedo(String a) {

        StringBuilder beseda = new StringBuilder(a);

        beseda.reverse();

        // print reversed String
        return beseda.toString();
    }

    public static String spremeniVrstniRedBesed(String a) {
        String temp;
        String[] besede = a.split(" ");

        for (int i = 0; i < besede.length - 1; i += 2) {
            temp = besede[i];
            besede[i] = besede[i + 1];
            besede[i + 1] = temp;
        }

        return String.join(" ", besede);
    }

    public static String razvozlaj(String vrstica) {

        int stPovedi = (int) stPovedi(vrstica);
        String[] povedi = razcleniVPovedi(vrstica, stPovedi);
        StringBuilder koncna = new StringBuilder();
        for (String poved : povedi) {
            poved = spremeniVrstniRedBesed(poved);
            for (String beseda : poved.split(" ")) {
                beseda = obrniBesedo(beseda);
                koncna.append(beseda + " ");

            }
        }
        koncna.setLength(koncna.length() - 1);

        return koncna.toString();
    }

    public static void main(String[] args) {

        String vrstica;
        String koncana;
        String[] odstavki = new String[100000];
        int stOdstavkov = 0;
        // bomo nrdili resitev samo za en odstavek za vec odstavku bi fajn blo met
        // podatke kolko jih je kr cene nevemo in bi blo fajn met arraylist
        // mybe pa nerabimo kr lahko usak odstavk posebi popravimo in ga zapoisemo
        // nekam(nebo slo kr je vrsti red odstaku napacen)
        try {

            BufferedReader read = new BufferedReader(new FileReader(args[0]));
            PrintWriter p = new PrintWriter(new FileWriter(args[1]));
            while ((vrstica = read.readLine()) != null) {
                // System.out.println(vrstica);

                odstavki[stOdstavkov] = razvozlaj(vrstica);
                stOdstavkov++;
            }
            // p.println(stOdstavkov);
            if (stOdstavkov % 2 == 0) {
                for (int i = 0; i < stOdstavkov; i++) {
                    if (i % 2 == 0) {
                        p.println(odstavki[stOdstavkov - 2 - i]);
                    } else {
                        p.println(odstavki[i]);
                    }
                }
            } else {
                for (int i = 0; i < stOdstavkov; i++) {
                    if (i % 2 == 0) {
                        p.println(odstavki[stOdstavkov - 1 - i]);
                    } else {
                        p.println(odstavki[i]);
                    }
                }
            }

            // System.out.println(Arrays.deepToString(odstavki));
            p.close();
            read.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
