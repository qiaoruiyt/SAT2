import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by polarvenezia on 2/11/16.
 *
 * This is the main test to be executed
 *
 */
public class SAT2SolverTest {

    // Input file path
    public static String filePath = "/home/polarvenezia/Downloads/Project-2D-starting-master/sampleCNF/largeSat.cnf";

    public static void main(String[] args) {
        System.out.println("Test");
        try{

            List<String> fileRead = readFileRegex(filePath);

            long started = System.nanoTime();
            DirectedGraph directedGraph = new DirectedGraph(fileRead);
            SAT2Solver sat2Solver = new SAT2Solver(directedGraph);
            int[] result = sat2Solver.solve();
            long time = System.nanoTime();
            long timeTaken= time - started;

            System.out.println("Time:" + timeTaken/1000000.0 + "ms");
            System.out.println("Result: " + Arrays.toString(result));

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    // read file using regex
    private static List<String> readFileRegex(String filename)
    {
        List<String> data = new ArrayList<String>();
        try
        {
            BufferedReader bReader = new BufferedReader(new FileReader(filename));
            String line;
            String rawData = "";
            while ((line = bReader.readLine()) != null)
            {
                rawData += line + " ";
            }

            // check cnf format
            Pattern cnf = Pattern.compile("(c\\s+.*?\\s+)(p cnf\\s+(\\d+\\s+\\d+))(\\s+(-?\\d+)(\\s+(-?\\d+))?\\s+0)*\\s+");
            Matcher cnfMatcher = cnf.matcher(rawData);
            if (!cnfMatcher.matches()) throw new Exception("None SAT format");
            data.add(cnfMatcher.group(1));
            data.add(cnfMatcher.group(2));

            // construct list of data and check 2-SAT format
            Pattern satData = Pattern.compile("(-?\\d+\\s+(-?\\d+\\s+)?)0");
            Matcher dataMatcher = satData.matcher(rawData);
            while (dataMatcher.find()){
                if (dataMatcher.groupCount() > 2) throw new Exception("Not 2-SAT format");
                data.add(dataMatcher.group(1));
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


    // old version readFile by readliness
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
