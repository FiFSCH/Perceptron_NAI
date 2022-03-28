import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Attribute {

    protected static ArrayList<String> decisionAttributes = new ArrayList<>();
    protected HashMap<ArrayList<Float>, String> attribute;
    protected String decision;

    public Attribute(HashMap<ArrayList<Float>, String> attribute, String decision) {
        this.attribute = attribute;
        this.decision = decision;
    }

    protected static void insertAttributes(String path, ArrayList<Attribute> whichVectorsList, int flag) {
        try {

            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();

            while ( line != null ) {
                String[] whichDataSet = line.split(",");
                System.out.println(whichDataSet.length);
                ArrayList<Float> tmp = new ArrayList<>();
                HashMap<ArrayList<Float>, String> tmpMap = new HashMap<>();
                Main.vectorDimensionality = whichDataSet.length - 1;
                for (int i = 0; i < whichDataSet.length - 1; i++) {
                    tmp.add(Float.parseFloat(whichDataSet[i]));
                }
                tmpMap.put(tmp, whichDataSet[whichDataSet.length - 1]);
                if (flag == 1) {
                    decisionAttributes.add(whichDataSet[whichDataSet.length - 1]);
                    decisionAttributes = decisionAttributes.stream().distinct().collect(Collectors.toCollection(ArrayList<String>::new));
                }
                whichVectorsList.add(new Attribute(tmpMap, whichDataSet[whichDataSet.length - 1]));
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
