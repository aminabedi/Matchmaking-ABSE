package ase;

import jade.lang.acl.ACLMessage;
import jade.core.AID;
import java.util.ArrayList;

public class Project {
	private AID customer;
    private AID provider;
    
    private String name;
    private int progress = 0;
    private int bid;
    private ArrayList<Message> messages = new ArrayList<>();

    public Project(AID customer, String name, int bid) {
    	this.customer = customer;
        this.name = name;
        this.bid = bid;
    }


    public void setName(String newName) {
        this.name = newName;
    }
    
    public void addMessage(Message msg) {
    	messages.add(msg);
    }


    public ACLMessage convertToAclMessage() {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);

        message.setContent("\nProject = " + name + " Bid=" + bid);

        return message;
    }

    public String getName() {
        return this.name;
    }
    
    public void setProvider(AID p) {
    	this.provider = p;
    }
    
    public AID getProvider() {
    	return this.provider;
    }
    
    public AID getCustomer() {
    	return this.customer;
    }
    
    public void sendMessageByProvider(String c) {
    	Message msg = new Message(provider, customer, c);
    	addMessage(msg);
    }

    public void sendMessageByCustomer(String c) {
    	Message msg = new Message(customer, provider, c);
    	addMessage(msg);
    }
    
    public boolean isFinal() {
        return progress == 100;
    }
}

