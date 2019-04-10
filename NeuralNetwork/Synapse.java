public class Synapse{

    private double weights;
    private Neuron first;
    private Neuron last;
    private double lastWeight;
    private double deltaOutput;

    public Synapse(){
        weights = (Randomizer.getRgen(201) / 100.0) - 1.0;
    }

    public void setConnection(Neuron a, Neuron b){
        first = a;
        last = b;
    }

    public void feedforward(double input){
        input *= weights;
        last.feedforward(input);
    }

    public void lastLearning(double error){
        /*double deltaWeight = first.getLastOutput() * last.getDeltaOutput(error);
        lastWeight = weights;
        weights += deltaWeight;*/
		double deltaWeight = -1*(activation(first.getLastInput()));
		lastWeight = weights;
        weights += deltaWeight;
    }

    public void learning(double hiddenDelta, double deltaOutput){
        double deltaWeight = hiddenDelta * deltaOutput * first.getLastInput();
        weights += deltaWeight;
    }

    public double getWeight(){
        return weights;
    }

    public double getLastWeight(){
        return lastWeight;
    }
    
    public double getHiddenDelta(){
        return lastWeight;
    }
	
	public double activation(double input){
		return  1 / (1 + Math.exp((-1  * input)));
	}
}
