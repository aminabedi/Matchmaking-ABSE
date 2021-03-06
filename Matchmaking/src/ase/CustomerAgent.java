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
    private int credit = 1000;
    @Override
    protected void setup() {
        System.out.println("Hello! CustomerAgent " + getAID().getName() + " is here!");
        Set<AID> providers = searchForService("project-provide");
        projects = new ArrayList<>();
        gui = new CustomerGui(this, providers, projects);
        gui.showGui();
//        addBehaviour(new MessageHandlingBehaviour(this));
        addBehaviour(new CyclicBehaviour() {
            public void action() {
                ACLMessage msg, reply = null;

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
                        String projectName = msg.getContent().split(":")[0];
                        String chatMessage = msg.getContent().split(":")[1];
                        for (Project project : projects){
                            if (project.getName().equals(projectName)){
                                project.chatUpdate(chatMessage);
                            }
                        }
                    }
                    else if (msg.getConversationId() == Constants.PROGRESS){
                        String projectName = msg.getContent().split(":")[0];
                        String progressText = msg.getContent().split(":")[1];
                        int progress = Integer.parseInt(progressText);
                        for (Project project : projects){
                            if (project.getName().equals(projectName)){
                                project.progress(progress);
                            }
                        }
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

    public void sendMessage(AID provider, String p, String projectName, String conversationId) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.setConversationId(conversationId);
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
    
    public void markProjectDone(Project project) {
        System.out.println("MARKING DONE " + project.getProvider().getLocalName());
        
    	ACLMessage message = new ACLMessage(ACLMessage.CONFIRM);
        message.setConversationId(Constants.PAYMENT);
        message.setContent(project.getName()+":"+70*project.getBid()/100);
        message.addReceiver(project.getProvider());
        send(message);
        project.setDone();
        for(Project p: projects){
            if(p.getName().equals(project.getName())){
                System.out.println("FOUND DONE PROJECT");
                p.setDone();
            }
        }
        gui.updateProjects(projects);
    	addCredit(-1 * project.getBid());
    }
    public int getCredit() {
    	return credit;
    }
    public void addCredit(int x) {
        credit += x;
        gui.updateCredit();
    	
    }
}
