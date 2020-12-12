package ase;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.Set;

public class ProviderAgent extends Agent {
    private Set<Project> projects;
    int rating;

    @Override
    protected void setup() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Providing-project");
        sd.setName("Jade-Matchmaking");
        dfd.addServices(sd);
        try {
            DFService.register(this,dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }

    @Override
    protected void takeDown() {
        System.out.println("CustomerAgent " + getAID().getName() + " terminated!");
    }

    public static class RecieveMessage extends CyclicBehaviour {

        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();

            if (msg != null){
                System.out.println("Received a message");
                String content = msg.getContent();
                int performative = msg.getPerformative();

                if (performative == 7){
                    // It's a proposal.
                    ACLMessage reply = msg.createReply();
                    boolean decision = decide();
                    if (decision){
                        reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    }
                    else {
                        reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                    }
                }

            }
            else {
                block();
            }
        }

    }

    private static boolean decide() {
        return true;
        //TODO GUI
    }
}
