import java.io.*;

/**
 * Naloga1
 */
public class Naloga1 {

    public static void main(String[] args) {

        try {
            String vrstica;
            BufferedReader tok = new BufferedReader(new FileReader(args[0]));
            PrintWriter p = new PrintWriter(new FileWriter(args[1]));
            vrstica = tok.readLine();
            int n = Integer.parseInt(vrstica);
            int[][] tabela = new int[n][n];
            char[][] tabelaChar = new char[n][n];
            int j = 0;
            while ((vrstica = tok.readLine()) != null) {
                String[] items = vrstica.replaceAll("\\s", "").split(",");
                int[] results = new int[items.length];
                for (int i = 0; i < items.length; i++) {
                    try {
                        results[i] = Integer.parseInt(items[i]);
                    } catch (NumberFormatException nfe) {
                        System.err.println(nfe);
                    }
                }
                tabela[j] = results;
                j++;
            }
            noviPoskus(tabela, tabelaChar, 0, 0, n, 0, 0, p);
            tok.close();
            p.close();
        } catch (Exception e) {
            System.out.println(e);
            System.err.println("smetnana muha kaj si tak suha kdo te bo placu miha kovacu");
            System.exit(1);
        }
    }

    // nam samo vrne true false glede na to ali je visinska med dvema poljema okej
    public static boolean jeVisinskaOk(int trenutno, int naslednje) {
        int razlika = trenutno - naslednje;
        return razlika <= 30 && razlika >= -20;
    }

    // zracuna vzpon med dvema poljema
    public static int vsponcek(int a, int b) {
        if (a < b) {
            return b - a;
        } else {
            return 0;
        }
    }

    // ideja je recursion with backtracking ne
    // na usakem kvadratku pogledamo ce je veljavni x y pole ce sploh lohko gremo
    // tja zarad visinske alpa ce smo ze bloi
    // ko to pogledamo pole smo gvisno na novem veljavnem polju, ce je to cilj pole
    // pac sprontamo cene pa gremo v use 4 smeri pogledt za naprej.
    // umes pa pristevamo visino
    // in seveda oznacimo polje kjer smo ze bili
    public static boolean noviPoskus(int[][] glavnaTabela, char[][] charTabela, int x, int y, int n, int vzpon,
            int prejsne, PrintWriter p) {

        if (y < 0 || y >= n) {
            return false;
        }

        if (x < 0 || x >= n) {
            return false;
        }
        if (charTabela[y][x] == '*' || !jeVisinskaOk(prejsne, glavnaTabela[y][x])) {
            return false;
        }

        if (x == n - 1 && y == n - 1) {
            p.print(vzpon + vsponcek(prejsne, glavnaTabela[x][y]));
            p.println();
            return true;
        }

        charTabela[y][x] = '*';

        if (noviPoskus(glavnaTabela, charTabela, x + 1, y, n, vzpon + vsponcek(prejsne, glavnaTabela[y][x]),
                glavnaTabela[y][x], p)) {
            return true;
        }
        if (noviPoskus(glavnaTabela, charTabela, x, y + 1, n, vzpon + vsponcek(prejsne, glavnaTabela[y][x]),
                glavnaTabela[y][x], p)) {
            return true;
        }
        if (noviPoskus(glavnaTabela, charTabela, x - 1, y, n, vzpon + vsponcek(prejsne, glavnaTabela[y][x]),
                glavnaTabela[y][x], p)) {
            return true;
        }
        if (noviPoskus(glavnaTabela, charTabela, x, y - 1, n, vzpon + vsponcek(prejsne, glavnaTabela[y][x]),
                glavnaTabela[y][x], p)) {
            return true;
        }
        return false;
    }
}
