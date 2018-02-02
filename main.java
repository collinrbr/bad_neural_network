
public class main
{
    //TODO: make output node its own thing
    public static void main(String[] args){
        System.out.println("Starting----------------------------");
        Network n = new Network(4,6); //TODO: better inOut system
        n.addLayer(6,6);
        n.addLayer(6,4);
        n.addLayer(4,2);
        n.addLayer(2,2);
        n.setup();
        n.main();
        
        
        
        
    }
}
