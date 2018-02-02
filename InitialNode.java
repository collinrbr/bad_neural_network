public class InitialNode extends Node
{
    InitialNode(int lyrSz){
        super(lyrSz);
    }
    
    public void propagate(){
        for(int i = 0; i < forwardNodes.length; i++)
        {
            forwardNodes[i].accumulateValue(weight[i]*value);
        }
    }
}
