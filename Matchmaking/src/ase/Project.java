package ase;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

public class Project {
    private String name;
    private String description;
    private int progress = 0;
    private int bid;
    private ArrayList<String> messagesHistory = new ArrayList<>();
    private AID provider;
    private AID customer;
    private String deadline;
    private ProjectDetailGui projectDetailGui;
    private boolean done = false;

    public Project(String name, String description, int bid, AID provider, AID customer, String deadline) {
        this.name = name;
        this.bid = bid;
        this.description = description;
        this.provider = provider;
        this.customer = customer;
        this.deadline = deadline;
    }

    public void setDone() {
        done = true;
    }

    public void setName(String newName) {
        this.name = newName;
    }


    public ArrayList<String> getMessagesHistory() {
        return messagesHistory;
    }

    public ACLMessage convertToAclMessage() {
        ACLMessage message = new ACLMessage(ACLMessage.INFORM);

        message.setContent("\nProject = " + name + " Bid=" + bid);

        return message;
    }

    public String getName() {
        if (done) {
            return "" + name + " (done)";
        }
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }


    public String toString() {
        if (done) {
            return "" + name + " (done)";
        }
        return "" + name + ":" + description + ":" + bid + ":" + deadline;
    }

    public String getContract() {
        return "Contract for project: " + toString();
    }

    public String getRejectionMessage(AID sender) {
        return sender.getLocalName() + " has rejected " + toString();
    }


    public boolean isFinal() {
        return progress == 100;
    }

    public int getProgress() {
        return progress;
    }

    public void progress(int progressPercentage) {
        int permittedProgress = 100 - this.progress;
        if (progressPercentage < permittedProgress) {
            progress += progressPercentage;
        }
        if (projectDetailGui != null) {
            projectDetailGui.updateRightLabel(this.getName(), this.getDescription(), this.getProgress(), this.getMessagesHistory());
        }
    }

    public void chatUpdate(String chatMessage) {
        messagesHistory.add(chatMessage);
        if (projectDetailGui != null) {
            projectDetailGui.updateRightLabel(this.getName(), this.getDescription(), this.getProgress(), this.getMessagesHistory());
        }
    }

    public int getBid() {
        return bid;
    }

    public AID getProvider() {
        return provider;
    }

    public AID getCustomer() {
        return customer;
    }

    public void connectGUI(ProjectDetailGui projectDetailGui) {
        this.projectDetailGui = projectDetailGui;
    }

    public void disposeGUI() {
        this.projectDetailGui.disposeGUI();
    }
}

