package ase;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

public class ProviderAgent extends EnhancedAgent {
    private List<Project> projects;
    ArrayList<Integer> ratings=new ArrayList<>();
    ProviderGui providerGui;
    private double rate = 0.0;
    private int credit = 1000;

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

    public void sendMessage(AID customer, String messageText, String projectName) {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
        message.setConversationId(Constants.CHAT);
        message.setContent(projectName + ":" + messageText);
        message.addReceiver(customer);
        send(message);
    }


    private class RecieveProposal extends CyclicBehaviour {
    	ProviderAgent myAgent;
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
                System.out.println("PRVD:" +msg.getPerformative() + " " + msg.getConversationId());
                if (msg.getPerformative() == ACLMessage.PROPOSE){
                    String content = msg.getContent();
                    reply = msg.createReply();
                    MessageGui msgGui = new MessageGui(myAgent, reply, msg, true);
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
                else if (msg.getConversationId() == Constants.PAYMENT)
                {
                    System.out.println("Recieving payment "+ msg.getContent());
                    int bid = Integer.parseInt(msg.getContent());
                    myAgent.addCredit(bid);
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

    public int getCredit() {
    	return credit;
    }
    public void addCredit(int x) {
        credit += x;
        providerGui.updateCredit();
    }
    public Provider getProvider(){
        return UserManagerAgent.getProvider(this.getLocalName().split(":")[1]);
    }
    public Boolean goPremium(){
        if(getCredit()<Constants.PREMIUM_PRICE){
            return false;
        }
        addCredit(-Constants.PREMIUM_PRICE);
        Provider p = getProvider();
        p.setPremium();
        providerGui.updatePremium();
        return true;
    }
    public void withdraw(){
        Provider p = getProvider();
        p.setRole(User.PROVIDER);
        providerGui.dispose();
        createAgent("Customer:" + p.getUsername(), "ase.CustomerAgent");
        takeDown();
    }
}

