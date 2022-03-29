import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Attribute {

    protected static ArrayList<String> decisionAttributes = new ArrayList<>();
    protected static ArrayList<Float> weights = new ArrayList<>();
    private static final Random random = new Random();
    protected ArrayList<Float> vector;
    protected String decision;

    public Attribute(ArrayList<Float> vector, String decision) {
        this.vector = vector;
        if (decision.equalsIgnoreCase("Iris-versicolor")) //Iris-versicolor == 1
            this.decision = "1";
        else if (decision.equalsIgnoreCase("Iris-virginica")) // Iris-virginica == 0
            this.decision = "0";
        else
            this.decision = decision;
    }

    protected static void initializeWeights() {
        for (int i = 0; i < Main.vectorDimensionality; i++) {
            weights.add(random.nextFloat());
        }
    }

    protected static void insertAttributes(String path, ArrayList<Attribute> whichVectorsList, int flag) {
        try {

            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();

            while ( line != null ) {
                String[] whichDataSet = line.split(",");
                Main.vectorDimensionality = whichDataSet.length - 1;
                ArrayList<Float> tmp = new ArrayList<>();

                for (int i = 0; i < whichDataSet.length - 1; i++) {
                    tmp.add(Float.parseFloat(whichDataSet[i]));
                }
                if (flag == 1) {
                    decisionAttributes.add(whichDataSet[whichDataSet.length - 1]);
                    decisionAttributes = decisionAttributes.stream().distinct().collect(Collectors.toCollection(ArrayList<String>::new));
                }
                whichVectorsList.add(new Attribute(tmp, whichDataSet[whichDataSet.length - 1]));
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int output(Attribute attribute) {

        float output = 0.0f;
        for (int i = 0; i < attribute.vector.size(); i++) {
            output = attribute.vector.get(i) * weights.get(i);
        }
        if (output >= Main.threshold) {
            return 1;
        }
        return 0;
    }

    public static float updateWeightsAndTheta(int d, int y, Attribute a) {
        int result = d - y;
        ArrayList<Float> tmp = new ArrayList<>();
        for (int i = 0; i < a.vector.size(); i++) {
            tmp.add(weights.get(i) + (result * Main.learningRate * a.vector.get(i)));
        }
        weights = tmp;
        return Main.threshold - result * Main.learningRate;
    }
}