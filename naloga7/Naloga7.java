
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

    public static class Postaja {
        int idPostaje;
        HashSet<Integer> sosednjePostaje;
        HashSet<Integer> linijeNaPostaji;

        public Postaja(int id, HashSet<Integer> sosednjePostaje, HashSet<Integer> linijeNaPostaji) {
            this.idPostaje = id;
            this.sosednjePostaje = sosednjePostaje;
            this.linijeNaPostaji = linijeNaPostaji;
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

    public static class QueueEl2 {
        int idPostaje;
        int idLinije;
        int korak;

        public QueueEl2(int idlinije, int korak, int idpostaje) {
            this.idLinije = idlinije;
            this.korak = korak;
            this.idPostaje = idpostaje;
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
        Postaja[] postaje = new Postaja[25];// treba nekako popraviti alpa sam dat nek max

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
            System.out.println(tempPostaje.toString());

            Integer prev = null;
            for (Integer idPostaje : tempPostaje) {
                if (postaje[idPostaje] == null) {
                    postaje[idPostaje] = new Postaja(idPostaje, new HashSet<>(), new HashSet<>());
                    postaje[idPostaje].linijeNaPostaji.add(i);
                }
                if (prev != null) {

                    postaje[idPostaje].sosednjePostaje.add(prev);
                    postaje[prev].sosednjePostaje.add(idPostaje);
                    postaje[idPostaje].linijeNaPostaji.add(i);
                }

                prev = idPostaje;
            }

        }
        temp = tok.readLine().split(",");
        idZacetka = Integer.parseInt(temp[0]);
        idKonca = Integer.parseInt(temp[1]);

        Queue<QueueEl> q = new LinkedList<>();
        HashSet<Integer> smoZeObiskaliLinijo = new HashSet<>();

        for (Linija linija : linije) {
            linija.izpisi();
            // System.out.println();
            if (linija.postaje.contains(idZacetka)) {
                q.add(new QueueEl(linija.idLinije, 0));
            }
        }
        boolean najdenaPot = false;
        QueueEl tempQel = null;
        while (!q.isEmpty()) {
            tempQel = q.poll();
            System.out.println("LOOP1 " + tempQel.idLinije);
            if (linije[tempQel.idLinije].postaje.contains(idKonca)) {
                najdenaPot = true;
                break;
            }
            smoZeObiskaliLinijo.add(tempQel.idLinije);

            for (Integer idLinije : linije[tempQel.idLinije].sosedneLinije) {
                if (!smoZeObiskaliLinijo.contains(idLinije)) {
                    q.add(new QueueEl(idLinije, tempQel.korak + 1));
                }
            }

        }

        int najmanjseSteviloPrestopanj = tempQel.korak;
        if (!najdenaPot) {
            System.out.println();
            System.out.println();
            System.out.println(-1);
            System.out.println(-1);
            System.out.println(-1);
            p.println(-1);
            p.println(-1);
            p.println(-1);
            System.exit(0);
        }

        System.out.println("konca linija " + tempQel.idLinije);
        System.out.println("stPrestopanj: " + tempQel.korak);

        Queue<QueueEl2> q2 = new LinkedList<>();
        HashSet<Integer> smoZeObiskaliPostajo = new HashSet<>();
        HashSet<Integer> obiskaneLinije = new HashSet<>();
        // rabimo d gre samo dovolj mest
        for (Integer integer : postaje[idZacetka].linijeNaPostaji) {
            q2.add(new QueueEl2(integer, 0, idZacetka));

        }
        System.out.println(q2.toString());

        QueueEl2 tempQel2 = null;
        while (!q2.isEmpty()) {
            tempQel2 = q2.poll();
            System.out.println(tempQel2.idPostaje);

            if (tempQel2.idPostaje == idKonca) {
                System.out.println("     " + tempQel2.korak + "   " + obiskaneLinije.size());
                System.out.println(obiskaneLinije.toString());
                break;
            }
            smoZeObiskaliPostajo.add(tempQel2.idPostaje);
            obiskaneLinije.add(tempQel2.idLinije);
            // for (Integer idLinije : linije[tempQel.idLinije].sosedneLinije) {
            // if (!smoZeObiskaliLinijo.contains(idLinije)) {
            // q.add(new QueueEl(idLinije, tempQel.korak + 1));
            // }
            // }

            for (Integer idPostaje : postaje[tempQel2.idPostaje].sosednjePostaje) {
                if (!smoZeObiskaliPostajo.contains(idPostaje)) {
                    for (Integer idlinije : postaje[idPostaje].linijeNaPostaji) {
                        q2.add(new QueueEl2(idlinije, tempQel2.korak + 1, idPostaje));
                    }
                }
            }

        }
        int najkraskaPot = tempQel2.korak;
        int najkraskaPotStevilolinij = obiskaneLinije.size();

        System.out.println();
        System.out.println();

        System.out.println(najmanjseSteviloPrestopanj);
        System.out.println(najkraskaPot);
        System.out.println(najkraskaPotStevilolinij == najmanjseSteviloPrestopanj ? 1 : 0);
        p.println(najmanjseSteviloPrestopanj);
        p.println(najkraskaPot);
        p.println(najkraskaPotStevilolinij == najmanjseSteviloPrestopanj ? 1 : 0);
        p.close();
        tok.close();

    }

}