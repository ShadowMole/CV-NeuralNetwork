public class ConstantNeuron extends Neuron{

    public ConstantNeuron(int x){
        super(x);
        lastInput = 1;
        lastOutput = 1;
    }

    @Override
    public void fire(){
        for(Synapse c : connections){
            c.feedforward(1);
        }
    }
}
