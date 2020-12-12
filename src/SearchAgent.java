import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class SearchAgent extends Agent {
    private static provider[] providersList;

    @Override
    protected void setup() {
        System.out.println("Search Agent " + getAID().getName() + " is here!");
        addBehaviour(new ProvidersListRequestBehaviour());
    }


    public static class ProvidersListRequestBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();

            if (msg == null)
                return;

            System.out.println("Received a provider list request");
            String content = msg.getContent();
            if (content.startsWith("list")) {
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.INFORM);
                reply.setContent(provider.getStringFromList(providersList));
            }
        }
    }
}
