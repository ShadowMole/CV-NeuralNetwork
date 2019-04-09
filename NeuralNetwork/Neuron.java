import java.util.ArrayList;
public class Neuron{

    protected double sums;
    protected ArrayList<Synapse> connections;
    protected int selection;
    protected double bias;
    protected double lastInput;
    protected double lastOutput;
    protected int image;

    public Neuron(int x){
        connections = new ArrayList<>();
        selection = x - 1;
        bias = (Randomizer.getRgen(101) / 100.0);
    }

    public void addConnection(int x, Neuron n){
        Synapse s = new Synapse();
        s.setConnection(this, n);
        connections.add(s);
    }

    public void feedforward(double input){
        sums += input;    
    }

    public void fire(){
        lastInput = sums;
        activation();
        for(Synapse c : connections){
            c.feedforward(sums);
        }
        sums = 0.0;
    }

    public void activation(){            
        sums = 1 / (1 + Math.exp((-1  * sums)));
    }

    public double selection(){
        lastInput = sums;
        activation();
        lastOutput = sums;
        double answer = sums;
        sums = 0.0;
        return answer;
    }

    public double getLastInput(){
        return lastInput;
    }

    public double primeActivation(){
        return (Math.exp(lastInput)) / ((Math.exp(lastInput) + 1) * (Math.exp(lastInput) + 1));
    }

    public double getLastOutput(){
        return lastOutput;
    }

    public ArrayList<Synapse> getConnections(){
        return connections;
    }

    public double getDeltaOutput(double error){
        return error * primeActivation();
    }

    public void setImage(int i){
        image = i;
    }

    public int getImage(){
        return image;
    }
}