import java.io.*;

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
        // zgori so pac basic funkcije ki so bile ze obdelane pri vajah



        //funckija natisne quee v podano datoteko
        // v nasem primeru je to queue kjer so ostrizene stranke (v staticnem se jih ni splacalo met i mean lohko bi samo pac lohko tud to
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

        //koda ki pride prow sam za debug
        // public void natisniVrsto() {
        //     QueueElement temp = this.front;
        //     System.out.print("VRSTA: ");
        //     while (temp != null) {
        //         System.out.print(((Stranka) temp.element).id);
        //         System.out.print(" ");
        //         temp = temp.next;
        //     }
        //     if (!this.empty()) {
        //         System.out.print(" front: " + ((Stranka) front.element).id);
        //         System.out.print(" rear: " + ((Stranka) rear.element).id);
        //     }

        //     System.out.println();
        // }


        //funkcija ki umakne vse strnkae ki nemorejo vec cakatai 
        //umakne use stranke ki niso sposobne cakati naprej od podanega casa
        public void umakniDo(int cas) {

            if (this.empty()) {
                return;
            } else if (this.stClenov == 1) {
                if (((Stranka) (front.element)).cakaDo <= cas) {

                    this.dequeue();
                }
            } else {
                QueueElement temp = front;
                while (temp.next != null) {
                    if (((Stranka) (temp.next.element)).cakaDo <= cas) {
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
                    this.dequeue();
                }

            }
        }
    }

    //stranka samo zato da mamo njene podatke lepsi lahko bi imeli simple int[2]
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

        //ker so inputi vedno enaki naredimo pac za vsako vrstico da obdela drugace.
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
            //naredi string ki ima notr elemnte stevilke ampak so stringi  potem te stringe parsamo v inte in jih damo v tabelo
                String[] items = vrstica.replaceAll("\\s", "").split(",");
                zamikiPrihodovStrank = new int[items.length];
                for (int j = 0; j < items.length; j++) {

                    zamikiPrihodovStrank[j] = Integer.parseInt(items[j]);

                }
                break;
            case 5:
            //podobno kot gori ker je isti princip
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

        Queue cakalnaVrsta = new Queue(stStolovVCakalnici);// cakalna
        Queue ostrizeni = new Queue(stKorakovSimulacije);
        boolean stolJeProst = true;
        int idStranke = 0;
        int dolzinaStrizenja = trajanjeStrizenja;
        Stranka trenutnaStranka = null;
        int casovnaPerioda = 1;
        int casZaNovoStranko = zamikiPrihodovStrank[0];
        while (casovnaPerioda <= stKorakovSimulacije) {
           //samo sledimo napisanim navodilom v nalogi in postopoma pisemo korake


           //ce stol ni prost kar pomeni da se nekdo strize in se njen cas cakado potece potem je ta ostrizena jo damo v vrsto ostrizenih in jo vrzemo iz stola stopl postane prost  ter podaljsamo dolzino strizenja

            if (!stolJeProst) {
                if (trenutnaStranka.cakaDo == casovnaPerioda) {
                    ostrizeni.enqueue(trenutnaStranka.id);
                    trenutnaStranka = null;
                    dolzinaStrizenja += podalnjasnjeStrizenja;
                    stolJeProst = true;

                }
            }
            // ce je stol prost in vrsta ni prazna potem dodamo stranko na stol kar pomeni da jo odstranimo iz vrste ter ji spremenimo cakado na cas strizena
            // also stol pppotem nivec prost
            if (stolJeProst) {
                if (!(cakalnaVrsta.empty())) {
                    trenutnaStranka = (Stranka) cakalnaVrsta.front();
                    cakalnaVrsta.dequeue();
                    trenutnaStranka.cakaDo = casovnaPerioda + dolzinaStrizenja;

                    stolJeProst = false;
                }
            }

            //usako periodo pogledamo kolkoo je takih ki so v vrsti in jim je mal dosadilo in pole leavnejo to pohendla funkcija
            cakalnaVrsta.umakniDo(casovnaPerioda);



            //ce v tej periodi pride nova stranka potem jo to naredimo in ce je stol prost se ta kar usede ter je stol zaseden ce pa stol ni prost potem jo damo v vrsto le ce je v vrsti cse prostor ce ga ni ptem lahko enostavno pozabimo na stranko
            //nesmemo pozabiti da id strake vedno prostejemo ter nastavimo nov cas za novostranko 
            if (casovnaPerioda == casZaNovoStranko) {
                Stranka novaStranka = new Stranka(idStranke + 1,
                        casovnaPerioda + potrpljenjeStrank[idStranke % potrpljenjeStrank.length]);

                if (stolJeProst) {

                    novaStranka.cakaDo = casovnaPerioda + dolzinaStrizenja;
                    trenutnaStranka = novaStranka;
                    stolJeProst = false;
                } else if (cakalnaVrsta.stClenov < stStolovVCakalnici) {
                    cakalnaVrsta.enqueue(novaStranka);
                } else {
                }
                idStranke++;
                casZaNovoStranko += zamikiPrihodovStrank[idStranke % zamikiPrihodovStrank.length];
            }
            //vsak obhod povecamo casovno periodo
            casovnaPerioda++;
        }
        ostrizeni.natisniVDatoteko(p);
        p.close();
        read.close();
    }
}