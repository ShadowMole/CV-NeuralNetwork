public class Driver {

    public static void main(String args[]){
        String[] classes = new String[4];
        classes[0] = "Dog";
        classes[1] = "Cat";
        classes[2] = "Horse";
        classes[3] = "Llama";
        Network brain = new Network(64,4);
        while(true) {
            //Get image
            //Get correct class as answer
            int answer;
            //Give to Matlab and get giantArray and max
            double[] giantArray;
            double max;
            int selection = brain.makeDecision(giantArray, max);
            System.out.println("Guess: " + classes[selection] + "\nAnswer: " + classes[answer]);
            //Do error correctly
            double error;
            brain.learn(answer);
        }
    }
}
