package ase;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.Set;

public class ProviderAgent extends EnhancedAgent {
    private Set<Project> projects;
    int rating;
    
//    public ProviderAgent(User user) {
//    	
//    }

    @Override
    protected void setup() {
    	System.out.println("SETTING UP A PROVIDER AGENT");
        register("project-provide");
        addBehaviour(new RecieveMessage());
    }

    @Override
    protected void takeDown() {
        System.out.println("CustomerAgent " + getAID().getName() + " terminated!");
    }

    private class RecieveMessage extends CyclicBehaviour {

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
