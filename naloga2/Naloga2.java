import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Naloga2 {

    static int zacetekPovediVBesede;

    public static long stPovedi(String a) {
        // poved ma lhko poiko klicaj alpa vprasaj ceprow mamo samo pike always
        long pike = a.chars().filter(ch -> ch == '.').count();
        long vprasaj = a.chars().filter(ch -> ch == '?').count();
        long klicaj = a.chars().filter(ch -> ch == '!').count();

        return pike + klicaj + vprasaj;
    }

    // vrnemo zadnjo poved tko d pac pogledamo mal kje je pika glede nalihe in sode
    // pole mal preracunamo od kje do kje sega poved in pole lepo jo sestavimo ter
    // vrnemo in mislim d tudi brisemo
    // also dela ne glede na to kaksne so velike zacetnice s katerimei je mal bol u
    // izi to nrdit ampak ja smo naredili mal bel pravo smer ki bi resila tudi tako
    // poved (Halo Imamo Se Lepo. Ja Mi Tudi.) ofkors obrnejno but we get the point
    public static String vrniZadnjoPoved(String[] besede) {
        StringBuilder temp = new StringBuilder();

        if (besede[zacetekPovediVBesede - 1].contains(".") || besede[zacetekPovediVBesede - 1].contains("?")
                || besede[zacetekPovediVBesede - 1].contains("!")) {
            // poved ima liho stevilo besed
            int zacetekPovedi = 0;
            int dolzinaPovedi = 1;
            for (int i = zacetekPovediVBesede - 2; i >= 0; i--) {

                if (besede[i].contains(".") || besede[i].contains("?") || besede[i].contains("!")) {
                    if (dolzinaPovedi % 2 == 0) {
                        zacetekPovedi = zacetekPovediVBesede - dolzinaPovedi + 1;
                    } else {
                        zacetekPovedi = zacetekPovediVBesede - dolzinaPovedi;
                    }
                    break;
                } else if (i == 0) {
                    zacetekPovedi = 0;
                    break;
                }
                dolzinaPovedi++;
                // dva Ena tri. sest Pet sedem.
            }

            for (int i = zacetekPovedi; i < zacetekPovediVBesede; i++) {
                temp.append(besede[i]);
                if (i < zacetekPovediVBesede - 1) {
                    temp.append(" ");
                }
            }

            zacetekPovediVBesede = zacetekPovedi;

            return temp.toString();

        } else {

            // poved ima sodo stevilo besed
            int zacetekPovedi = 0;
            int dolzinaPovedi = 2;

            for (int i = zacetekPovediVBesede - 3; i >= 0; i--) {

                if (besede[i].contains(".") || besede[i].contains("?") || besede[i].contains("!")) {
                    if (dolzinaPovedi % 2 == 0) {
                        zacetekPovedi = zacetekPovediVBesede - dolzinaPovedi;
                    } else {
                        zacetekPovedi = zacetekPovediVBesede - dolzinaPovedi + 1;
                    }
                    break;
                } else if (i == 0) {
                    zacetekPovedi = 0;
                    break;
                }
                dolzinaPovedi++;
                // dva Ena tri. sest Pet sedem.
            }

            for (int i = zacetekPovedi; i < zacetekPovediVBesede; i++) {
                temp.append(besede[i]);
                if (i < zacetekPovediVBesede - 1) {
                    temp.append(" ");
                }
            }

            zacetekPovediVBesede = zacetekPovedi;

            return temp.toString();

        }

    }
    // se sprehodi skoz use povedi in pole za usako klice nje vrne zadnjo poved in
    // pac jih obrne in spravi nzaja

    public static String[] razcleniVPovediOdZadi(String str, int stPovedi) {

        String[] besede = str.split(" ");
        String[] povedi = new String[stPovedi];
        zacetekPovediVBesede = besede.length;

        int dolzinaTekocePovedi = 0;
        boolean prisliDoPrvePike = true;

        for (int j = 0; j < stPovedi; j++) {
            povedi[j] = vrniZadnjoPoved(besede);
        }

        return povedi;

    }

    // ne uprabljam ker je pri tej metodi velika zacetnica single point of faliure
    // in je bz met tako funkcjio tudi ce bos lo skoz teste ker so pac profesorji
    // prijazni in dajo lahke primere
    // ampak je una bel splosna ampak ta dela podobno samo d gre od sprei in pole
    // pogleda za dve besedi naprej al splohe je velika zacetnika al ne in pole
    // izbere kok besed spada pod eno poved
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

    // sam reverse klicemo ampak damo u metodo i guess zgleda leps pole
    public static String obrniBesedo(String a) {

        StringBuilder beseda = new StringBuilder(a);

        beseda.reverse();

        // print reversed String
        return beseda.toString();
    }

    // i mean pac obrnemo besede mislim d to je fora da 2 1 4 3 => 1 2 3 4
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

    // glavna funkcjia katero klicemo nad celim tekstom ki v obratnem vrstnem redu
    // kot je bilo besedilo "azsifrirano" odsifrira kar pomeni, darazdlenimo povedi
    // nato vsako poved obrnemo nato vsko besedo

    public static String razvozlaj(String vrstica) {

        int stPovedi = (int) stPovedi(vrstica);

        // String[] povedi = razcleniVPovedi(vrstica, stPovedi);

        String[] povedi = razcleniVPovediOdZadi(vrstica, stPovedi);

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
        String[] odstavki = new String[90000000];
        int stOdstavkov = 0;
        // bomo nrdili resitev samo za en odstavek za vec odstavku bi fajn blo met
        // podatke kolko jih je kr cene nevemo in bi blo fajn met arraylist
        // mybe pa nerabimo kr lahko usak odstavk posebi popravimo in ga zapoisemo
        // nekam(nebo slo kr je vrsti red odstaku napacen)
        try {

            BufferedReader read = new BufferedReader(new FileReader(args[0]));
            PrintWriter p = new PrintWriter(new FileWriter(args[1]));
            while ((vrstica = read.readLine()) != null) {
                odstavki[stOdstavkov] = razvozlaj(vrstica);
                stOdstavkov++;

            }
            if (stOdstavkov % 2 == 0) {
                for (int i = 0; i < stOdstavkov; i++) {
                    if (i % 2 == 0) {
                        p.print(odstavki[stOdstavkov - 2 - i]);
                    } else {
                        p.print(odstavki[i]);
                    }
                    if (i != stOdstavkov - 1) {
                        p.println();
                    }
                }
            } else {
                for (int i = 0; i < stOdstavkov; i++) {
                    if (i % 2 == 0) {
                        p.print(odstavki[stOdstavkov - 1 - i]);
                    } else {
                        p.print(odstavki[i]);
                    }
                    if (i != stOdstavkov - 1) {
                        p.println();
                    }
                }

            }
            p.close();
            read.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
