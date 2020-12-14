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

    public MessageGui(Agent myAgent, ACLMessage reply, String msg, Boolean isProposal) {
        jFrame = new JFrame("Message for " + myAgent.getLocalName());
        jFrame.setSize(600, 400);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        JLabel jLabel = new JLabel("<html>" + msg.replaceAll("\n", "<br>") + "</html>");
        jLabel.setSize(new Dimension(20, 20));
        jPanel.add(jLabel, BorderLayout.CENTER);
        
        JPanel jPanelNewMessage = new JPanel();
        if(isProposal) {
        	JButton jButtonAccept = new JButton("Accept");
        	JButton jButtonReject = new JButton("Reject");
            jButtonAccept.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	reply.setContent(msg);
                	reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    myAgent.send(reply);
                }
            });
            jButtonReject.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	reply.setContent(msg);
                	reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                    myAgent.send(reply);
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
	            	reply.setContent(msg + "\n" + newMessage.getText());
	                myAgent.send(reply);
	            }
	        });
	        
	        jPanelNewMessage.add(newMessage, BorderLayout.CENTER);
	        jPanelNewMessage.add(jButtonSend, BorderLayout.SOUTH);
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
