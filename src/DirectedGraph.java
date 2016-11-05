import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by polarvenezia on 2/11/16.
 *
 * Graph class that transform the cnf representation to directed graph representation
 *
 */
public class DirectedGraph {

    private ArrayList<Integer>[] adj;  // Be aware: list start count at 0
    int numOfV = 0;
    int numOfE = 0;

    DirectedGraph (List<String> cnfFile) throws Exception{
        String[] fileInfo = cnfFile.get(1).trim().split("\\s+");
        numOfV = Integer.parseInt(fileInfo[2])+1;
        adj = (ArrayList<Integer>[]) new ArrayList[numOfV*2];
        for (int i = 0; i < numOfV*2; i++){
            adj[i] = new ArrayList<>();
        }
        System.out.println(adj.length);
        for (String lines : cnfFile.subList(2,cnfFile.size())){
            String[] lineInfo = lines.trim().split("\\s+");
            if (lineInfo.length == 2) {
                int v = Integer.parseInt(lineInfo[0]);
                int w = Integer.parseInt(lineInfo[1]);
                addEdge(-v,w);
                addEdge(-w,v);
            }
            else if (lineInfo.length == 1){
                int v = Integer.parseInt(lineInfo[0]);
                addEdge(-v,v);
            }
            else throw new Exception("Not 2-SAT format, line info: " + Arrays.toString(lineInfo));

        }
    }

    public void addEdge(int v, int w){
        if ( v > 0 && w > 0) adj[v].add(w);
        else if ( v > 0 && w < 0) adj[v].add(numOfV - w);
        else if ( v < 0 && w > 0) adj[numOfV - v].add(w);
        else adj[numOfV - v].add(numOfV - w);
        numOfE++;
    }

    public Iterable<Integer> getAdj(int v){
        return adj[v];
    }

    public int V(){ return numOfV*2; }

    public int E(){ return numOfE; }

}
