public class Layer
{
    Node[] nodes;
    
    //make hidden layer
    public Layer(int lyrSz,int nxtLyrSz)
    {
        nodes = new Node[lyrSz];
        for(int i = 0; i < nodes.length; i++){
            nodes[i] = new Node(nxtLyrSz);
        }
    }
    
    
    //make in or out layer
    public Layer(int lyrSz, boolean isInput)
    {
        if(isInput)
            nodes = new InitialNode[lyrSz];
        else
            nodes = new Node[lyrSz];
    }
}
