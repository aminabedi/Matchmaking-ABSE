package ase;

import ase.Project;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Set;

public class CustomerAgent extends Agent {
    Set<Project> projects;

    @Override
    protected void setup() {
        System.out.println("Hello! CustoemrAgent " + getAID().getName() + " is here!");
    }

    @Override
    protected void takeDown() {
        System.out.println("CustomerAgent " + getAID().getName() + " terminated!");
    }

    private void sendProposal(String text) {
        ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);

        message.setContent("Project: " + text + "\n");
        AID[] projectAgents = AgentFinder.findAgentByService(this, "ProjectAgent");
        for (AID project_manager : projectAgents) {
            message.addReceiver(project_manager);
        }
        send(message);
    }

    public static class RecieveMessage extends CyclicBehaviour {

        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();

            if (msg != null){
                System.out.println("Received a message");
                String content = msg.getContent();
                int performative = msg.getPerformative();
                if (performative == ACLMessage.ACCEPT_PROPOSAL){
                    System.out.println("Project has been accepted.");
                }
                else if (performative == ACLMessage.REJECT_PROPOSAL){
                    System.out.println("Project has been rejected.");
                }
            }
            else {
                block();
            }
        }

    }

}
