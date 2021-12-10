
/**
 * Naloga7
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

public class Naloga7 {
    public static class NodePostaja {
        int idPosaje;
        int idLinije;
        ArrayList<NodePostaja> sosedje;
        ArrayList<Integer> idSosedov;

        public NodePostaja(int idPostaje, int idLinije) {
            this.idPosaje = idPostaje;
            this.idLinije = idLinije;
            this.sosedje = new ArrayList<>();
            this.idSosedov = new ArrayList<>();
        }

    }

    public static class Tuple {
        int idPostaje;
        int stKorakov;
        int idLinije;

        public Tuple(int id, int st, int linija) {
            this.idPostaje = id;
            this.stKorakov = st;
            this.idLinije = linija;
        }

    }

    public static class NodeLinija {
        int idLinije;
        ArrayList<NodeLinija> sosedje;
        ArrayList<Integer> idSosedov;
        ArrayList<Integer> idPostaj;

        public NodeLinija(int idPostaje, int idLinije) {

            this.idLinije = idLinije;
            this.idPostaj = new ArrayList<>();
            this.sosedje = new ArrayList<>();
            this.idSosedov = new ArrayList<>();
        }

    }

    // imamo dva nacina imamo en graf ki ima vse postaje s tem iscemo najjkrajso pot
    // imamo gra fki ima samo postaje s tem iscemo pot znajmanj prestopanji ravno
    // taki, da iscemo najkraso pot le da se tokrat sprehajamo samo med linijami
    // rabili bomo dve drevesi eno je dreve

    public static void main(String[] args) throws IOException {
        BufferedReader tok = new BufferedReader(new FileReader(args[0]));
        PrintWriter p = new PrintWriter(new FileWriter(args[1]));

        int stLinij = Integer.parseInt(tok.readLine());

        int idZacetka, idKonca;

        String[][] linije = new String[stLinij][];
        NodePostaja prva;
        NodePostaja zacetna;
        String[] temp;
        // boolean[][] povezavePostajalisc = new boolean[][];
        int steviloPostaj = 0;
        for (int i = 0; i < stLinij; i++) {

            temp = tok.readLine().split(",");

            for (String a : temp) {
                if (Integer.parseInt(a) > steviloPostaj) {
                    steviloPostaj = Integer.parseInt(a);
                }
            }

            linije[i] = temp;

        }
        temp = tok.readLine().split(",");
        idZacetka = Integer.parseInt(temp[0]);
        idKonca = Integer.parseInt(temp[1]);

        // System.out.println(Arrays.deepToString(linije));
        // System.out.println(steviloPostaj);

        boolean[][] seznamPovezavPostaj = new boolean[steviloPostaj][steviloPostaj];
        // System.out.println(Arrays.deepToString(seznamPovezavPostaj));
        int stlinije = 0;
        Tuple[] vsePostaje = new Tuple[steviloPostaj];
        for (String[] linija : linije) {

            for (int i = 0; i < linija.length; i++) {

                if (vsePostaje[Integer.parseInt(linija[i]) - 1] == null) {
                    vsePostaje[Integer.parseInt(linija[i]) - 1] = new Tuple(Integer.parseInt(linija[i]), 0, stlinije);
                } else {

                }
                if (i == 0) {
                    continue;
                }
                seznamPovezavPostaj[Integer.parseInt(linija[i]) - 1][Integer.parseInt(linija[i - 1]) - 1] = true;
                seznamPovezavPostaj[Integer.parseInt(linija[i - 1]) - 1][Integer.parseInt(linija[i]) - 1] = true;
            }
            stlinije++;

        }
        // System.out.println(Arrays.deepToString(seznamPovezavPostaj));
        // System.out.println(Arrays.deepToString(vsePostaje));
        for (Tuple tuple : vsePostaje) {
            System.out.print(tuple.idPostaje);
            System.out.print(" ");
            System.out.println(tuple.idLinije);
        }

        // addi v q zacetno pozicijo in korak ima 0
        // while q ni empty gres
        // vsakic popnemo prvi element damo njegove sosede v vrsto in jim damo korak+1.
        // potem
        // ko pridemo do zeljenega vozlisca se sutavimo in vsakic ko pridemo do novega
        // si zapisemo linijio, da drzimo stevilo linij
        LinkedList<Tuple> vrstica = new LinkedList<>();
        int dolzinaPoti = 0;
        vrstica.add(vsePostaje[idZacetka - 1]);
        LinkedList<Integer> smoZe = new LinkedList<>();
        HashSet<Integer> obiskaneLinije = new HashSet<>();
        Tuple tempTuple = null;
        while (!vrstica.isEmpty()) {
            tempTuple = vrstica.removeFirst();
            System.out.printf("      %d %d \n", tempTuple.idPostaje, tempTuple.stKorakov);

            if (tempTuple.idPostaje == idKonca) {
                break;
            }
            smoZe.add(tempTuple.idPostaje);
            obiskaneLinije.add(tempTuple.idLinije);
            for (int i = 0; i < steviloPostaj; i++) {
                if (seznamPovezavPostaj[tempTuple.idPostaje - 1][i] && !(smoZe.contains(i + 1))) {
                    vrstica.add(new Tuple(i + 1, tempTuple.stKorakov + 1, vsePostaje[i].idLinije));
                }
            }
        }
        System.out.println();
        System.out.println();
        System.out.println(tempTuple.idLinije);
        System.out.println(tempTuple.idPostaje);
        System.out.println(tempTuple.stKorakov);

        p.close();
        tok.close();

    }

}