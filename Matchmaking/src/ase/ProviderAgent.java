package ase;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

public class ProviderAgent extends EnhancedAgent {
    private List<Project> projects;
    ArrayList<Integer> ratings;
    ProviderGui providerGui;
    private double rate = 0.0;

    @Override
    protected void setup() {
    	System.out.println("SETTING UP A PROVIDER AGENT");
    	projects = new ArrayList<>();
//    	addSomeMockProjects();
        register("project-provide");
        addBehaviour(new RecieveProposal());
//        addBehaviour(new MessageHandlingBehaviour(this));
        providerGui = new ProviderGui(this,projects);
        providerGui.showGui();

    }

    public void sendMessage(AID provider, String messageText, String projectName) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.setConversationId(Constants.CHAT);
        message.setContent(projectName + ":" + messageText);
        message.addReceiver(provider);
        send(message);
    }


    private class RecieveProposal extends CyclicBehaviour {
    	
    	public RecieveProposal() {
    		super(ProviderAgent.this);
    		myAgent = ProviderAgent.this;
    	}

        @Override
        public void action() {
            ACLMessage msg, reply;
//	        MessageTemplate template;

            //listening for project proposal
//            template = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);

            msg = myAgent.receive();
            if (msg != null){
                if (msg.getPerformative() == ACLMessage.PROPOSE){
                    System.out.println("Received a proposal");
                    String content = msg.getContent();
                    System.out.println("Received a proposal:" + content);
                    reply = msg.createReply();
                    MessageGui msgGui = new MessageGui(myAgent, reply, content, true);
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
                else if (msg.getConversationId() == Constants.RATE)
                {
                    int rate = Integer.parseInt(msg.getContent());
                    ratings.add(rate);
                    update_rate_and_name();

                }
            }
//            else {
//                block();
//            }
        }

    }

    private void update_rate_and_name() {
        Integer sum = 0;
        if(!ratings.isEmpty()) {
            for (Integer mark : ratings) {
                sum += mark;
            }
            rate = sum.doubleValue() / ratings.size();
        }
        rate = sum;
        getAID().setLocalName(getLocalName().split("\\(")[0] + Double.toString(rate));
        System.out.println("changed rate");
    }

//    private void addSomeMockProjects() {
//        projects = new ArrayList<>();
//        projects.add(new Project("Project 1", "Description 1", 10));
//        projects.add(new Project("Project 2", "Description 2", 20));
//        projects.add(new Project("Project 3", "Description 3", 30));
//    }

}
