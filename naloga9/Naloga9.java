import java.io.*;
import java.util.*;

/**
 * Naloga9
 */
public class Naloga9 {

    public static void dodajPovezavo(ArrayList<ArrayList<Integer>> povezave, int zacetna, int koncna) {
        // itak je u obe smeri zato zacetna konca ne mejka sensa sam ja.
        povezave.get(zacetna).add(koncna);
        povezave.get(koncna).add(zacetna);
    }

    private static boolean BFS(ArrayList<ArrayList<Integer>> povezave, int zac,
            int konc, int stNodes, int pred[], int razdalja[]) {
        // navaden queue za BFS algoritem
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // oznacimoi node, ki smo jih ze obiskali
        boolean smoze[] = new boolean[stNodes];

        // nastavimo, da nismo obiskali ze nobenga noda(ceprau je deefault ze to), pa da
        // je od enega noda do nasledngea distance neskoncxno in da nima predhodnika se.
        // to spreminjamo pole v loop v
        for (int i = 0; i < stNodes; i++) {
            smoze[i] = false;
            razdalja[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        // ker se lotimo na nodu z id zac oznacimo da smo tu ze bili oznacimio da je
        // razdalja o sm 0(ker je start) in ga sddodamoo v que in se odpravimo v zankico
        smoze[zac] = true;
        razdalja[zac] = 0;
        queue.add(zac);

        // BFS
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < povezave.get(u).size(); i++) {
                if (smoze[povezave.get(u).get(i)] == false) {
                    smoze[povezave.get(u).get(i)] = true;
                    // no comment za gor

                    // razdalja do tega vozlisca je enaka razdalji do vozlisca v katerem smo + 1,
                    // saj se tja premaknemo das ist ja logisch
                    razdalja[povezave.get(u).get(i)] = razdalja[u] + 1;
                    // zapisemo si, da v tem nodu ki ga dodamo v queue je biu prejsni node enak nodu
                    // v katerem smo sedaj. to nam pride prav, ko iz cilja lahko
                    // enolicno dolocimo pot iz katere smo prisli
                    pred[povezave.get(u).get(i)] = u;
                    queue.add(povezave.get(u).get(i));

                    // ce pa naletimo na konc odpremo sampanjec in smo lahko veseli
                    if (povezave.get(u).get(i) == konc)
                        return true;
                }
            }
        }
        System.out.println("CE SMO TU JE NEKI NAROBE");
        // nej nebi prsli sm cene je neki nrobe
        return false;
    }

    private static void dodajNajkrasiRazdalji(
            ArrayList<ArrayList<Integer>> povezave,
            int zac, int konc, int stnodes, int stpotnikov, int[][] carina) {

        // tabela, ki jo bomo uporabili v BFS in jo tam napoilnili nato pa potrebovali
        // tu
        // ime index->predhodnik na poti

        int pred[] = new int[stnodes];
        // razdalja
        int razdalja[] = new int[stnodes];
        if (zac >= stnodes || konc >= stnodes) {
            // somehow ima 4 nek sfaljen test in hoce da pridemo iz nekega noda k ne obstaja
            // meme ugl
            return;
        }
        BFS(povezave, zac, konc, stnodes, pred, razdalja);

        LinkedList<Integer> path = new LinkedList<Integer>();
        int temp = konc;
        path.add(temp);
        // sprehajamo se od odzadi (konc) preko predhodnikov do zacetka oz do noda, ki
        // ima predhodnika -1 kar pa je zacetek
        while (pred[temp] != -1) {
            path.add(pred[temp]);
            temp = pred[temp];
        }

        for (int i = 1; i < path.size(); i++) {
            Integer prva = path.get(i - 1);
            Integer druga = path.get(i);

            if (prva < druga) {
                carina[prva][druga] += stpotnikov;
            } else {
                carina[druga][prva] += stpotnikov;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader tok = new BufferedReader(new FileReader(args[0]));
        PrintWriter p = new PrintWriter(new FileWriter(args[1]));

        String[] stevila = tok.readLine().split(",");
        int stPovezav = Integer.parseInt(stevila[0]);
        int stFacts = Integer.parseInt(stevila[1]);
        String[][] vhodPovezave = new String[stPovezav][];

        int stNodes = 0;
        for (int i = 0; i < stPovezav; i++) {
            vhodPovezave[i] = tok.readLine().split(",");
            int dva = Integer.parseInt(vhodPovezave[i][1]);
            if (dva > stNodes) {
                stNodes = dva;
            }
        }
        stNodes++;
        ArrayList<ArrayList<Integer>> povezave = new ArrayList<ArrayList<Integer>>(stNodes);
        for (int i = 0; i < stNodes; i++) {
            povezave.add(new ArrayList<Integer>());
        }
        for (String[] a : vhodPovezave) {
            dodajPovezavo(povezave, Integer.parseInt(a[0]), Integer.parseInt(a[1]));
        }

        int[][] carina = new int[stNodes][stNodes];

        for (int i = 0; i < stFacts; i++) {
            stevila = tok.readLine().split(",");

            dodajNajkrasiRazdalji(povezave, Integer.parseInt(stevila[0]), Integer.parseInt(stevila[1]),
                    stNodes,
                    Integer.parseInt(stevila[2]), carina);
        }

        int max = 0;
        ArrayList<int[]> rezultati = new ArrayList<>();
        for (int i = 1; i < carina.length; i++) {
            for (int j = i; j < carina.length; j++) {
                if (carina[i][j] > max) {
                    rezultati = new ArrayList<>();
                    int[] jaja = new int[2];
                    jaja[0] = i;
                    jaja[1] = j;
                    rezultati.add(jaja);
                    max = carina[i][j];
                } else if (carina[i][j] == max) {
                    int[] jaja = new int[2];
                    jaja[0] = i;
                    jaja[1] = j;
                    rezultati.add(jaja);
                }
            }
        }
        for (int[] neki : rezultati) {
            p.printf("%d,%d\n", neki[0], neki[1]);
        }

        p.close();

    }
}