package ase;

import java.util.Set;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class CustomerAgent extends EnhancedAgent {
    Set<Project> projects;

    @Override
    protected void setup() {
        System.out.println("Hello! CustoemrAgent " + getAID().getName() + " is here!");
        Set<AID> providers = searchForService("project-provide");
        CustomerGui gui = new CustomerGui(this, providers);
        gui.showGui();
        addBehaviour(new MessageHandlingBehaviour(this));
        addBehaviour(new CyclicBehaviour() {
        	public void action() {
        		ACLMessage msg, reply=null;
    	        MessageTemplate template;

                //listening for project proposal
                template = MessageTemplate.MatchConversationId(Constants.PROJECT_REG);

                msg = myAgent.receive(template);
                
                if (msg != null){
                	String content = null;
                	Project project = Project.fromString(msg.getContent());
                	if(msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                		reply = new ACLMessage(ACLMessage.INFORM);
                		reply.setConversationId(Constants.CHAT);
                		reply.addReceiver(msg.getSender());
                		content = project.getContract();
                	}
                	else {
                		content = project.getRejectionMessage(msg.getSender());
                	}
                	System.out.println("" + msg.getSender().getLocalName() + " responded to the proposal for " + msg.getContent());
                    MessageGui msgGui = new MessageGui(myAgent,reply, content, false);
                }
        	}
        });
    }


    protected void sendProposal(Project p, AID provider) {
        ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
        message.setConversationId(Constants.PROJECT_REG);

        message.setContent(p.toString());
        System.out.println("Proposing "+ p.toString() + " to "+ provider.getLocalName());
        message.addReceiver(provider);
        send(message);
    }



}
