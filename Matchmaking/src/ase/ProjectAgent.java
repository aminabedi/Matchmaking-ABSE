package ase;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import jade.core.AID;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import jade.core.behaviours.CyclicBehaviour;


public class ProjectAgent extends EnhancedAgent {
	public Set<Project> projects = new HashSet<>();
	private boolean done = false;
	
	@Override
    protected void setup() {
        super.setup();
        System.out.println("Project agent is set up");


        register("project-register");
        register("project-update");
        register("project-message");

        this.addBehaviour(new ProjectRegisterBehaviour());
        this.addBehaviour(new ProjectUpdateBehaviour());
    }
	
	private class ProjectRegisterBehaviour extends CyclicBehaviour{
		
		public ProjectRegisterBehaviour() {
			super(ProjectAgent.this);
	        myAgent = ProjectAgent.this;
		}
		public void action() {
			ACLMessage msg, reply;
	        MessageTemplate template;

            //listening for game REQUEST
            template = MessageTemplate.and(
                      MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                      MessageTemplate.MatchConversationId(Constants.PROJECT_REG));
            
            msg = myAgent.blockingReceive(template);
            if(msg != null) {
              System.out.println("I, " + getLocalName() + ", received a project to register");
              String content = msg.getContent();
              String[] contents = content.split(":");
              Project proj = new Project(
            		  msg.getSender(),
            		  contents[0],
            		  Integer.parseInt(contents[1]));
              projects.add(proj);
              //reply with accepted
              reply = msg.createReply();
              reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
              reply.setConversationId(msg.getConversationId());
              myAgent.send(reply);
            }
			
		}
		
	}
	private class ProjectUpdateBehaviour extends CyclicBehaviour{
		
		public ProjectUpdateBehaviour() {
			super(ProjectAgent.this);
	        myAgent = ProjectAgent.this;
		}
		public void action() {
			ACLMessage msg, reply;
	        MessageTemplate template;

            //listening for project CONFIRM
            template = MessageTemplate.and(
                      MessageTemplate.MatchPerformative(ACLMessage.CONFIRM),
                      MessageTemplate.MatchConversationId(Constants.PROJECT_CONFIRM));
            
            msg = myAgent.blockingReceive(template);
            if(msg != null) {
              System.out.println("I, " + getLocalName() + ", received a project confirmation");
              String content = msg.getContent();
              for (Iterator<Project> it = projects.iterator(); it.hasNext(); ) {
                  Project f = it.next();
                  if (f.getName() == content) {
                      f.setProvider(msg.getSender());
                      break;
                  }
              }
            }
		}
	}
	
	private class ProjectMessageBehaviour extends CyclicBehaviour{
		
		public ProjectMessageBehaviour() {
			super(ProjectAgent.this);
	        myAgent = ProjectAgent.this;
		}
		public void action() {
			ACLMessage msg, reply;
	        MessageTemplate template;

            //listening for project MESSAGES
            template = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            
            msg = myAgent.blockingReceive(template);
            if(msg != null) {
              System.out.println("I, " + getLocalName() + ", received a project message");
              String content = msg.getContent();
              String contents[] = content.split(":");
              AID sender = msg.getSender();
              for (Iterator<Project> it = projects.iterator(); it.hasNext(); ) {
                  Project f = it.next();
                  if (f.getName() == contents[0]) {
                      if(f.getProvider() == sender) {
                    	  f.sendMessageByProvider(contents[1]);
                      }
                      else if(f.getCustomer() == sender) {
                    	  f.sendMessageByCustomer(contents[1]);
                      }
                      break;
                  }
              }
            }
			
		}
	}

}
