package ase;

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
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.add(jButtonSend, BorderLayout.NORTH);
        JButton doneButton = new JButton("Done");
        southPanel.add(doneButton, BorderLayout.SOUTH);
        jPanel.add(southPanel, BorderLayout.SOUTH);
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDialog();
            }
        });
        this.jFrame.add(jPanel);
    }

    private void showDialog() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setSize(400, 400);
        HintTextField ratingTextField = new HintTextField("Rating");
        ratingTextField.setPreferredSize(new Dimension(200, 24));

        HintTextField commentTextField = new HintTextField("Comment");
        commentTextField.setPreferredSize(new Dimension(200, 24));

        JButton jButton = new JButton("Done");

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                System.out.println("Done" + ratingTextField.getText() + commentTextField.getText());
            }
        });

        panel.add(ratingTextField);
        panel.add(commentTextField);
        panel.add(jButton);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
