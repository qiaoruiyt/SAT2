import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by polarvenezia on 2/11/16.
 */
public class SAT2SolverTest {
    public static void main(String[] args) {
        System.out.println("Test");
        final String filePath = "/home/polarvenezia/Downloads/Project-2D-starting-master/sampleCNF/largeSat.cnf";
        try{
            DirectedGraph directedGraph = new DirectedGraph(readFile(filePath));
            long started = System.nanoTime();
            SAT2Solver sat2Solver = new SAT2Solver(directedGraph);
            int[] result = sat2Solver.solve();
            long time = System.nanoTime();
            long timeTaken= time - started;
            System.out.println("Time:" + timeTaken/1000000.0 + "ms");
            System.out.println("Result: " + Arrays.toString(result));
            System.out.println(Arrays.toString(sat2Solver.id));
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    // temperorily just use this
    private static List<String> readFile(String filename)
    {
        List<String> data = new ArrayList<String>();
        try
        {
            BufferedReader bReader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = bReader.readLine()) != null)
            {
                data.add(line);
            }
            bReader.close();
            return data;
        }
        catch (Exception e)
        {
            System.err.format("ERROR reading the file");
            e.printStackTrace();
            return null;
        }
    }
}
