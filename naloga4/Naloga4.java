import java.io.*;
import java.util.Arrays;

public class Naloga4 {

    public static class QueueElement {
        Object element;
        QueueElement next;

        QueueElement() {
            element = null;
            next = null;
        }
    }

    public static class Queue {
        // QueueElement -> QueueElement -> QueueElement -> ... -> QueueElement
        // front rear
        //
        // nove elemente dodajamo za element 'rear'
        // elemente jemljemo s front strani

        private QueueElement front;
        private QueueElement rear;
        private int stClenov;
        private int maksDolzina;

        public Queue(int n) {
            makenull();
            this.maksDolzina = n;
        }

        public void makenull() {
            front = null;
            rear = null;
            stClenov = 0;

        }

        public boolean empty() {
            return (front == null);
        }

        public Object front() {
            if (!empty())
                return front.element;
            else
                return null;
        }

        public void enqueue(Object obj) {
            QueueElement el = new QueueElement();
            el.element = obj;
            el.next = null;

            if (empty()) {
                front = el;
            } else {
                rear.next = el;
            }
            this.stClenov++;
            rear = el;
        }

        public void dequeue() {
            if (!empty()) {
                front = front.next;
                stClenov--;
                if (front == null)
                    rear = null;
            }
        }

        public void natisniVDatoteko(PrintWriter p) {
            QueueElement temp = this.front;

            while (temp != null) {
                if (temp == this.rear) {
                    p.print(temp.element);
                } else {
                    p.print(temp.element);
                    p.print(",");
                }
                temp = temp.next;
            }
            p.println();

        }

        public void natisniVrsto() {
            QueueElement temp = this.front;
            System.out.print("VRSTA: ");
            while (temp != null) {
                System.out.print(((Stranka) temp.element).id);
                System.out.print(" ");
                temp = temp.next;
            }
            if (!this.empty()) {
                System.out.print(" front: " + ((Stranka) front.element).id);
                System.out.print(" rear: " + ((Stranka) rear.element).id);
            }

            System.out.println();
        }

        public void umakniDo(int cas) {
            // this.natisniVrsto();
            if (this.empty()) {
                return;
            } else if (this.stClenov == 1) {
                // System.out.println("SAMO EN CLEN POGLEJ CE JE PROW");
                if (((Stranka) (front.element)).cakaDo <= cas) {
                    // System.out.printf(" Stranka %d je odlsa zaradni poterplenja.\n", ((Stranka)
                    // (front.element)).id);

                    this.dequeue();
                }
            } else {
                QueueElement temp = front;
                while (temp.next != null) {
                    // System.out.print("|||| |||" + ((Stranka) (temp.next.element)).id);
                    if (((Stranka) (temp.next.element)).cakaDo <= cas) {
                        // System.out.printf(" Stranka %d je odlsa zaradni poterplenja.\n",((Stranka)
                        // (temp.next.element)).id);
                        if (temp.next == this.rear) {
                            this.rear = temp;
                        }
                        temp.next = temp.next.next;
                        this.stClenov--;
                    }
                    temp = temp.next;
                    if (temp == null) {
                        break;
                    }
                }

                if (((Stranka) (front.element)).cakaDo <= cas) {
                    // System.out.printf(" Stranka %d je odlsa zaradni poterplenja.\n", ((Stranka)
                    // (front.element)).id);

                    this.dequeue();
                }

            }
        }
    }

    public static class Stranka {
        private int id;
        private int cakaDo;

        public Stranka(int id, int cakaDo) {
            this.id = id;
            this.cakaDo = cakaDo;
        }
    }

    public static void main(String[] args) throws IOException {
        int stKorakovSimulacije = 0;
        int stStolovVCakalnici = 0;
        int trajanjeStrizenja = 0;
        int podalnjasnjeStrizenja = 0;
        int[] zamikiPrihodovStrank = null;

        int[] potrpljenjeStrank = null;

        BufferedReader read = new BufferedReader(new FileReader(args[0]));
        PrintWriter p = new PrintWriter(new FileWriter(args[1]));
        String vrstica;
        int i = 0;
        while ((vrstica = read.readLine()) != null) {
            switch (i) {
            case 0:
                stKorakovSimulacije = Integer.parseInt(vrstica);
                break;
            case 1:
                stStolovVCakalnici = Integer.parseInt(vrstica);

                break;
            case 2:
                trajanjeStrizenja = Integer.parseInt(vrstica);

                break;
            case 3:
                podalnjasnjeStrizenja = Integer.parseInt(vrstica);

                break;
            case 4:
                String[] items = vrstica.replaceAll("\\s", "").split(",");
                zamikiPrihodovStrank = new int[items.length];
                for (int j = 0; j < items.length; j++) {

                    zamikiPrihodovStrank[j] = Integer.parseInt(items[j]);

                }
                break;
            case 5:
                String[] items2 = vrstica.replaceAll("\\s", "").split(",");
                potrpljenjeStrank = new int[items2.length];
                for (int j = 0; j < items2.length; j++) {

                    potrpljenjeStrank[j] = Integer.parseInt(items2[j]);

                }
                break;
            default:
                break;
            }

            i++;
        }

        // System.out.println(stKorakovSimulacije);
        // System.out.println(stStolovVCakalnici);
        // System.out.println(trajanjeStrizenja);
        // System.out.println(podalnjasnjeStrizenja);
        // System.out.println(Arrays.toString(zamikiPrihodovStrank));
        // System.out.println(Arrays.toString(potrpljenjeStrank));

        Queue cakalnaVrsta = new Queue(stStolovVCakalnici);
        Queue ostrizeni = new Queue(stKorakovSimulacije);
        boolean stolJeProst = true;
        int idStranke = 0;
        int dolzinaStrizenja = trajanjeStrizenja;
        Stranka trenutnaStranka = null;
        int casovnaPerioda = 1;
        int casZaNovoStranko = zamikiPrihodovStrank[0];
        while (casovnaPerioda <= stKorakovSimulacije) {
            // System.out.printf("Korak %d:\n", casovnaPerioda);
            // cakalnaVrsta.natisniVrsto();
            // ce se je stranka strizgla in se konca potem stranka gre od salona strizenje
            // se podaljsa
            if (!stolJeProst) {
                // trenutnaStranka;
                // System.out.println(casovnaPerioda);
                if (trenutnaStranka.cakaDo == casovnaPerioda) {
                    // System.out.println("STRANKA SE JE OSTRIGLA ID: " + trenutnaStranka.id);
                    // System.out.print(trenutnaStranka.id);
                    // System.out.print(",");
                    // System.out.printf(" Stranka %d je postrizena.\n", trenutnaStranka.id);
                    // p.print(trenutnaStranka.id);
                    // p.print(",");
                    ostrizeni.enqueue(trenutnaStranka.id);
                    trenutnaStranka = null;
                    dolzinaStrizenja += podalnjasnjeStrizenja;
                    stolJeProst = true;

                }
            }
            // ce je frizerski salon zdj prazn posedemo novo stranko na frizerski stol iz
            // cakalne vrste
            if (stolJeProst) {
                if (!(cakalnaVrsta.empty())) {
                    // System.out.println("hejla " + casovnaPerioda);
                    trenutnaStranka = (Stranka) cakalnaVrsta.front();
                    cakalnaVrsta.dequeue();
                    trenutnaStranka.cakaDo = casovnaPerioda + dolzinaStrizenja;
                    // System.out.printf(" Stranka %d gre iz cakalne na stol.\n",
                    // trenutnaStranka.id);

                    stolJeProst = false;
                }
            }
            // sprehodi se skozi queue in pogelj ce kdo predolgo caka in ga vrzi vn iz vrste

            cakalnaVrsta.umakniDo(casovnaPerioda);

            // ce nastopi cas za prihod nove stranke potem jo zgenerirramo(pravilen id ter
            // ostale lastnosti), ter se ta usede na stol ce je prazen na ckaalnico ce imamo
            // prostor in cene pa gre domow
            if (casovnaPerioda == casZaNovoStranko) {
                // System.out.println("Prihod stranke " + (idStranke + 1) + " ob :" +
                // casovnaPerioda);
                // int[] novaStranka = {idStranke+1,casovnaPerioda +
                // potrpljenjeStrank[idStranke%potrpljenjeStrank.length]};
                Stranka novaStranka = new Stranka(idStranke + 1,
                        casovnaPerioda + potrpljenjeStrank[idStranke % potrpljenjeStrank.length]);
                // smo kreirali novo stranko
                // System.out.printf(" Stranka %d je prisla.\n", novaStranka.id);

                if (stolJeProst) {

                    novaStranka.cakaDo = casovnaPerioda + dolzinaStrizenja;
                    trenutnaStranka = novaStranka;
                    stolJeProst = false;
                    // System.out.printf(" Stranka %d gre takoj na stol.\n", trenutnaStranka.id);

                    // moramo povecati dolzino strizenja

                    // System.out.println("Stranka se je usedla na stol " + trenutnaStranka.id + "
                    // CAka do "
                    // + trenutnaStranka.cakaDo);
                } else if (cakalnaVrsta.stClenov < stStolovVCakalnici) {
                    // cakalnica
                    cakalnaVrsta.enqueue(novaStranka);
                    // System.out.printf(" Stranka %d je sla v cakalno vrsto.\n", novaStranka.id);

                    // System.out.println("dodamo v cakalnico");
                } else {
                    // System.out.printf(" Stranka %d je odsla, ni vec placa.\n", novaStranka.id);

                    // stol ni prost in cakalnica tudi ne potem s stranko ne naridemo nicesar, saj
                    // bo ta odsla
                }
                idStranke++;
                casZaNovoStranko += zamikiPrihodovStrank[idStranke % zamikiPrihodovStrank.length];
            }
            casovnaPerioda++;
        }
        ostrizeni.natisniVDatoteko(p);
        p.close();
        read.close();
    }
}