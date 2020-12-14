package ase;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Set;

public class ProviderAgent extends EnhancedAgent {
    private Set<Project> projects;
    int rating;
    

    @Override
    protected void setup() {
    	System.out.println("SETTING UP A PROVIDER AGENT");
        register("project-provide");
        addBehaviour(new RecieveProposal());
        addBehaviour(new MessageHandlingBehaviour(this));
        ProviderGui providerGui = new ProviderGui(this);
        providerGui.showGui();
    }
    
  

    private class RecieveProposal extends CyclicBehaviour {
    	
    	public RecieveProposal() {
    		super(ProviderAgent.this);
    		myAgent = ProviderAgent.this;
    	}

        @Override
        public void action() {
            ACLMessage msg, reply;
	        MessageTemplate template;

            //listening for project proposal
            template = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);

            msg = myAgent.receive(template);
            if (msg != null){
                System.out.println("Received a proposal");
                String content = msg.getContent();
                System.out.println("Received a proposal:" + content);
                reply = msg.createReply();
                MessageGui msgGui = new MessageGui(myAgent, reply, content, true);
                
            }
//            else {
//                block();
//            }
        }

    }

}
