import java.io.*;

public class Naloga3 {

    /**
     * clenSeznama
     */
    private class clenSeznama {

        private Integer[] arr;
        private clenSeznama naslednji;
        private int dolzinaClena;
        private int prviProstIndex;
        private boolean imamoProstor;
        // clen ma take uporabne elemente

        public int getPrviProstiIndex() {
            return this.prviProstIndex;
        }

        // nevem keje bla neka fora d ni hotlo dejansko mi setta in pole sm napisu
        // funkcijo d mi nrdi samo
        // al je fora da ta funkcjia mi automatsko tudi updejta ce mamo prostor k cene
        // bi mogu usakig hardcode prevert
        public int setPrviProstiIndex(int a) {
            this.prviProstIndex = this.prviProstIndex + a;

            if (prviProstIndex < dolzinaClena) {
                this.imamoProstor = true;
            } else {
                this.imamoProstor = false;
            }
            return a;
        }

        public clenSeznama vrniNovClen() {

            return new clenSeznama(this.dolzinaClena);
        }

        public clenSeznama(int n) {
            this.arr = new Integer[n];
            this.dolzinaClena = n;
            this.prviProstIndex = 0;
            this.naslednji = null;
            this.imamoProstor = true;
        }

        public void popraviIndex() {
            int i = 0;
            while (this.arr[i] != null) {
                i++;
            }
            this.prviProstIndex = i;

        }

    }

    private class Seznam {

        private int stClenov;
        private clenSeznama prvi;
        private clenSeznama zadnji;
        private int dolzinaClena;

        public Seznam(int a) {
            this.stClenov = 0;
            this.prvi = null;
            this.zadnji = null;
            this.dolzinaClena = a;
        }

        public int dolzina() {
            int i = 0;
            if (this.prvi == null) {
                return 0;
            }
            clenSeznama temp = this.prvi;

            while (temp != null) {
                temp = temp.naslednji;
                i++;
            }

            return i;
        }

        public void init(int n) {
            clenSeznama novi = new clenSeznama(n);
            this.prvi = novi;
            this.zadnji = novi;
        }

        // metoda, ki se m jo pred popravo naloge se rabu i guess je zdj tle sam za
        // spomni d sm nrdu narobe enkrt
        public void umakniPrazne() {

            clenSeznama temp = this.prvi;

            while (temp != null) {
                boolean vseJeNull = true;
                if (temp.naslednji != null) {
                    clenSeznama next = temp.naslednji;
                    for (int i = 0; i < next.arr.length; i++) {
                        if (next.arr[i] != null) {
                            vseJeNull = false;
                            break;
                        }
                    }

                    if (vseJeNull) {
                        temp.naslednji = next.naslednji;
                        next.naslednji = null;
                    }
                }

                temp = temp.naslednji;
            }

        }

        // vstavi clen notr heh simple

        // ideja je s mi najprej se sprehodimo skozid clene ter pogledamo na kateremu
        // clenu sploh moramo vstaciti to kar ustavimo
        // ko pridemo do tega clena mamo neki moznosti zdj ce nemormo ustavit vrni false
        // ok
        // ce nimamo prostora in je pozicija enaka koncnemu indexu fora da ustavmio
        // nakonci pole ubistvi ustauljamo v nasledji clen na prvo pozicijo
        // nevem zkj nism tega pohendlu gori k se preimikamo sam pac dela tko i guess
        // nerabim pole poravat ugl samo premaknemo naprej in updejtamo pozicijo

        // ce je ta clen polno zaseden potem naredimo nov clen seznama naslednji je noci
        // novi nasledji we pac samo povezemo pointerje
        // in pole ga do polovicke celostevilksega delhenja skopriamo naprej ofkors kar
        // premaknemo nadomestimo z null ter prosti index spreminjamo
        // potem pa kr rekurzivno klicemo funkcijo s prvotno pozicijo kr why not itak bo
        // hendalo zdj k mamo plac
        // ce lohko usatavimo samo shiftamo use za ena ker fix nebo overflowwalo in
        // potem dodamo na pravo mesto simple af
        public boolean insert(int vrednost, int pozicija) {
            clenSeznama temp = this.prvi;
            int prvotnaPozicija = pozicija;
            boolean lahkoVstavimo = false;
            while (temp != null) {

                if (pozicija > temp.prviProstIndex) {
                    pozicija -= temp.prviProstIndex;
                } else {
                    lahkoVstavimo = true;
                    break;
                }
                temp = temp.naslednji;
            }

            if (!lahkoVstavimo) {
                return false;
            }

            if (!temp.imamoProstor && pozicija == temp.prviProstIndex) {
                temp = temp.naslednji;
                pozicija = 0;
            }

            if (!temp.imamoProstor) {

                clenSeznama naslednji = temp.naslednji;
                clenSeznama noviObject = temp.vrniNovClen();

                temp.naslednji = noviObject;
                noviObject.naslednji = naslednji;
                int j = 0;
                for (int i = temp.dolzinaClena / 2; i < temp.dolzinaClena; i++) {
                    noviObject.arr[j] = temp.arr[i];
                    temp.arr[i] = null;
                    temp.setPrviProstiIndex(-1);
                    noviObject.setPrviProstiIndex(1);
                    j++;
                }

                return this.insert(vrednost, prvotnaPozicija);
            } else {
                for (int i = temp.arr.length - 1; i > pozicija; i--) {
                    temp.arr[i] = temp.arr[i - 1];
                }

                temp.arr[pozicija] = vrednost;

                temp.setPrviProstiIndex(1);
                return true;
                // mamo prostor delamo samo z enim arrayom shiftamo in prekanme in dodsamo
            }

        }

        // pri remove ista pasta ni se premaknemo do pravega clena
        // najprej sam use shiftamo pole zadnjio damo na null ce ni bla in to je to
        // ko smo tm pogledamo situacijo ce bo po brisnju ratalo premajhno pole moramo
        // pac use skopirat
        // ce je stecilo res premajhno in nasledji clen nima nulll pole moramo prenesti
        // clene iz naslednjega v sedanjai ter naslednjega enostavno izbrisati s
        // kazalcki ampka to se zgodi le ce se lohko prenesejo usi elemtni
        // 
        //cene pa rpenesemo ssamo tolk stevilk da pac ima ta clen kjer smo odstranli n/2 notr d se ne sesede
        public boolean remove(int pozicija) {
            // this.umakniPrazne();
            clenSeznama temp = this.prvi;
            int prvotnaPozicija = pozicija;
            boolean lahkoBrisemo = false;
            while (temp != null) {

                if (pozicija >= temp.prviProstIndex) {
                    pozicija -= temp.prviProstIndex;
                } else {
                    lahkoBrisemo = true;
                    break;
                }
                temp = temp.naslednji;
            }

            if (!lahkoBrisemo) {
                return false;
            }

            for (int i = pozicija; i < temp.arr.length - 1; i++) {
                temp.arr[i] = temp.arr[i + 1];
            }
            temp.arr[temp.arr.length - 1] = null;
            temp.setPrviProstiIndex(-1);

            if (temp.prviProstIndex < temp.dolzinaClena / 2) {

                if (temp.naslednji != null && (temp.naslednji.prviProstIndex - 1) < temp.dolzinaClena / 2) {
                    // System.out.print("hi");

                    int j = 0;
                    for (int i = temp.prviProstIndex; i < temp.dolzinaClena; i++) {
                        temp.arr[i] = temp.naslednji.arr[j];
                        j++;
                    }

                    temp.popraviIndex();

                    temp.naslednji = temp.naslednji.naslednji;
                    return true;
                } else if (temp.naslednji != null) {
                    System.out.print("HEJLA");
                    temp.arr[temp.prviProstIndex] = temp.naslednji.arr[0];
                    temp.setPrviProstiIndex(1);
                    clenSeznama temp2 = temp;
                    temp = temp.naslednji;

                    for (int i = 0; i < temp.arr.length - 1; i++) {
                        temp.arr[i] = temp.arr[i + 1];
                    }
                    temp.arr[temp.arr.length - 1] = null;
                    temp.setPrviProstiIndex(-1);

                }
            }
            return true;
        }

        //izpise seznam v datoteko na katero kaze p 
        public void izpisiSeznam(PrintWriter p) {
            clenSeznama temp = this.prvi;
            p.println(this.dolzina());
            while (temp != null) {

                for (int i = 0; i < this.dolzinaClena; i++) {
                    if (temp.arr[i] == null) {
                        p.print("NULL");
                    } else {
                        p.print(temp.arr[i]);
                    }
                    if (i != this.dolzinaClena - 1) {
                        p.print(",");
                    }
                }
                // p.print(" " + temp.prviProstIndex);
                p.println();
                temp = temp.naslednji;
            }
            p.close();
        }

        //izpoise na konzolo ofkors samo za debuging
        public void izpisiSeznamKonzola() {

            clenSeznama temp = this.prvi;
            while (temp != null) {

                for (int i = 0; i < this.dolzinaClena; i++) {
                    if (temp.arr[i] == null) {
                        System.out.print("NULL");
                    } else {
                        System.out.print(temp.arr[i]);

                    }
                    if (i != this.dolzinaClena - 1) {
                        System.out.print(",");
                    }
                }
                System.out.print("...." + temp.prviProstIndex);
                System.out.println();
                temp = temp.naslednji;
            }
            System.out.println();

        }
    }

    public static void main(String[] args) {
        Naloga3 zunajiNaloga3 = new Naloga3();
        String vrstica;
        int stUkazov; // nerabomo ker beremo do konca
        char ukaz;
        String[] argumenti;
        int arg1;
        int arg2;
        Seznam sez;
        try {
            BufferedReader read = new BufferedReader(new FileReader(args[0]));
            PrintWriter p = new PrintWriter(new FileWriter(args[1]));
            stUkazov = Integer.parseInt(read.readLine());

            vrstica = read.readLine();

            argumenti = vrstica.split(",");
            arg1 = Integer.parseInt(argumenti[1]);
            sez = zunajiNaloga3.new Seznam(arg1);
            sez.init(arg1);
            while ((vrstica = read.readLine()) != null) {

                ukaz = vrstica.charAt(0);
                //switch ker za usak ukaz je druga crka in usak ukaz ma mal drgac te cifre k jim mormo parsat 
                switch (ukaz) {
                    case 'i':
                        argumenti = vrstica.split(",");

                        arg1 = Integer.parseInt(argumenti[1]);
                        arg2 = Integer.parseInt(argumenti[2]);
                        sez.insert(arg1, arg2);

                        break;
                    case 'r':

                        argumenti = vrstica.split(",");
                        arg1 = Integer.parseInt(argumenti[1]);
                        sez.remove(arg1);

                        break;
                    case 's':
                        sez = zunajiNaloga3.new Seznam(5);
                        argumenti = vrstica.split(",");
                        arg1 = Integer.parseInt(argumenti[1]);
                        sez.init(arg1);

                        break;
                    default:
                        break;
                }

            }
            sez.izpisiSeznam(p);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}