import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Naloga8
 */
public class Naloga8 {

    public static class DreevesceBozicnoLepo {
        public Node root;

        public DreevesceBozicnoLepo(Node to) {
            this.root = to;
        }

        public void poNivojih(PrintWriter p) {
            if (this.root == null) {
                return;
                // tega itak ne rabimo ma kao safety
            }

            Queue<Node> q = new LinkedList<Node>();

            q.add(this.root);

            while (true) {

                // nodeCount (queue size) indicates number of nodes
                // at current level.
                int nodeCount = q.size();
                if (nodeCount == 0)
                    break;

                // Dequeue all nodes of current level and Enqueue all
                // nodes of next level
                while (nodeCount > 0) {
                    Node node = q.peek();
                    p.printf("%d,%d,%d\n", node.vrednost, node.x, node.y);
                    // System.out.printf("%d,%d,%d\n", node.vrednost, node.x, node.y);
                    q.remove();
                    if (node.levi != null)
                        q.add(node.levi);
                    if (node.desni != null)
                        q.add(node.desni);
                    nodeCount--;
                }
            }
        }

        public void izpisi() {
            this.izpisirek(this.root);
        }

        public void izpisirek(Node a) {
            if (a == null) {
                return;
            }
            a.y = levo + desno;
            if (a.desni == null && a.levi == null) {
                // System.out.print(" this one ->");
                // System.out.print(a.vrednost);

            } else {
                if (a.levi != null) {
                    levo++;
                    poVrsti.add(poVrsti.indexOf(a), a.levi);
                    izpisirek(a.levi);
                    levo--;
                }
                if (a.desni != null) {
                    desno++;
                    poVrsti.add(poVrsti.indexOf(a) + 1, a.desni);
                    izpisirek(a.desni);
                    desno--;
                }
                // System.out.print(" ");
                // System.out.print(a.vrednost);

            }

        }
    }

    public static class Node {
        public int vrednost;
        public Node levi;
        public Node desni;
        public int id;
        public int leviiId;
        public int desniId;
        public int x;
        public int y;

        public Node(String[] a) {
            this.vrednost = Integer.parseInt(a[1]);
            this.id = Integer.parseInt(a[0]);
            this.desniId = Integer.parseInt(a[3]);
            this.leviiId = Integer.parseInt(a[2]);
            this.levi = null;
            this.desni = null;
            this.x = -1;
            this.y = -1;

            vsiNodsi.add(Integer.parseInt(a[0]));
            sinovi.add(Integer.parseInt(a[3]));
            sinovi.add(Integer.parseInt(a[2]));
        }
        // public Node(int id, int vrednost, int leviId, int desniId) {
        // this.vrednost = vrednost;
        // this.id = id;
        // this.desniId = desniId;
        // this.leviiId = leviId;
        // this.levi = null;
        // this.desni = null;
        // }

    }

    public static int levo = 0;
    public static int desno = 0;
    public static HashSet<Integer> vsiNodsi = new HashSet<>();
    public static HashSet<Integer> sinovi = new HashSet<>();
    public static LinkedList<Node> poVrsti = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader tok = new BufferedReader(new FileReader(args[0]));
        PrintWriter p = new PrintWriter(new FileWriter(args[1]));

        int numOfNOdes = Integer.parseInt(tok.readLine());

        Node[] allNodes = new Node[numOfNOdes];

        for (int i = 0; i < numOfNOdes; i++) {
            allNodes[i] = new Node(tok.readLine().split(","));
        }
        // da najdemo root pogledamo kiri node nima nobenega ki bi kazal najn to je tata
        // smrkec
        sinovi.remove(-1);
        vsiNodsi.removeAll(sinovi);
        int idGlavnega = (int) vsiNodsi.toArray()[0];

        DreevesceBozicnoLepo drev = null;

        for (Node node1 : allNodes) {
            if (node1.id == idGlavnega) {
                drev = new DreevesceBozicnoLepo(node1);
                poVrsti.add(node1);
            }
            for (Node node2 : allNodes) {
                if (node1.desniId == node2.id) {
                    node1.desni = node2;
                }
                if (node1.leviiId == node2.id) {
                    node1.levi = node2;
                }
            }
        }
        // System.out.println(poVrsti.size());
        // System.out.println(allNodes.length);
        drev.izpisi();
        // System.out.println();
        // for (Node node : allNodes) {
        // System.out.printf("%d,%d,%d\n", node.vrednost, node.y, node.x);
        // }
        int i = 0;
        for (Node node : poVrsti) {
            // System.out.println(node.vrednost);
            node.x = i;
            i++;
        }

        drev.poNivojih(p);
        tok.close();
        p.close();
    }
}