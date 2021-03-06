package ase;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class MessageGui {

    JFrame jFrame;
    JTextArea jTextAreaMessages;

    public MessageGui(Agent myAgent, ACLMessage reply, ACLMessage msg, Boolean isProposal) {
        String content = msg.getContent();
        jFrame = new JFrame("Message for " + myAgent.getLocalName());

        this.jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
//                myAgent.killAgent(myAgent.getLocalName());
            }
        });

        jFrame.setSize(600, 400);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        JLabel jLabel = new JLabel();
        String[] text = content.split(":");
        String output = "<HTML>";
        for(String string : text){
            output+=" "+string+"<br/>";
        }
        output+="</HTML>";
        jLabel.setText(output);
        jLabel.setSize(new Dimension(20, 20));
        jPanel.add(jLabel, BorderLayout.CENTER);
        
        JPanel jPanelNewMessage = new JPanel();
        if(isProposal) {
        	JButton jButtonAccept = new JButton("Accept");
        	JButton jButtonReject = new JButton("Reject");
            jButtonAccept.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	reply.setContent(content);
                	reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    myAgent.send(reply);
                    String c[] = content.split(":");
                    Project project = new Project(c[0], c[1], Integer.parseInt(c[2]), myAgent.getAID(), msg.getSender(),"");
                    ((ProviderAgent)myAgent).providerGui.addProject(project);
                    dispose();
                }
            });
            jButtonReject.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	reply.setContent(content);
                	reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                    myAgent.send(reply);
                    dispose();
                }
            });
            jPanelNewMessage.add(jButtonAccept, BorderLayout.WEST);
            jPanelNewMessage.add(jButtonReject, BorderLayout.EAST);
        }else if(reply != null){
	        HintTextField newMessage = new HintTextField("Reply:");
	        newMessage.setPreferredSize(new Dimension(300, 30));
	        JButton jButtonSend = new JButton("Send");
	        jButtonSend.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	reply.setContent(content + "\n" + newMessage.getText());
	                myAgent.send(reply);
	            }
	        });
	        
	        jPanelNewMessage.add(newMessage, BorderLayout.CENTER);
	        jPanelNewMessage.add(jButtonSend, BorderLayout.SOUTH);
//	        myAgent.openProject(selectProject);
        }
        jPanel.add(jPanelNewMessage, BorderLayout.SOUTH);
        jFrame.add(jPanel);
        showGui();
    }


    private void showMessage(String message) {
        jTextAreaMessages.setText(message);
    }

    public void showGui() {
        jFrame.setVisible(true);
    }

    public void dispose() {
        this.jFrame.dispose();
    }



}
