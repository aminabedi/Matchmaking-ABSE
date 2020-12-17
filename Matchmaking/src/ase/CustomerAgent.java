package ase;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomerAgent extends EnhancedAgent {
    List<Project> projects;

    @Override
    protected void setup() {
        System.out.println("Hello! CustomerAgent " + getAID().getName() + " is here!");
        addSomeMockProjects();
        Set<AID> providers = searchForService("project-provide");
        CustomerGui gui = new CustomerGui(this, providers, projects);
        gui.showGui();
        addBehaviour(new MessageHandlingBehaviour(this));
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg, reply = null;
                MessageTemplate template;

                //listening for project proposal
                template = MessageTemplate.MatchConversationId(Constants.PROJECT_REG);

                msg = myAgent.receive(template);

                if (msg != null) {
                    String content = null;
                    Project project = Project.fromString(msg.getContent());
                    if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                        reply = new ACLMessage(ACLMessage.INFORM);
                        reply.setConversationId(Constants.CHAT);
                        reply.addReceiver(msg.getSender());
                        content = project.getContract();
                    } else {
                        content = project.getRejectionMessage(msg.getSender());
                    }
                    System.out.println("" + msg.getSender().getLocalName() + " responded to the proposal for " + msg.getContent());
                    MessageGui msgGui = new MessageGui(myAgent, reply, content, false);
                }
            }
        });
    }


    protected void sendProposal(Project p, AID provider) {
        ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
        message.setConversationId(Constants.PROJECT_REG);

        message.setContent(p.toString());
        System.out.println("Proposing " + p.toString() + " to " + provider.getLocalName());
        message.addReceiver(provider);
        send(message);
    }

    private void addSomeMockProjects() {
        projects = new ArrayList<>();
        projects.add(new Project("Project 1", "Description 1", 10));
        projects.add(new Project("Project 2", "Description 2", 20));
        projects.add(new Project("Project 3", "Description 3", 30));
    }
}
