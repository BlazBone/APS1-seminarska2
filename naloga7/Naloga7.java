
/**
 * Naloga7
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Naloga7 {

    public static class Linija {
        int idLinije;
        HashSet<Integer> sosedneLinije;
        HashSet<Integer> postaje;

        public Linija(int idlinije, HashSet<Integer> sosedi, HashSet<Integer> postaje) {
            this.idLinije = idlinije;
            this.sosedneLinije = sosedi;
            this.postaje = postaje;
        }

        public void izpisi() {
            System.out.printf("Linija: %d\n sosednjeLinije: %s\n postaje: %s\n", this.idLinije,
                    this.sosedneLinije.toString(), this.postaje.toString());
        }
    }

    public static class QueueEl {
        int idLinije;
        int korak;

        public QueueEl(int id, int korak) {
            this.idLinije = id;
            this.korak = korak;
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

        Linija[] linije = new Linija[stLinij];

        String[] temp;
        int steviloPostaj = 0;

        for (int i = 0; i < stLinij; i++) {

            temp = tok.readLine().split(",");
            HashSet<Integer> tempPostaje = new HashSet<>();
            HashSet<Integer> tempSosedi = new HashSet<>();

            for (String postaja : temp) {
                tempPostaje.add(Integer.parseInt(postaja));
            }
            for (int j = 0; j < i; j++) {
                for (Integer postajaInteger : tempPostaje) {
                    if (linije[j].postaje.contains(postajaInteger)) {
                        tempSosedi.add(j);
                        linije[j].sosedneLinije.add(i);
                        break;
                    }
                }
            }

            linije[i] = new Linija(i, tempSosedi, tempPostaje);

        }
        temp = tok.readLine().split(",");
        idZacetka = Integer.parseInt(temp[0]);
        idKonca = Integer.parseInt(temp[1]);

        Queue<QueueEl> q = new LinkedList<>();
        HashSet<Integer> smoZeObiskaliLinijo = new HashSet<>();

        for (Linija linija : linije) {
            // linija.izpisi();
            // System.out.println();
            if (linija.postaje.contains(idZacetka)) {
                q.add(new QueueEl(linija.idLinije, 0));
            }
        }
        QueueEl tempQel = null;
        while (!q.isEmpty()) {
            tempQel = q.poll();

            if (linije[tempQel.idLinije].postaje.contains(idKonca)) {
                break;
            }
            smoZeObiskaliLinijo.add(tempQel.idLinije);

            for (Integer idLinije : linije[tempQel.idLinije].sosedneLinije) {
                if (!smoZeObiskaliLinijo.contains(idLinije)) {
                    q.add(new QueueEl(idLinije, tempQel.korak + 1));
                }
            }

        }
        System.out.println("konca linija " + tempQel.idLinije);
        System.out.println("stPrestopanj: " + tempQel.korak);

        p.close();
        tok.close();

    }

}