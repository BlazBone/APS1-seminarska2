import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Naloga9
 */
public class Naloga9 {

    public static void dodajPovezavo(ArrayList<ArrayList<Integer>> povezave, int zacetna, int koncna) {
        // itak je u obe smeri ma we
        povezave.get(zacetna).add(koncna);
        povezave.get(koncna).add(zacetna);
    }

    private static boolean BFS(ArrayList<ArrayList<Integer>> adj, int src,
            int dest, int v, int pred[], int dist[]) {
        // a queue to maintain queue of vertices whose
        // adjacency list is to be scanned as per normal
        // BFS algorithm using LinkedList of Integer type
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // boolean array visited[] which stores the
        // information whether ith vertex is reached
        // at least once in the Breadth first search
        boolean visited[] = new boolean[v];

        // initially all vertices are unvisited
        // so v[i] for all i is false
        // and as no path is yet constructed
        // dist[i] for all i set to infinity
        for (int i = 0; i < v; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        // now source is first to be visited and
        // distance from source to itself should be 0
        visited[src] = true;
        dist[src] = 0;
        queue.add(src);

        // bfs Algorithm
        while (!queue.isEmpty()) {
            int u = queue.remove();
            for (int i = 0; i < adj.get(u).size(); i++) {
                if (visited[adj.get(u).get(i)] == false) {
                    visited[adj.get(u).get(i)] = true;
                    dist[adj.get(u).get(i)] = dist[u] + 1;
                    pred[adj.get(u).get(i)] = u;
                    queue.add(adj.get(u).get(i));

                    // stopping condition (when we find
                    // our destination)
                    if (adj.get(u).get(i) == dest)
                        return true;
                }
            }
        }
        return false;
    }

    private static void dodajNajkrasiRazdalji(
            ArrayList<ArrayList<Integer>> adj,
            int s, int dest, int v, int stpotnikov, int[][] carina) {
        // predecessor[i] array stores predecessor of
        // i and distance array stores distance of i
        // from s
        int pred[] = new int[v];
        int dist[] = new int[v];

        if (BFS(adj, s, dest, v, pred, dist) == false) {
            System.out.println("Given source and destination" +
                    "are not connected");
            return;
        }

        // LinkedList to store path
        LinkedList<Integer> path = new LinkedList<Integer>();
        int crawl = dest;
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }

        for (int i = 1; i < path.size(); i++) {
            Integer prva = path.get(i - 1);
            Integer druga = path.get(i);

            if (prva < druga) {
                carina[prva][druga] += stpotnikov;
            } else {
                carina[druga][prva] += stpotnikov;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader tok = new BufferedReader(new FileReader(args[0]));
        PrintWriter p = new PrintWriter(new FileWriter(args[1]));

        String[] stevila = tok.readLine().split(",");
        int stPovezav = Integer.parseInt(stevila[0]);
        int stFacts = Integer.parseInt(stevila[1]);

        // for (int i = 0; i < stPovezav; i++) {
        // stevila = tok.readLine().split(",");
        // povezave[Integer.parseInt(stevila[0]) - 1][Integer.parseInt(stevila[1]) - 1]
        // = 1;
        // povezave[Integer.parseInt(stevila[1]) - 1][Integer.parseInt(stevila[0]) - 1]
        // = 1;
        // }
        int stNodes = 0;
        ArrayList<ArrayList<Integer>> povezave = new ArrayList<ArrayList<Integer>>(505);
        for (int i = 0; i < 505; i++) {
            povezave.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < stPovezav; i++) {
            stevila = tok.readLine().split(",");
            int ena = Integer.parseInt(stevila[0]);
            int dva = Integer.parseInt(stevila[1]);
            if (dva > stNodes) {
                stNodes = dva;
            }
            dodajPovezavo(povezave, ena, dva);
        }
        stNodes++;
        int[][] carina = new int[stNodes][stNodes];

        int zac = 1;
        int kon = 2;
        int stLjudi = 10;
        for (int i = 0; i < stFacts; i++) {
            stevila = tok.readLine().split(",");

            dodajNajkrasiRazdalji(povezave, Integer.parseInt(stevila[0]), Integer.parseInt(stevila[1]), 505,
                    Integer.parseInt(stevila[2]), carina);
        }

        int max = 0;
        ArrayList<int[]> rezultati = new ArrayList<>();
        for (int i = 1; i < carina.length; i++) {
            for (int j = i; j < carina.length; j++) {
                if (carina[i][j] > max) {
                    // System.out.println("HERE " + i + " " + j);
                    rezultati = new ArrayList<>();
                    int[] jaja = new int[2];
                    jaja[0] = i;
                    jaja[1] = j;
                    rezultati.add(jaja);
                    max = carina[i][j];
                } else if (carina[i][j] == max) {
                    int[] jaja = new int[2];
                    jaja[0] = i;
                    jaja[1] = j;
                    rezultati.add(jaja);
                }
            }
        }
        // System.out.println("FISODA JHFSDHJFSDHJ A");
        for (int[] biba : rezultati) {
            System.out.println(Arrays.toString(biba));
            p.printf("%d,%d\n", biba[0], biba[1]);
        }

        p.close();

    }
}