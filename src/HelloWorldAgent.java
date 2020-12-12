import jade.core.Agent;

public class HelloWorldAgent extends Agent {
    protected void setup() {
        System.out.println("Hello! Agent " + getAID().getName() + " is here!");
    }
}
