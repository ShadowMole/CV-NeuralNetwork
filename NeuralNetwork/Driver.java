import com.mathworks.engine.*;
public class Driver {

    public static void main(String args[]){
        String[] classes = new String[4];
        classes[0] = "Dog";
        classes[1] = "Cat";
        classes[2] = "Bird";
        classes[3] = "Dolphin";
        Network brain = new Network(64,4);
        MatlabEngine eng = MatlabEngine.startMatlab();
        while(true) {
            //Get image
            //Get correct class as answer
            for(int answer = 0; answer < classes.length; answer++) {
                for (int image = 1; image < 5; image++) {
                    String file = "../Images/" + answer + "_" + image + ".png";
                    eng.eval("cd '../ImageProcessing'");
                    double[][] giantArray =  eng.feval("HW4", file);;
                    double max = 0;
                    double[] input = new double[giantArray.length * giantArray[0].length];
                    for(int i = 0, k = 0; i < giantArray.length; i++) {
                        for (int j = 0; j < giantArray[i].length; j++, k++) {
                            if (giantArray[i][j] > max) {
                                max = giantArray[i][j];
                            }
                            input[k] = giantArray[i][j];
                        }
                    }
                    int selection = brain.makeDecision(input, max);
                    System.out.println("Guess: " + classes[selection] + "\nAnswer: " + classes[answer]);
                    //Do error correctly
                    double error;
                    brain.learn(answer);
                }
            }
        }
    }
}
