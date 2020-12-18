package ase;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomerAgent extends EnhancedAgent {
    List<Project> projects;
    int currentNumber = 0;
    CustomerGui gui;

    @Override
    protected void setup() {
        System.out.println("Hello! CustomerAgent " + getAID().getName() + " is here!");
//        addSomeMockProjects();
        Set<AID> providers = searchForService("project-provide");
        projects = new ArrayList<>();
        gui = new CustomerGui(this, providers, projects);
        gui.showGui();
        addBehaviour(new MessageHandlingBehaviour(this));
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg, reply = null;
//                MessageTemplate template;

                //listening for project proposal
//                template = MessageTemplate.MatchConversationId(Constants.PROJECT_REG);

                msg = myAgent.receive();

                if (msg != null) {
                    if (msg.getConversationId() == Constants.PROJECT_REG){
                        String content = null;
                        String c[] = msg.getContent().split(":");
                        Project project = new Project(c[0], c[1], Integer.parseInt(c[2]), msg.getSender(), myAgent.getAID(),"");
                        if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                            reply = new ACLMessage(ACLMessage.INFORM);
                            reply.setConversationId(Constants.CHAT);
                            reply.addReceiver(msg.getSender());
                            content = project.getContract();
                            gui.addProject(project);
                        } else {
                            content = project.getRejectionMessage(msg.getSender());
                        }
                        System.out.println("" + msg.getSender().getLocalName() + " responded to the proposal for " + msg.getContent());
//                    MessageGui msgGui = new MessageGui(myAgent, reply, content, false);

                        // TODO: Instead of this, corresponded projectGUI would be opened.
                    }
                    else if (msg.getConversationId() == Constants.CHAT){

                    }
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

//    private void addSomeMockProjects() {
//        projects = new ArrayList<>();
//        projects.add(new Project("Project 1", "Description 1", 10));
//        projects.add(new Project("Project 2", "Description 2", 20));
//        projects.add(new Project("Project 3", "Description 3", 30));
//    }

    public void sendMessage(AID provider, String p, String projectName) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.setConversationId(Constants.CHAT);
        message.setContent(projectName + ":" + p);
        message.addReceiver(provider);
        send(message);
    }

    public void sendRating(AID provider, String rate) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.setConversationId(Constants.RATE);
        message.setContent(rate);
        message.addReceiver(provider);
        send(message);
    }
}
