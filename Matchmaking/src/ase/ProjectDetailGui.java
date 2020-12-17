package ase;

import jade.core.Agent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectDetailGui {

    JFrame jFrame;

    ProjectDetailGui(CustomerAgent agent, Project project) {

        jFrame = new JFrame("Welcome " + agent.getLocalName());
        jFrame.setSize(400, 400);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        JLabel jLabel = new JLabel();
        String text = "<html>";
        text += "Name: " + project.getName() + "<br/>";
        text += "Description: " + project.getDescription() + "<br/>";
        text += "Progress: " + project.getProgress();
        text += "</html>";
        jPanel.add(new JLabel(text), BorderLayout.CENTER);

        JTextField jTextFieldMessage = new HintTextField("Message");
        jPanel.add(jTextFieldMessage, BorderLayout.NORTH);

//        jPanel.add(new JButton("Next"),BorderLayout.SOUTH);
        JButton jButtonSend = new JButton("send");
        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                agent.send();
//                Project project = new Project(jTextFieldMessage.getText(), jTextAreaDescription.getText(),
//                        Integer.parseInt(bid.getText()));
                String messageText = jTextFieldMessage.getText();
                project.chatUpdate(messageText);
                agent.sendMessage(project.getProvider(), messageText);

//                System.out.println(jTextFieldMessage.getText() + "  " + project.toString());
//                myAgent.sendProposal(project, selectedProvider);
            }
        });
        jPanel.add(jButtonSend, BorderLayout.SOUTH);
        this.jFrame.add(jPanel);
    }

    ProjectDetailGui(ProviderAgent agent, Project project) {

        jFrame = new JFrame("Welcome " + agent.getLocalName());
        jFrame.setSize(400, 400);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        JLabel jLabel = new JLabel();
        String text = "<html>";
        text += "Name: " + project.getName() + "<br/>";
        text += "Description: " + project.getDescription() + "<br/>";
        text += "Progress: " + project.getProgress();
        text += "</html>";
        jPanel.add(new JLabel(text), BorderLayout.CENTER);

        JTextField jTextFieldMessage = new HintTextField("Message");
        jPanel.add(jTextFieldMessage, BorderLayout.NORTH);

//        jPanel.add(new JButton("Next"),BorderLayout.SOUTH);
        JButton jButtonSend = new JButton("send");
        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                agent.send();
//                Project project = new Project(jTextFieldMessage.getText(), jTextAreaDescription.getText(),
//                        Integer.parseInt(bid.getText()));
                System.out.println(jTextFieldMessage.getText());
//                System.out.println(jTextFieldMessage.getText() + "  " + project.toString());
//                myAgent.sendProposal(project, selectedProvider);
            }
        });
        jPanel.add(jButtonSend, BorderLayout.SOUTH);
        this.jFrame.add(jPanel);
    }


    public void showGui() {
        jFrame.setVisible(true);
    }
}
