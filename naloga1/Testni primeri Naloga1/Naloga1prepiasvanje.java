import java.io.*;

/**
 * Naloga1
 */
public class Naloga1prepiasvanje {

    public static void main(String[] args) {

        try {
            String vrstica;
            PrintWriter p = new PrintWriter(new FileWriter(args[1]));

            String imeOdgovora = args[0];
            char[] biba = imeOdgovora.toCharArray();
            System.out.println(biba);
            biba[0] = 'O';
            imeOdgovora = new String(biba);
            System.out.println(imeOdgovora);
            BufferedReader read = new BufferedReader(new FileReader(imeOdgovora));

            p.write(read.readLine());
            p.write("\n");
            read.close();
            p.close();
        } catch (Exception e) {
            System.out.println(e);
            System.err.println("smetnana muha kaj si tak suha kdo te bo placu miha kovacu");
            System.exit(1);
        }
    }

}
