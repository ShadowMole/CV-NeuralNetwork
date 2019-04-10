import java.io.*;

public class Driver {

    public static void main(String args[]){
        String[] classes = new String[4];
        classes[0] = "Dog";
        classes[1] = "Cat";
        classes[2] = "Bird";
        classes[3] = "Dolphin";
        Network brain = new Network(64,4);
		int correct = 0;
        for(int times = 0; times < 10; times++) {
            //Get image
            //Get correct class as answer
            ClassLoader classLoader = Driver.class.getClassLoader();
            BufferedReader reader;
            for(int answer = 0; answer < classes.length; answer++) {
                for (int image = 1; image <= 5; image++) {
                    // System.out.println("Working Directory = " + System.getProperty("user.dir"));

                    // for Steven's workspace
                     String filename = "../Data/" + answer + "_" + image + ".txt";
                    // for Jatin's workspace
                   // String filename = "Data/" + answer + "_" + image + ".txt";

                   // File file = new File(classLoader.getResource(filename).getFile());
                    try {
                        // change back to filename
                        reader = new BufferedReader(new FileReader(filename));
                        try {
                            String s = reader.readLine();
                            String[] giantArray = s.split(",");
                            double max = 0;
                            double[] input = new double[giantArray.length];

                            for(int i = 0; i < giantArray.length; i++) {
                                input[i] = Double.parseDouble(giantArray[i]);
                                if (input[i] > max) {
                                    max = input[i];
                                }
                            }
                            int selection = brain.makeDecision(input, max);
							if(selection == answer){
								correct++;
							}
                            //Do error correctly
                            double error = brain.learn(answer);
								System.out.println("Iteration: " + (times) 
								+ " File Name: " + answer + " " + image 
								+ "\nGuess: " + classes[selection] 
								+ "\nAnswer: " + classes[answer] 
								+ "\nError: " + error + "\n");

                        }catch(IOException e){
                            e.printStackTrace();
                            System.out.println("io excep");

                        }
                    }catch(FileNotFoundException e)
                    {
                        e.printStackTrace();
                        System.out.println("file not found");

                    }
                }
            }
        }
    }
}
