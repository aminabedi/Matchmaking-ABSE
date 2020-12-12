package ase;

import jade.lang.acl.ACLMessage;
import jade.core.AID;
import jade.tools.sniffer.Message;

import java.util.ArrayList;
import java.util.List;

public class Project {
	
	private AID customer;
    private AID provider = null;
    
    private String name;
    private String description;
    private int progress = 0;
    private int bid;
    private ArrayList<InternalMessage> messages = new ArrayList<>();

    public Project(AID customer, String name, String description, int bid) {
    	this.customer = customer;
        this.name = name;
        this.bid = bid;
        this.description = description;
    }


    public void setName(String newName) {
        this.name = newName;
    }
    
    public void addMessage(InternalMessage msg) {
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
    	InternalMessage msg = new InternalMessage(provider, customer, c);
    	addMessage(msg);
    }

    public void sendMessageByCustomer(String c) {
    	InternalMessage msg = new InternalMessage(customer, provider, c);
    	addMessage(msg);
    }
    
    public String toString() {
    	return "" + customer + ":" + name + ":" + bid + ":" + (provider!=null?provider:"none"); 
    }
    
    public boolean isFinal() {
        return progress == 100;
    }

    public int getProgress(){
        return progress;
    }

    public List<InternalMessage> getMessages(){
        return messages;
    }
}

