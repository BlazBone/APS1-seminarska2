import java.io.*;
import java.lang.ref.Cleaner.Cleanable;
import java.lang.reflect.Array;
import java.util.Arrays;

import javax.swing.plaf.synth.SynthSplitPaneUI;
import javax.xml.transform.Templates;

public class Naloga3 {

    /**
     * clenSeznama
     */
    private class clenSeznama {

        private Integer[] arr;
        private clenSeznama naslednji;
        private clenSeznama prejsni;
        private int dolzinaClena;
        private boolean cezPolovico;
        private int prviProstIndex;
        private boolean imamoProstor;

        public int getPrviProstiIndex() {
            return this.prviProstIndex;
        }

        public int setPrviProstiIndex(int a) {
            this.prviProstIndex = this.prviProstIndex + a;

            if (prviProstIndex < dolzinaClena) {
                this.imamoProstor = true;
            } else {
                this.imamoProstor = false;
            }

            if (this.prviProstIndex - 1 > this.dolzinaClena / 2) {
                cezPolovico = true;
            } else {
                cezPolovico = false;
            }
            return a;
        }

        public clenSeznama vrniNovClen() {

            return new clenSeznama(this.dolzinaClena);
        }

        public boolean dodajSeCeManjka() {
            if ((((this.prviProstIndex) < this.dolzinaClena / 2)) && this.naslednji != null
                    && this.naslednji.prviProstIndex != 0) {
                this.arr[this.prviProstIndex] = this.naslednji.arr[0];
                this.setPrviProstiIndex(1);

                return true;
            } else {
                // sowy
                return false;
            }
        }

        public clenSeznama(int n) {
            this.arr = new Integer[n];
            this.dolzinaClena = n;
            this.prviProstIndex = 0;
            this.cezPolovico = false;
            this.prejsni = null;
            this.naslednji = null;
            this.imamoProstor = true;
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
            System.out.printf("init(%d)\n", n);
            clenSeznama novi = new clenSeznama(n);
            novi.arr = new Integer[this.dolzinaClena];
            this.stClenov++;
            if (this.prvi == null) {
                // System.out.println("pric");
                this.prvi = novi;
                this.zadnji = novi;
            } else {
                // System.out.println("hejaaa");
                novi.naslednji = null;
                this.zadnji.naslednji = novi;
                novi.prejsni = this.zadnji;
                this.zadnji = novi;
            }

        }

        public boolean insert(int vrednost, int pozicija) {
            System.out.println("hej");
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
                System.out.println("nemoremo vstaviti");
                return false;
            }

            if (!temp.imamoProstor && pozicija == temp.prviProstIndex) {
                temp = temp.naslednji;
            }

            if (!temp.imamoProstor) {
                System.out.println("hej2");

                clenSeznama naslednji = temp.naslednji;
                clenSeznama noviObject = temp.vrniNovClen();

                temp.naslednji = noviObject;
                // naslednji.prejsni = noviObject;
                // noviObject.prejsni = temp;
                noviObject.naslednji = naslednji;
                int j = 0;
                for (int i = temp.dolzinaClena / 2; i < temp.dolzinaClena; i++) {
                    noviObject.arr[j] = temp.arr[i];
                    temp.arr[i] = null;
                    temp.setPrviProstiIndex(-1);
                    noviObject.setPrviProstiIndex(1);
                    j++;
                }
                System.out.println("hej3");

                return this.insert(vrednost, prvotnaPozicija);
            } else {
                System.out.println("hej4");

                for (int i = temp.arr.length - 1; i > pozicija; i--) {
                    temp.arr[i] = temp.arr[i - 1];
                }
                temp.arr[pozicija] = vrednost;
                temp.setPrviProstiIndex(1);
                return true;
                // mamo prostor delamo samo z enim arrayom shiftamo in prekanme in dodsamo
            }

        }

        // public boolean insert(int vrednost, int pozicija) {
        // System.out.printf("insert(%d,%d)\n", vrednost, pozicija);
        // // this.izpisiSeznamKonzola();
        // clenSeznama temp = this.prvi;
        // while (true) {
        // // System.out.println("hi");
        // // System.out.println(temp);
        // // System.out.printf("vrednost:%d pozicija:%d\n", vrednost, pozicija);
        // if (temp.imamoProstor) {
        // if (temp.prviProstIndex == pozicija) {
        // // System.out.println(temp.prviProstIndex + " HEHEHE");

        // temp.arr[temp.prviProstIndex] = vrednost;
        // temp.setPrviProstiIndex(1);
        // return true;
        // } else if (temp.prviProstIndex < pozicija) {
        // pozicija = pozicija - temp.prviProstIndex;
        // // System.out.println(temp.prviProstIndex);
        // // System.out.println(pozicija);
        // temp = temp.naslednji;
        // if (temp == null) {
        // return false;
        // }
        // } else {

        // for (int i = temp.prviProstIndex; i > pozicija; i--) {
        // // System.out.println(i + " HEHEHE");

        // temp.arr[i] = temp.arr[i - 1];
        // }
        // temp.arr[pozicija] = vrednost;
        // temp.setPrviProstiIndex(1);
        // return true;
        // }
        // } else {
        // if (temp.naslednji == null) {
        // // System.out.println("we are here");
        // if (temp.dolzinaClena < pozicija) {
        // pozicija = pozicija - temp.dolzinaClena;
        // // System.out.println(temp.prviProstIndex);
        // // System.out.println(pozicija);
        // temp = temp.naslednji;
        // if (temp == null) {
        // return false;
        // }
        // continue;
        // }
        // if (pozicija == temp.prviProstIndex) {
        // this.init(this.dolzinaClena);
        // clenSeznama next = temp.naslednji;
        // next.arr[0] = vrednost;
        // next.setPrviProstiIndex(1);
        // return true;
        // } else {
        // this.init(this.dolzinaClena);
        // clenSeznama next = temp.naslednji;
        // next.arr[0] = temp.arr[temp.dolzinaClena - 1];
        // next.setPrviProstiIndex(1);

        // for (int i = temp.arr.length - 1; i > pozicija; i--) {
        // // System.out.println(i + " HEHEHE");

        // temp.arr[i] = temp.arr[i - 1];
        // }
        // System.out.println(pozicija + " HEHEHE");

        // temp.arr[pozicija] = vrednost;
        // this.izpisiSeznamKonzola();
        // return true;
        // }

        // }
        // temp = temp.naslednji;
        // }

        // }

        // }

        public boolean remove(int pozicija) {
            clenSeznama temp = this.prvi;
            // this.izpisiSeznamKonzola();
            System.out.printf("remove(%d)\n", pozicija);

            while (true) {

                if (temp.prviProstIndex > pozicija) {

                    for (int i = pozicija; i < temp.arr.length - 1; i++) {
                        temp.arr[i] = temp.arr[i + 1];
                    }
                    temp.arr[temp.arr.length - 1] = null;
                    temp.setPrviProstiIndex(-1);
                    // System.out.println((temp.prviProstIndex) < temp.dolzinaClena / 2);
                    // System.out.printf("stEl:%d n:%d n/2:%d\n", temp.prviProstIndex,
                    // temp.dolzinaClena,
                    // temp.dolzinaClena / 2);
                    // this.izpisiSeznamKonzola();
                    if (temp.dodajSeCeManjka()) {

                        this.remove(pozicija + 1);
                    }

                    return true;

                } else {
                    // System.out.println("hi");
                    pozicija -= temp.prviProstIndex;
                    temp = temp.naslednji;
                    continue;
                }

            }

        }

        public void izpisiSeznam(PrintWriter p) {
            clenSeznama temp = this.prvi;
            // System.out.println(this.prvi);
            // System.out.println("izpisijuemo seznam");
            // System.out.println("###################");
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
                p.println();
                temp = temp.naslednji;
            }
            p.close();
            // System.out.println("###################");
        }

        public void izpisiSeznamKonzola() {

            clenSeznama temp = this.prvi;
            // System.out.println(this.prvi);
            // System.out.println("izpisijuemo seznam");
            // System.out.println("###################");
            // System.out.println();
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
                System.out.println();
                temp = temp.naslednji;
            }
            System.out.println();

            // System.out.println("###################");
        }

    }

    public static void main(String[] args) {
        Naloga3 zunajiNaloga3 = new Naloga3();
        String vrstica;
        int stUkazov;
        char ukaz;
        String[] argumenti;
        int arg1;
        int arg2;
        Seznam sez;
        // sez.init(5);
        // // System.out.println(sez.prvi);
        // // System.out.println(sez.zadnji);
        // sez.insert(7, 0);
        // sez.izpisiSeznam();
        // sez.insert(3, 1);
        // sez.izpisiSeznam();
        // sez.insert(4, 0);
        // sez.izpisiSeznam();
        // sez.insert(2, 3);
        // sez.izpisiSeznam();
        // sez.insert(1, 4);
        // sez.izpisiSeznam();
        // sez.insert(5, 3);
        // sez.izpisiSeznam();
        // sez.insert(8, 2);
        // sez.izpisiSeznam();

        try {
            BufferedReader read = new BufferedReader(new FileReader(args[0]));
            PrintWriter p = new PrintWriter(new FileWriter(args[1]));
            stUkazov = Integer.parseInt(read.readLine());

            vrstica = read.readLine();

            argumenti = vrstica.split(",");
            arg1 = Integer.parseInt(argumenti[1]);
            sez = zunajiNaloga3.new Seznam(arg1);
            // sez = zunajiNaloga3.new Seznam(5);
            sez.init(arg1);
            while ((vrstica = read.readLine()) != null) {
                // System.out.println(vrstica);
                ukaz = vrstica.charAt(0);
                // System.out.println("ukaz: " + ukaz);

                switch (ukaz) {
                case 'i':
                    argumenti = vrstica.split(",");

                    arg1 = Integer.parseInt(argumenti[1]);
                    arg2 = Integer.parseInt(argumenti[2]);
                    sez.insert(arg1, arg2);
                    sez.izpisiSeznamKonzola();
                    break;
                case 'r':
                    argumenti = vrstica.split(",");

                    arg1 = Integer.parseInt(argumenti[1]);
                    sez.remove(arg1);
                    sez.izpisiSeznamKonzola();

                    break;
                case 's':
                    sez = zunajiNaloga3.new Seznam(5);

                    argumenti = vrstica.split(",");
                    arg1 = Integer.parseInt(argumenti[1]);
                    sez.init(arg1);
                    sez.izpisiSeznamKonzola();

                    break;

                default:
                    break;
                }
            }
            sez.izpisiSeznam(p);
            System.out.println(sez.dolzina());
            // System.out.println("kkoncali brat list");
        } catch (Exception e) {
            System.err.println(e);
        }

    }
}