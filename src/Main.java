import java.util.ArrayList;

public class Main {

    protected static String trainingPath;
    protected static String testingPath;
    protected static int vectorDimensionality, maxIterations = 30;
    protected static ArrayList<Attribute> trainingAttributes = new ArrayList<>();
    protected static ArrayList<Attribute> testingAttributes = new ArrayList<>();
    protected static float learningRate, threshold = 1;

    public static void main(String[] args) {
        if (args.length == 3) {
            trainingPath = args[0];
            testingPath = args[1];
            try {
                learningRate = Float.parseFloat(args[2]);
                if (learningRate < 0 || learningRate > 1)
                    throw new IllegalArgumentException();
            } catch (Exception e) {
                System.out.println("Wrong learning rate value.");
                System.exit(-1);
            }
        } else {
            throw new IllegalArgumentException("Wrong input parameters.");
        }
        Attribute.insertAttributes(trainingPath, trainingAttributes, 1);
        Attribute.insertAttributes(testingPath, testingAttributes, 0);
        Attribute.initializeWeights();

        for (int i = 0; i < maxIterations; i++) {
            for (Attribute a : trainingAttributes) {
                int d;
                try {
                    d = Integer.parseInt(a.decision);
                } catch (Exception e) {
                    d = Attribute.decisionAttributes.indexOf(a.decision);
                }

                int y = Attribute.output(a);

                threshold = Attribute.updateWeightsAndTheta(d, y, a);
            }
        }
        CLI.Menu();
    }
}