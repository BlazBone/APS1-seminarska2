import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Naloga5
 */
public class Naloga5 {
    // javna staticna spremenljika pac nebi rabli ampak ker sem jo rabil v prejsnih
    // iteracijah naloge pac je ostala if it works dont dfix it kinda way
    public static int MAX_PREMIKOV = 0;

    public static void main(String[] args) throws IOException {

        BufferedReader read = new BufferedReader(new FileReader(args[0]));
        PrintWriter p = new PrintWriter(new FileWriter(args[1]));

        int steviloKupckov = Integer.parseInt(read.readLine());
        int[] kupcki = new int[steviloKupckov];
        for (int i = 0; i < steviloKupckov; i++) {
            kupcki[i] = Integer.parseInt(read.readLine());

        }
        read.close();
        // pogledamo ce mamo sploh kej sanse zmagati ce nimamo potem automatsko
        // sprintamo -1 ter se zahvalimo za tako lahko nalogo
        //
        // ce vemo dabomo zmagali pole pa klicemo funkcjio ki zracuna premike
        if (nima2(kupcki) == 0) {
            p.print(-1);
            p.print('\n');
            p.close();
        } else {
            bubbleSort(kupcki);// ni treba sortirati ampak jas sem ker pole sm lazje debugu
            resiRek2(kupcki, 0, true);
            p.print(MAX_PREMIKOV);
            p.print('\n');
            p.close();

        }

    }

    // pac bubble sort nerabimo neke razlage
    // enega po enega sortiramo ter se array pocasi sortira kot celota
    public static void bubbleSort(int[] kupcki) {
        int temp = 0;
        for (int i = 0; i < kupcki.length; i++) {
            for (int j = 1; j < (kupcki.length - i); j++) {
                if (kupcki[j - 1] > kupcki[j]) {

                    temp = kupcki[j - 1];
                    kupcki[j - 1] = kupcki[j];
                    kupcki[j] = temp;
                    // ce je en vecji ko drugi jih zamenjamo ker hocmo d je tavelik zadi ter malil
                    // spredi
                }

            }
        }

    }

    // druga verzija tiste funkcjie
    // kaj nrdi ta funkcija pac poisce najbl efektivn move za prvega igralca to je
    // premik ki je najbl optimaln je premik, ki vzame najvec kamenckov in pri tem
    // spremeni nim sum kupckov na 0 (formukla sa wikipedije)
    // automatsko odstrani in to je to
    public static void makeNimSumZeroDva(int[] kupcki) {
        int nimSum = nima2(kupcki);
        int maksRazlika = 0;
        int indexMaksa = 0;
        int razlika;
        for (int i = 0; i < kupcki.length; i++) {
            // ce je prazen kupcek, ga ne gledamo
            if (kupcki[i] == 0) {
                continue;
            }

            int temp = nimSum ^ kupcki[i];
            if (temp < kupcki[i]) {
                // kupcki[i] = temp;
                razlika = kupcki[i] - temp;

                if (razlika > maksRazlika) {
                    maksRazlika = razlika;
                    indexMaksa = i;
                }
            }
        }

        kupcki[indexMaksa] = kupcki[indexMaksa] - maksRazlika;
    }

    //
    public static void resiRek2(int[] kupcki, int stPremikov, boolean igraPrvi) {
        // ko pridemo notr je premik alwawys veljavn ker to cekiramo ze predn klicemo
        // rekurzijo
        // pogledamo ce je prazno ce je prazno smo na konci pole samo return oz nerabimo
        // pisat pau auto konca se rekurzija
        // ce ni prazno pogledamo kateri igralec je na vrsti in za njega poklicemo
        // doloceno funkcijo ki bo izvedla njegov premik
        // po izvedenem premiku poklicemo funkcjio rekurzivno ter

        // bubbleSort(kupcki);
        if (isEmpty(kupcki)) {
            MAX_PREMIKOV = stPremikov;
        } else {
            if (igraPrvi) {
                makeNimSumZeroDva(kupcki);
                resiRek2(kupcki, stPremikov + 1, !igraPrvi);
            } else {
                lowestNimSum(kupcki);
                resiRek2(kupcki, stPremikov + 1, !igraPrvi);
            }

        }

    }
    // to je funkcjia ki pac polgda optimalni(nevem ce je sklepamo malo)
    // ideja je da pogledamo use mozne premike pac z dvemi for zankami za usak
    // kupcek pogledamo kolko uzamemo ter za vsako vzeto stevilo pogledamo koliko je
    // nimm sum novo pridobljenih kucku
    // ideja je da kjer je nimm sum v naslednji potezi najmanjsi bo to pomenilo, da
    // bo prvi igralec moral vzeti manj iz kupcka(ta skusa vzeti njavec kar se da),
    // da bo nimm sum postal 0(to je seveda njegova naloga)
    // te makse si zapomnimo in potem naredimo ta premik

    public static void lowestNimSum(int[] kupcki) {
        int maxIndex = 0;
        int minSum = Integer.MAX_VALUE;
        int maxRazlika = 0;
        int temp;

        int temp2;
        for (int i = 0; i < kupcki.length; i++) {
            temp2 = kupcki[i];
            for (int j = 0; j < temp2; j++) {
                if (kupcki[i] > 0) {
                    kupcki[i]--;
                    temp = nima2(kupcki);

                    if (temp < minSum) {
                        minSum = temp;
                        maxIndex = i;
                        maxRazlika = temp2 - kupcki[i];
                    }

                    kupcki[i]++;
                }
            }
            kupcki[i] = temp2;
        }

        kupcki[maxIndex] = kupcki[maxIndex] - maxRazlika;

    }

    // gremo skozi seznam ter pogledamo ali imamo elemnet ki ni enak 0 ter vrnemo
    // false cene true samoumevno kao
    public static boolean isEmpty(int[] arr) {

        for (int i : arr) {
            if (i != 0) {
                return false;
            }
        }

        return true;
    }

    // nima2 druga verzija racunanja nimSumm ki pac zracuna nimm summ ter ga vrne ce
    // temp zacne z 0 ta ne vpliva na xor ker x xor 0 = x
    public static int nima2(int[] kupcki) {
        int nimmSum = 0;

        for (int i : kupcki) {
            nimmSum ^= i;
        }
        return nimmSum;
    }

}