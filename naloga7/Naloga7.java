
/**
 * Naloga7
 */
import java.io.*;
import java.time.temporal.TemporalQueries;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.event.SwingPropertyChangeSupport;

public class Naloga7 {

    public static class Linija {
        int idLinije;
        HashSet<Integer> sosedneLinije;
        ArrayList<Integer> postaje;

        public Linija(int idlinije, HashSet<Integer> sosedi, ArrayList<Integer> postaje) {
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
        int prestopanja;

        public QueueEl2(int idlinije, int korak, int idpostaje, int prestopanja) {
            this.idLinije = idlinije;
            this.korak = korak;
            this.idPostaje = idpostaje;
            this.prestopanja = prestopanja;
        }
    }
    // imamo dva nacina imamo en graf ki ima vse postaje s tem iscemo najjkrajso pot
    // imamo gra fki ima samo postaje s tem iscemo pot znajmanj prestopanji ravno
    // taki, da iscemo najkraso pot le da se tokrat sprehajamo samo med linijami
    // rabili bomo dve drevesi eno je dreve

    public void najdiPotRek() {

    }

    public static void main(String[] args) throws IOException {
        BufferedReader tok = new BufferedReader(new FileReader(args[0]));
        PrintWriter p = new PrintWriter(new FileWriter(args[1]));

        int stLinij = Integer.parseInt(tok.readLine());

        int idZacetka, idKonca;

        Linija[] linije = new Linija[stLinij];
        Postaja[] postaje = new Postaja[50000];// treba nekako popraviti alpa sam dat nek max
        ArrayList[] vseLinijelist = new ArrayList[stLinij];
        String[] temp;
        int steviloPostaj = 0;

        for (int i = 0; i < stLinij; i++) {

            temp = tok.readLine().split(",");
            ArrayList<Integer> tempPostaje = new ArrayList<>();
            HashSet<Integer> tempSosedi = new HashSet<>();

            for (String postaja : temp) {
                tempPostaje.add(Integer.parseInt(postaja));
            }
            vseLinijelist[i] = tempPostaje;
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

            Integer prev = null;
            for (Integer idPostaje : tempPostaje) {
                if (postaje[idPostaje] == null) {
                    postaje[idPostaje] = new Postaja(idPostaje, new HashSet<>(), new HashSet<>());
                    postaje[idPostaje].linijeNaPostaji.add(i);

                }
                postaje[idPostaje].linijeNaPostaji.add(i);

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

        // for (Postaja postaja : postaje) {
        // if (postaja != null) {
        // System.out.println(postaja.idPostaje);
        // System.out.println(postaja.sosednjePostaje.toString());
        // System.out.println(postaja.linijeNaPostaji.toString());
        // System.out.println();
        // }
        // }

        Queue<QueueEl> q = new LinkedList<>();
        HashSet<Integer> smoZeObiskaliLinijo = new HashSet<>();

        for (Linija linija : linije) {

            if (linija.postaje.contains(idZacetka)) {
                q.add(new QueueEl(linija.idLinije, 0));
            }
        }
        boolean najdenaPot = false;
        QueueEl tempQel = null;
        while (!q.isEmpty()) {
            tempQel = q.poll();

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

        Queue<QueueEl2> q2 = new LinkedList<>();
        HashSet<Integer> smoZeObiskaliPostajo = new HashSet<>();
        HashSet<Integer> smoDaliVseVerzije = new HashSet<>();
        HashSet<Integer> obiskaneLinije = new HashSet<>();
        // rabimo d gre samo dovolj mest
        for (Integer integer : postaje[idZacetka].linijeNaPostaji) {
            q2.add(new QueueEl2(integer, 0, idZacetka, 0));
        }
        smoDaliVseVerzije.add(idZacetka);

        QueueEl2 najpot = null;
        QueueEl2 tempQel2 = null;
        while (!q2.isEmpty()) {
            tempQel2 = q2.poll();

            // System.out.printf("id:%d, lin:%d, kor:%d, prestop:%d\n", tempQel2.idPostaje,
            // tempQel2.idLinije,
            // tempQel2.korak,
            // tempQel2.prestopanja);
            if (tempQel2.idPostaje == idKonca) {
                System.out.printf("id:%d, lin:%d, kor:%d, prestop:%d\n", tempQel2.idPostaje, tempQel2.idLinije,
                        tempQel2.korak,
                        tempQel2.prestopanja);
                if (najpot == null) {
                    najpot = tempQel2;
                } else {
                    // if (tempQel2.korak > najpot.korak) {
                    // break;
                    // }
                    if (tempQel2.korak < najpot.korak) {
                        najpot = tempQel2;
                    }

                    if (tempQel2.prestopanja < najpot.prestopanja) {
                        najpot = tempQel2;
                    }
                }
            }
            smoZeObiskaliPostajo.add(tempQel2.idPostaje);

            // for (Integer idSosedaInteger : postaje[tempQel2.idPostaje].sosednjePostaje) {
            // if (!smoZeObiskaliPostajo.contains(idSosedaInteger)) {
            // if (postaje[idSosedaInteger].linijeNaPostaji.contains(tempQel2.idLinije)) {
            // q2.add(new QueueEl2(tempQel2.idLinije, tempQel2.korak + 1, idSosedaInteger,
            // tempQel2.prestopanja));
            // // System.out.println("dodajamo1: ");
            // if (idSosedaInteger == 17) {
            // System.out.println("TA JE: ");
            // System.out.printf("id:%d, lin:%d, kor:%d, prestop:%d\n", tempQel2.idPostaje,
            // tempQel2.idLinije,
            // tempQel2.korak,
            // tempQel2.prestopanja);
            // System.out.println();

            // }
            // }
            // }
            // }
            ArrayList tempPost = vseLinijelist[tempQel2.idLinije];
            int pozTemp = tempPost.indexOf(tempQel2.idPostaje);

            if (pozTemp == 0) {
                if (tempPost.size() >= 2 && !smoZeObiskaliPostajo.contains(tempPost.get(pozTemp + 1))) {
                    q2.add(new QueueEl2(tempQel2.idLinije, tempQel2.korak + 1, (Integer) tempPost.get(pozTemp + 1),
                            tempQel2.prestopanja));
                }

            } else if (pozTemp == tempPost.size() - 1) {
                if (!smoZeObiskaliPostajo.contains(tempPost.get(pozTemp - 1))) {
                    q2.add(new QueueEl2(tempQel2.idLinije, tempQel2.korak + 1, (Integer) tempPost.get(pozTemp - 1),
                            tempQel2.prestopanja));
                }
            } else {
                if (!smoZeObiskaliPostajo.contains(tempPost.get(pozTemp + 1))) {
                    q2.add(new QueueEl2(tempQel2.idLinije, tempQel2.korak + 1, (Integer) tempPost.get(pozTemp + 1),
                            tempQel2.prestopanja));
                }
                if (!smoZeObiskaliPostajo.contains(tempPost.get(pozTemp - 1))) {
                    q2.add(new QueueEl2(tempQel2.idLinije, tempQel2.korak + 1, (Integer) tempPost.get(pozTemp - 1),
                            tempQel2.prestopanja));
                }
            }

            if (!smoDaliVseVerzije.contains(tempQel2.idPostaje)) {
                for (Integer linijaTempa : postaje[tempQel2.idPostaje].linijeNaPostaji) {
                    // System.out.printf("dodajamo1: %d postajo, na liniji %d \n",
                    // tempQel2.idPostaje, linijaTempa);
                    // System.out.println(postaje[tempQel2.idPostaje].linijeNaPostaji.toString());

                    if (linijaTempa != tempQel2.idLinije) {
                        q2.add(new QueueEl2(linijaTempa, tempQel2.korak, tempQel2.idPostaje,
                                tempQel2.prestopanja + 1));
                        // System.out.println("dodajamo1: ");

                    }
                }
            }
            smoDaliVseVerzije.add(tempQel2.idPostaje);

        }
        int najkraskaPot = najpot.korak;

        int jabadabaduba = najpot.prestopanja;

        System.out.println(najmanjseSteviloPrestopanj);
        System.out.println(najkraskaPot);

        System.out.println(
                jabadabaduba == najmanjseSteviloPrestopanj ? 1
                        : 0);

        p.println(najmanjseSteviloPrestopanj);
        p.println(najkraskaPot);
        p.println(jabadabaduba == najmanjseSteviloPrestopanj ? 1 : 0);

        p.close();
        tok.close();

    }

}