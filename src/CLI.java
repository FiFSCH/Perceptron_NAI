import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class CLI {
    private static boolean finish = true;
    protected static int flag = 0;

    protected static void Menu() {
        DecimalFormatSymbols symbol = new DecimalFormatSymbols();
        symbol.setDecimalSeparator('.');
        DecimalFormat format = new DecimalFormat("0.#");
        format.setDecimalFormatSymbols(symbol);
        Scanner scan = new Scanner(System.in);

        while ( finish ) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("\nTraining set location: " + Main.trainingPath + "\nTesting set location: " + Main.testingPath
                    + "\nLearning rate: " + Main.learningRate + "\nVector Dimensionality: " + Main.vectorDimensionality
                    + "\nFound Decision attributes: " + Attribute.decisionAttributes + "\n");
            System.out.println("""
                    What would you like to do?
                    1. Test program using testing set.
                    2. Provide your own vector.
                    3. exit.
                    Enter number from 1-3:\s""");
            int input = scan.nextInt();
            flag = 0;
            switch (input) {
                case 1 -> {
                    System.out.println("/=====================\nTesting set:");
                    int correct = 0;
                    for (Attribute test : Main.testingAttributes) {
                        int output = Attribute.output(test);
                        if (Integer.parseInt(test.decision) == output) {
                            correct++;
                        }
                    }
                    System.out.println("Correct: " + correct + "/" + Main.testingAttributes.size());
                }
                case 2 -> {
                    flag = 1;
                    ArrayList<Float> tmp = new ArrayList<>();
                    for (int i = 0; i < Main.vectorDimensionality; i++) {
                        System.out.println("Enter a float number: ");
                        try {
                            tmp.add(format.parse(scan.next()).floatValue());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    Attribute userAttribute = new Attribute(tmp, null);
                    int output = Attribute.output(userAttribute);
                    System.out.println("/=====================\nPerceptron output: " + output);
                }
                case 3 -> {
                    scan.close();
                    finish = false;
                    System.exit(1);
                }
                default -> throw new IllegalArgumentException("Wrong input!");
            }
        }
    }
}