import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Naloga10
 */
public class Naloga10 {

    public static class Tocka {
        private int idTocke;
        private double x;
        private double y;
        private Tocka najblizja;
        private double razdaljaDoNajblizje;
        private Mnozica spada;
        private int tuki;

        public Tocka(String[] a, int id) {
            this.x = Double.parseDouble(a[0]);
            this.y = Double.parseDouble(a[1]);
            this.idTocke = id;
            this.najblizja = null;
            this.razdaljaDoNajblizje = Double.MAX_VALUE;
            this.spada = null;
            this.tuki = id;
        }

        public void izpisi() {
            System.out.printf("Tocka %d: %f %f raz: %f naj: %d spada:%d tuki:%d\n", this.idTocke, this.x, this.y,
                    this.razdaljaDoNajblizje, this.najblizja.idTocke, this.spada.idMnozice, this.tuki);
        }

        public double razdalja(Tocka dva) {
            return Math.sqrt(Math.pow((this.x - dva.x), 2) + Math.pow((this.y - dva.y), 2));
        }

        public void zamenjajMnozico(Mnozica a) {
            this.spada = a;
        }

    }

    public static class Mnozica {
        private int idMnozice;
        private HashSet<Tocka> tocke;
        // private HashMap<Mnozica, Double> povezaveInRazdalje;

        public Mnozica(int id) {
            this.tocke = new HashSet<>();
            // this.povezaveInRazdalje = new HashMap<>();
            this.idMnozice = id;
        }

        public void dodajTocko(Tocka a) {
            this.tocke.add(a);
            a.zamenjajMnozico(this);
            if (a.idTocke < this.idMnozice) {
                idMnozice = a.idTocke;
            }
        }

        public void dodajMnozico(Mnozica a) {
            for (Tocka tocka : a.tocke) {
                this.dodajTocko(tocka);

            }
        }

        public void izpisiMnozico() {

            System.out.println("MNOZICA: " + this.idMnozice);
            for (Tocka tocka : tocke) {
                tocka.izpisi();
            }

            System.out.println();

        }

    }

    public static void main(String[] args) throws IOException {

        BufferedReader tok = new BufferedReader(new FileReader(args[0]));
        PrintWriter p = new PrintWriter(new FileWriter(args[1]));

        int stTock = Integer.parseInt(tok.readLine());
        double[][] razdaljeMedTockami = new double[stTock][stTock];

        Tocka[] vseTocke = new Tocka[stTock];
        ArrayList<Tocka> vse = new ArrayList<>();
        ArrayList<Mnozica> vseMnozice = new ArrayList<>();
        for (int i = 0; i < stTock; i++) {
            vse.add(new Tocka(tok.readLine().split(","), i + 1));
            vseMnozice.add(new Mnozica(vse.get(i).idTocke));
            vseMnozice.get(i).dodajTocko(vse.get(i));
            for (int j = 0; j < i; j++) {
                double raz = vse.get(i).razdalja(vse.get(j));

                if (raz < vse.get(i).razdaljaDoNajblizje) {
                    vse.get(i).razdaljaDoNajblizje = raz;
                    vse.get(i).najblizja = vse.get(j);
                }
                if (raz < vse.get(j).razdaljaDoNajblizje) {
                    vse.get(j).razdaljaDoNajblizje = raz;
                    vse.get(j).najblizja = vse.get(i);
                }

            }

        }
        int stSkupin = Integer.parseInt(tok.readLine());
        tok.close();

        Collections.sort(vse, (o1, o2) -> Double.compare(o1.razdaljaDoNajblizje, o2.razdaljaDoNajblizje));

        while (stSkupin >= ) {
            
        }

        // for (int i = 0; i < vse.size() - stSkupin + 1; i++) {
        //     Tocka tc = vse.get(i);
        //     Mnozica a = tc.najblizja.spada;
        //     Mnozica b = tc.spada;

        //     tc.tuki = tc.najblizja.tuki;

        //     // if (!a.equals(b)) {
        //     // System.out.println("DODAMO");
        //     // vse.get(i).spada.izpisiMnozico();
        //     // System.out.println("+");

        //     // vse.get(i).najblizja.spada.izpisiMnozico();

        //     // vse.get(i).spada.dodajMnozico(vse.get(i).najblizja.spada);
        //     // System.out.println("=");
        //     // vse.get(i).spada.izpisiMnozico();

        //     // System.out.println("ODSTRANIMO");
        //     // a.izpisiMnozico();
        //     // vseMnozice.remove(a);

        //     // System.out.println();
        //     // System.out.println();
        //     // System.out.println();
        //     // }

        //     if (vseMnozice.size() == stSkupin) {
        //         break;
        //     }
        // }

        System.out.println();
        System.err.println(vse.size());
        // System.out.println(vseMnozice.size());
        for (Tocka jasnTocka : vse) {
            jasnTocka.izpisi();
        }
        System.out.println();

        System.out.println(vseMnozice.size());
        for (Mnozica mnozica : vseMnozice) {
            mnozica.izpisiMnozico();
        }

    }
}