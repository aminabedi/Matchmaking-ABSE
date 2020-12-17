package ase;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProviderAgent extends EnhancedAgent {
    private List<Project> projects;
    int rating;
    ProviderGui providerGui;

    @Override
    protected void setup() {
    	System.out.println("SETTING UP A PROVIDER AGENT");
    	projects = new ArrayList<>();
//    	addSomeMockProjects();
        register("project-provide");
        addBehaviour(new RecieveProposal());
        addBehaviour(new MessageHandlingBehaviour(this));
        providerGui = new ProviderGui(this,projects);
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
                // TODO: Instead of this, corresponded projectGUI would be opened.
            }
//            else {
//                block();
//            }
        }

    }

//    private void addSomeMockProjects() {
//        projects = new ArrayList<>();
//        projects.add(new Project("Project 1", "Description 1", 10));
//        projects.add(new Project("Project 2", "Description 2", 20));
//        projects.add(new Project("Project 3", "Description 3", 30));
//    }

}
