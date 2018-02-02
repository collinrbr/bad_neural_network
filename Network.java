import java.util.Random;
public class Network
{
    //double[][] inOut = {{0,0,0,0,0,0},{0,0,1,0,0,1},{0,1,0,0,1,0},{0,1,1,0,1,1},{1,0,0,1,0,0},{1,0,1,1,0,1},{1,1,0,1,1,0},{1,1,1,1,1,1}};
    //double[][] inOut = {{0,0,1,0,0,1},{0,0,1,0,0,1},{0,1,1,0,1,1},{0,1,1,0,1,1},{1,0,1,1,0,1},{1,0,1,1,0,1},{1,1,1,1,1,1},{1,1,1,1,1,1}};
    //double[][] inOut = {{0,0,0,0,0,0},{0,0,1,1,0,0},{0,1,0,0,1,1},{0,1,1,1,1,1},{1,0,0,0,0,1},{1,0,1,1,1,0},{1,1,0,0,1,0},{1,1,1,1,0,1}};
    //double[][] inOut = {{0,0,0,0,0,0},{0,0,1,0,1,0},{0,1,0,0,1,1},{0,1,1,1,0,0},{1,0,0,1,0,1},{1,0,1,1,1,0},{1,1,0,1,1,1},{1,1,1,0,0,0}};
    //double[][] inOut = {{0,0,0,1,1,1},{0,0,1,1,1,1},{0,1,0,1,1,1},{0,1,1,1,1,1},{1,0,0,1,1,1},{1,0,1,1,1,1},{1,1,0,1,1,1},{1,1,1,1,1,1}};
    //double[][] inOut = {{0,0,0,0,0,0},{0,0,1,0,0,0},{0,1,0,0,0,0},{0,1,1,0,0,0},{1,0,0,0,0,0},{1,0,1,0,0,0},{1,1,0,0,0,0},{1,1,1,0,0,0}};
    //double[][] inOut = {{0,0,0,0,0,0},{0,0,1,0,0,0},{0,1,0,0,0,0},{0,1,1,0,0,0},{1,0,0,0,0,0},{1,0,1,0,0,0},{1,1,0,0,0,0},{1,1,1,1,1,1}};
    //double[][] inOut = {{0,0,0,0}, {0,0,1,1}, {0,1,0,1}, {0,1,1,2},{1,0,0,1},{1,0,1,2},{1,1,0,1},{1,1,1,3}};
    double[][] inOut = {{1,0,0,0,0,1,0,1},{0,1,1,1,1,0,1,0}};
    Layer[] layers;
    
    private int layerInitIndex = 0;
    private int numInputs = 0;
    
    //TODO: make better inOut system
    //Square Network Contructor
    public Network(int hiddenlyrs, int hiddenlyrSz, int inoutLyrSz)
    {
        
        layers = new Layer[hiddenlyrs+2];
        for(int i = 1; i <= hiddenlyrs-1; i++){//nxtlyrsize of last hidden node is uniqque
            layers[i] = new Layer(hiddenlyrSz, hiddenlyrSz);
        }
        //make inout layers
        layers[0] = new Layer(inoutLyrSz, hiddenlyrSz);
        layers[hiddenlyrs] = new Layer(hiddenlyrSz, inoutLyrSz);
        layers[hiddenlyrs+1] = new Layer(inoutLyrSz, inoutLyrSz);
    }
    
    public Network(int totalLayers, int nmIn){
        layers = new Layer[totalLayers];
        numInputs = nmIn;
    }
    
    
    
    //TODO: use arraylists and this function to make a dynamically sized network
    public void addLayer(int sz,int nxtSz){
        layers[layerInitIndex] = new Layer(sz, nxtSz);
        layerInitIndex++;
    }
    
    //connects nodes
    public void setup(){
        for(int l = 0; l < layers.length-1; l++){//length-1 b/c dont want to connect final nodes
            for(int i = 0; i < layers[l].nodes.length; i++){
                for(int n = 0; n < layers[l+1].nodes.length; n++){// TODO: loop through next layer size
                    layers[l].nodes[i].forwardNodes[n] = layers[l+1].nodes[n];
                }
            }
        }
    }
    
    public void main(){
        Random rnd = new Random();
        
        
        //for(int tests = 0; tests < 10000000; tests++)
        int tests = 0;
        int t = 0;
        double sigma[] = new double[inOut[0].length-numInputs];//TODO: make termination system
        int inputIndex = 0;
        while(true)
        {
            //figure out random input
            if(tests % 100 == 0)
                inputIndex = rnd.nextInt(inOut.length);
            
            
            tests++;
            //set random input. output will be used later to find deltaValues
            
            //set input
            for(int i = 0; i < layers[0].nodes.length; i++){
                layers[0].nodes[i].value = inOut[inputIndex][i];
            }
            
            //propagate
            for(int l = 0; l < layers.length-1; l++){
                for(int i = 0; i < layers[l].nodes.length; i++){
                    layers[l].nodes[i].propagate();
                }
            }
            if(tests % 100000 == 0){
                System.out.println("test: " + tests + getLine1(inputIndex));
                System.out.println(getLine2());
            }
            
            
           
            for(int i = 0; i < sigma.length; i++){
                sigma[i] = inOut[inputIndex][numInputs+i] - layers[layers.length-1].nodes[i].value;
                layers[layers.length-1].nodes[i].deltaValue = sigma[i];//set delta for backpropagate
                                                                       //target - calc
            }
            
            double s = .1; //spread limit
            //calculate total spread
            double temp = 0;
            for(int i = 0; i < sigma.length; i++){
                temp = temp + sigma[i]*sigma[i];
            }
            if(temp < s)
            {
                t = t + 1;
            }
            else{
                t=0;
            }
            
            
            for(int l = layers.length-2; l >= 0; l--){//TODO: seperate output node from hidden nodes
                for(int i = 0; i < layers[l].nodes.length; i++){
                    layers[l].nodes[i].backpropagate();
                }
            }
            
            for(int l = 0; l < layers.length; l++){
                for(int i = 0; i < layers[l].nodes.length; i++){
                    layers[l].nodes[i].resetValues();
                }
            }
            
            if(t >= 1000)
                break;
        }
        
        
        //TODO: show all input output comb
        
        for(int test = 0; test < inOut.length; test++){
            //figure out random input
            inputIndex = test;
            //set random input. output will be used later to find deltaValues
            
            //set input
            for(int i = 0; i < layers[0].nodes.length; i++){
                layers[0].nodes[i].value = inOut[inputIndex][i];
            }
            
            //propagate
            for(int l = 0; l < layers.length-1; l++){
                for(int i = 0; i < layers[l].nodes.length; i++){
                    layers[l].nodes[i].propagate();
                }
            }
            System.out.println("test: " + test + getLine1(inputIndex));
            System.out.println(getLine2());
            
            for(int l = 0; l < layers.length; l++){
                for(int i = 0; i < layers[l].nodes.length; i++){
                    layers[l].nodes[i].resetValues();
                }
            }
        }
        System.out.println("Total tests: " + tests);
   }
    
   public String getLine1(int inputIndex){
         String temp = " in: ";
         for(int i = 0; i < numInputs; i++){
             temp = temp + inOut[inputIndex][i] + " ";
         }
         temp = temp + " out: ";
         for(int i = numInputs; i < inOut[inputIndex].length; i++){
             temp = temp + inOut[inputIndex][i] + " ";
         }
         return temp;
   }
   
   public String getLine2(){
         String temp = "calculated out: ";
         for(int i = 0; i < inOut[0].length - numInputs; i++){
             temp = temp + layers[layers.length-1].nodes[i].value + " ";
         }
         return temp;
   }
}
