
import java.util.Stack;

/**
 * Created by polarvenezia on 2/11/16.
 */
public class SAT2Solver {


    private boolean[] marked;        // marked[v] = has v been visited?
    int[] id;                        // id[v] = id of strong component containing v
    private int[] low;               // low[v] = low number of v
    private int pre;                 // preorder number counter
    private int count;               // number of strongly-connected components
    private Stack<Integer> stack;
    private int[] solution;
    private int[] solutionMarked;    // -1 is false, 1 is true, 0 is contigent
    DirectedGraph G;

    SAT2Solver(DirectedGraph graph){
        G = graph;
        marked = new boolean[G.V()];
        stack = new Stack<>();
        id = new int[G.V()];
        low = new int[G.V()];
        solution = new int[G.numOfV];
        solutionMarked = new int[G.numOfV];

    }

    public int[] solve(){
        // depth first search
        for (int v = 1; v < G.V(); v++) {
            if (v == G.numOfV) continue;
            if (!marked[v]) dfs(G, v);
        }

        // solve sat2
        for (int v = 1; v < G.numOfV; v++){
            if (stronglyConnected(v,v+G.numOfV)) {
                System.out.println(v);
                return null;
            }
        }

        for (int v = 1; v < G.numOfV; v++){
            if (id[v] < id[v+G.numOfV]) solution[v] = 1;
            else solution[v] = 0;
        }

        return solution;
    }


    private void dfs(DirectedGraph G, int v) {
        marked[v] = true;
        low[v] = pre++;
        int min = low[v];
        stack.push(v);
        for (int w : G.getAdj(v)) {
            if (!marked[w]) dfs(G, w);
            if (low[w] < min) min = low[w];
        }
        if (min < low[v]) {
            low[v] = min;
            return;
        }
        int w;
        do {
            w = stack.pop();
            id[w] = count;
            low[w] = G.V();
        } while (w != v);
        count++;
    }


    public boolean stronglyConnected(int v, int w) {
        return id[v] == id[w];
    }


    public int id(int v) {
        return id[v];
    }


    public int count() {
        return count;
    }
}
