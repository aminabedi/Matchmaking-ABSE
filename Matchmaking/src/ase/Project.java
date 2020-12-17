package ase;

import jade.lang.acl.ACLMessage;
import jade.core.AID;
import jade.tools.sniffer.Message;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String name;
    private String description;
    private int progress = 0;
    private int bid;
    private ArrayList<String> messagesHistory = new ArrayList<>();
    private AID provider;
    private AID customer;

    public Project(String name, String description, int bid, AID provider, AID customer) {
        this.name = name;
        this.bid = bid;
        this.description = description;
        this.provider = provider;
        this.customer = customer;
    }


    public void setName(String newName) {
        this.name = newName;
    }
    

    public ACLMessage convertToAclMessage() {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);

        message.setContent("\nProject = " + name + " Bid=" + bid);

        return message;
    }

    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
    	return this.description;
    }
    
    
    public String toString() {
    	return "" + name + ":" + description + ":" + bid; 
    }
    
    public String   getContract() {
    	return "Contract for project: " + toString();
    }
    
    public String getRejectionMessage(AID sender) {
    	return sender.getLocalName() + " has rejected " + toString();
    }
    
//    public static Project fromString(String s) {
//    	String c[] = s.split(":");
//    	Project p = new Project(c[0], c[1], Integer.parseInt(c[2]));
//    	return p;
//    }
    public boolean isFinal() {
        return progress == 100;
    }

    public int getProgress(){
        return progress;
    }

    public void progress(int progressPercentage){
        int permittedProgress = 100 - this.progress;
        if (progressPercentage < permittedProgress){
            progress += progressPercentage;
        }
    }

    public void chatUpdate(String chatMessage){
        messagesHistory.add(chatMessage);
    }

    public AID getProvider() {
        return provider;
    }
}

