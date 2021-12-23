import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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

        public Tocka(String[] a, int id) {
            this.x = Double.parseDouble(a[0]);
            this.y = Double.parseDouble(a[1]);
            this.idTocke = id;
        }

        public void izpisi() {
            System.out.printf("Tocka %d: %f %f\n", this.idTocke, this.x, this.y);
        }

        public double razdalja(Tocka dva) {
            return Math.sqrt(Math.pow((this.x - dva.x), 2) + Math.pow((this.x - dva.x), 2));
        }

    }

    public static class Mnozica {
        private int idMnozice;
        private ArrayList<Tocka> tocke;
        private HashMap<Mnozica, Double> povezaveInRazdalje;

        public Mnozica(int id) {
            this.tocke = new ArrayList<>();
            this.povezaveInRazdalje = new HashMap<>();
            this.idMnozice = id;
        }

        public void dodajTocko(Tocka a) {
            this.tocke.add(a);
        }

    }

    public static void main(String[] args) throws IOException {

        BufferedReader tok = new BufferedReader(new FileReader(args[0]));
        PrintWriter p = new PrintWriter(new FileWriter(args[1]));

        int stTock = Integer.parseInt(tok.readLine());

        Tocka[] vseTocke = new Tocka[stTock];
        ArrayList<Mnozica> vseMnozice = new ArrayList<>();
        for (int i = 0; i < stTock; i++) {
            vseTocke[i] = new Tocka(tok.readLine().split(","), i + 1);
            vseMnozice.add(new Mnozica(i + 1));
            vseMnozice.get(i).dodajTocko(vseTocke[i]);

            for (int j = 0; j < i; j++) {
                double razdalja = vseTocke[i].razdalja(vseTocke[j]);
                vseMnozice.get(i).povezaveInRazdalje.put(vseMnozice.get(j), razdalja);
                vseMnozice.get(j).povezaveInRazdalje.put(vseMnozice.get(j), razdalja);
            }

        }
        int stSkupin = Integer.parseInt(tok.readLine());
        tok.close();

        for (Tocka tocka : vseTocke) {
            tocka.izpisi();
        }
    }
}