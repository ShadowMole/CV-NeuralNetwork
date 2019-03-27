import java.util.ArrayList;

public class Network{

    private Neuron[] input;
    /*private Neuron[] first;
    private Neuron[] second;*/
    private Neuron[] last;
    private Neuron[] output;
    private double error;
    private double[] outputs;

    /**
     * Constructor for objects of class Network
     */
    public Network(int n, int x){
        input = new Neuron[n + 1];
        output = new Neuron[x];
        last = new Neuron[17];
        for(int i = 0; i < input.length - 1; i++){
            input[i] = new Neuron(x);
        }
        input[input.length - 1] = new ConstantNeuron(x)
        /*for(int i = 0; i < first.length; i++){
            first[i] = new Neuron(x);
        }
        for(int i = 0; i < second.length; i++){
            second[i] = new Neuron(x);
        }*/
        for(int i = 0; i < last.length - 1; i++){
            last[i] = new Neuron(x);
        }
        last[last.length - 1] = new ConstantNeuron(x);
        for(int i = 0; i < output.length; i++){
            output[i] = new Neuron(x);
            output[i].setImage(i);
        }
        outputs = new double[x];
        setConnections(n);
    }

    public void setConnections(int x){
        for(Neuron m : input){
            for(int i = 0; i < last.length - 1; i++){
                m.addConnection(x, last[i]);
            }
        }
        /*for(Neuron n : first){
            for(Neuron m : second){
                n.addConnection(x, m);
            }
        }
        for(Neuron n : second){
            for(Neuron m : last){
                n.addConnection(x, m);
            }
        }*/
        for(Neuron n : last){
            for(Neuron m : output){
                n.addConnection(x, m);
            }
        } 
    }

    public int makeDecision(double[] info, double max){
        for(int i = 0; i < info.length; i++){
            info[i] /= max;
        }
        for(int i = 0; i < info.length; i++){
            input[i].feedforward(info[i]);
        }
        for(Neuron n : input){
            n.fire();
        }
        /*for(Neuron n : first){
            n.fire();
        }
        for(Neuron n : second){
            n.fire();
        }*/
        for(Neuron n : last){
            n.fire();
        }
        for(int i = 0; i < output.length; i++){
            outputs[i] = output[i].selection();
        }
        return getSelection();
    }

    public void learn(int correct){
        error = calcError(correct);
        ArrayList<Double> hiddenDelta = new ArrayList<>();
        for(Neuron n : last){
            for(Synapse s : n.getConnections()){
                s.lastLearning(error);
                hiddenDelta.add(s.getHiddenDelta());
            }
        }
        double deltaOutput = 0;
        int i = 0;
        for(Neuron n : output){
            deltaOutput += n.getDeltaOutput(error);
            i++;
        }
        deltaOutput /= i;
        int count = 0;
        /*for(Neuron n : second){
            for(Synapse s : n.getConnections()){
                if(count == hiddenDelta.size()){
                    count = 0;
                }
                s.learning(hiddenDelta.get(count), deltaOutput);
                count++;
            }
        }
        count = 0;
        for(Neuron n : first){
            for(Synapse s : n.getConnections()){
                if(count == hiddenDelta.size()){
                    count = 0;
                }
                s.learning(hiddenDelta.get(count), deltaOutput);
                count++;
            }
        }
        count = 0;*/
        for(Neuron n : input){
            for(Synapse s : n.getConnections()){
                if(count == hiddenDelta.size()){
                    count = 0;
                }
                s.learning(hiddenDelta.get(count), deltaOutput);
                count++;
            }
        }
    }

    public double calcError(int correct){
        double sum = 0;
        for(int i = 0; i < outputs.length; i++){
            double diff;
            if(i == correct){
                diff = 1 - outputs[i];
            }else{
                diff = 0 - outputs[i];
            }
            sum += (.5 * diff * diff);
        }
        return sum;
    }

    public int getSelection(){
        int x = -1;
        for(int i = 0; i < outputs.length; i++){
            if(x == -1){
                x = i;
            }else if(outputs[i] > outputs[x]){
                x = i;
            }
        }
        return x;
    }

    public double[] getOutputs(){
        return outputs;
    }
}
