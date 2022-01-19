import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Naloga10
 */
public class Naloga10 {

    // 3/4 je uselesss mislim da sem potreboval samo id x y ter spada, ki nam pove
    // pod katero mnozico se v danem trenutku nahaja tocka
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
        // samo da ima se id in par mnozic
        private int idMnozice;
        private HashSet<Tocka> tocke;

        public Mnozica(int id) {
            this.tocke = new HashSet<>();

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

    // navadn razred razdalja ga rabimno za vse razdalje
    public static class Razdalja {
        private double razdalja;
        private Tocka ena;
        private Tocka dva;

        public Razdalja(Tocka ena, Tocka dva, double razdalja) {
            this.razdalja = razdalja;
            this.ena = ena;
            this.dva = dva;
        }

        @Override
        public String toString() {
            return String.format(" [%f %d %d] ", this.razdalja, this.ena.idTocke, this.dva.idTocke);
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader tok = new BufferedReader(new FileReader(args[0]));
        PrintWriter p = new PrintWriter(new FileWriter(args[1]));

        int stTock = Integer.parseInt(tok.readLine());
        double[][] razdaljeMedTockami = new double[stTock][stTock];// imamo vse razdalje
        ArrayList<Razdalja> vseRazdalje = new ArrayList<>();// tudi tukaj le da tukaj jih kasneje sortiramo in vzamemo
                                                            // najbljizjo vedno
        ArrayList<Tocka> vse = new ArrayList<>(); // vse tocke,
        ArrayList<Mnozica> vseMnozice = new ArrayList<>();// vse mnozice

        // gremo skozi vhod vsakic dodamo novo tocko in za to tocko naredimo tudi
        // mnozico
        // potem pa gremo do tak kjer smo ze prebrali in zracunamo razdaljo jih vpisemo
        // za obe tocki its...
        for (int i = 0; i < stTock; i++) {
            vse.add(new Tocka(tok.readLine().split(","), i + 1));
            vseMnozice.add(new Mnozica(vse.get(i).idTocke));
            vseMnozice.get(i).dodajTocko(vse.get(i));
            for (int j = 0; j < i; j++) {
                double raz = vse.get(i).razdalja(vse.get(j));
                vseRazdalje.add(new Razdalja(vse.get(i), vse.get(j), raz));
                razdaljeMedTockami[i][j] = raz;
                razdaljeMedTockami[j][i] = raz;
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

        Collections.sort(vse, (o1, o2) -> Double.compare(o1.razdaljaDoNajblizje, o2.razdaljaDoNajblizje));// sploh ne
                                                                                                          // uporabimo
        Collections.sort(vseRazdalje, (o1, o2) -> Double.compare(o1.razdalja, o2.razdalja));

        // gremo skozi in vsakic uzamemo najblizjo razdaljo zbrisemo mnozici teh tock
        // katerih razdalja je najmnajsa naredimo skupno mnozico in jo vstavimo v
        // arraylist vseh mnozic
        // algo se zakkljuci
        while (vseMnozice.size() > stSkupin) {
            Razdalja temp = vseRazdalje.get(0);
            vseRazdalje.remove(0);
            Mnozica prva = temp.ena.spada;
            Mnozica druga = temp.dva.spada;
            vseMnozice.remove(prva);
            vseMnozice.remove(druga);
            Mnozica temoMno = new Mnozica(Math.min(prva.idMnozice, druga.idMnozice));
            temoMno.dodajMnozico(prva);
            temoMno.dodajMnozico(druga);
            vseMnozice.add(temoMno);

        }

        // samo sortiramo da so mnozice po vrsti in enako tudi za tocke in printamo
        Collections.sort(vseMnozice, (o1, o2) -> o1.idMnozice - o2.idMnozice);
        for (Mnozica mnozica : vseMnozice) {
            ArrayList<Tocka> lista = new ArrayList<Tocka>(mnozica.tocke);

            Collections.sort(lista, (o1, o2) -> o1.idTocke - o2.idTocke);
            boolean prvi = true;
            for (Tocka tt : lista) {
                if (prvi) {
                    p.print(tt.idTocke);
                    prvi = false;
                } else {
                    p.print("," + tt.idTocke);
                }
            }
            p.println();
        }

        p.close();

        System.out.println(rek(5, 1));
    }

    private static int rek(int n, int zmnozek) {
        if (n == 0) {
            return zmnozek;
        } else {
            return rek(n - 1, zmnozek * n);
        }
    }

    private static int biba(int n, int a) {

        while (n > 0) {
            a *= n;

            n--;
        }
        return a;
    }
}