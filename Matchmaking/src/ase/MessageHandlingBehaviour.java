package ase;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class MessageHandlingBehaviour extends CyclicBehaviour{
	Agent myAgent;
	public MessageHandlingBehaviour(Agent myAgent) {
		super(myAgent);
		this.myAgent = myAgent;
	}
	public void action() {
		ACLMessage msg, reply;
        MessageTemplate template;

        //listening for chat messages
        template = MessageTemplate.MatchConversationId(Constants.CHAT);
        msg = myAgent.receive(template);
        if (msg != null){
            String content = msg.getContent();
            System.out.println("Received a chat message:" + content);
            reply = msg.createReply();
            MessageGui msgGui = new MessageGui(myAgent, reply, msg, false);
            msgGui.showGui();
        }
	}
}
