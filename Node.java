import java.util.Random;
//This node is linear(value recieved from previous node = value obtained)
public class Node
{
    // instance variables - replace the example below with your own
    Node[] forwardNodes;
    double[] weight;
    double[] prevWeight;
    double value;
    double deltaValue; //initialValue is target on output node
    double learningConst;
    
    

    public Node(int nxtlyrSz)
    {
        //Assume each layer is intially the same size
        forwardNodes = new Node[nxtlyrSz];
        weight = new double[nxtlyrSz];
        prevWeight = new double[nxtlyrSz];
        value = 0;
        learningConst = .1;
        
        Random rnd = new Random();
            for(int i = 0; i < nxtlyrSz; i++){
                //weight[i] = Math.abs(rnd.nextGaussian())+0.00001;
                weight[i] = (rnd.nextInt(1000) + 1) / 1000;
        }
    }

    public void propagate(){
        value = sigmoid(value);
        for(int i = 0; i < forwardNodes.length; i++)
        {
            forwardNodes[i].accumulateValue(weight[i]*value);
        }
    }
    
    //TODO: rename this to something more appropriate; Like tuneWeight
    public void backpropagate(){
        for(int i = 0; i < weight.length; i++){
               prevWeight[i]=weight[i];
               weight[i]=weight[i]+learningConst *this.value * forwardNodes[i].dEdW();
        }
    }
    
    public double dEdW(){
        if(forwardNodes[0] == null){//TODO: fix this mess
            return deltaValue * dSigDx(value);
        }
        double temp = 0;
        for(int i = 0; i < forwardNodes.length; i++){
            temp = temp + prevWeight[i] * forwardNodes[i].dEdW();
        }
        return temp * dSigDx(value);
    }
    
    public double sigmoid(double x){
        return 1/(1+Math.exp(-1*x));
    }
    
    public double dSigDx(double x){
        return (sigmoid(x)*(1-sigmoid(x)));
    }
    
    public void accumulateValue(double x){
        value = value + x;
    }
    
    public void resetValues(){
        value = 0;
        deltaValue = 0;
        for(int i = 0; i < prevWeight.length; i++){
            prevWeight[i] = 0;
        }
    }
    
    public void setForwardNodes(Node[] nodes){
        if(nodes.length != forwardNodes.length)
        {
            System.out.println("Layer size does not match forwardNodes size");
            System.exit(0);
        }
        for(int i = 0; i < nodes.length; i++){
            forwardNodes[i] = nodes[i];
        }
    }
    
}
