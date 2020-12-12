package ase;

import jade.core.AID;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MessageGui {

    JFrame jFrame;
    JTextArea jTextAreaMessages;

    public MessageGui(List<Project> projects) {
        jFrame = new JFrame();
        jFrame.setSize(600, 400);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        DefaultListModel<String> l1 = new DefaultListModel<>();
        for (Project project : projects) {
            String text = "Name: " + project.getName() + " Provider: " + project.getProvider() +
                    " Bid: " + project.getCustomer();
            l1.addElement(text);
        }
        JList<String> list = new JList<>(l1);
        list.setFixedCellHeight(30);
        list.setFixedCellWidth(350);

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    Project project = projects.get(list.getSelectedIndex());
                    String allMessages = "";
                    List<InternalMessage> messages = project.getMessages();
                    for (InternalMessage message : messages) {
                        allMessages += message.content;
                        allMessages += "\n";
                    }
                    showMessage(allMessages);
                }
            }
        });

        jTextAreaMessages = new JTextArea("Messages:");
        jTextAreaMessages.setRows(20);
        jTextAreaMessages.setColumns(20);
        jPanel.add(list, BorderLayout.WEST);
        jPanel.add(jTextAreaMessages, BorderLayout.CENTER);

        HintTextField newMessage = new HintTextField("New Message:");
        newMessage.setPreferredSize(new Dimension(300, 30));
        JButton jButtonSend = new JButton("Send");
        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Callback to the agent with a new message text
                System.out.println("Send " + newMessage.getText());
            }
        });
        JPanel jPanelNewMessage = new JPanel();
        jPanelNewMessage.add(newMessage, BorderLayout.CENTER);
        jPanelNewMessage.add(jButtonSend, BorderLayout.SOUTH);

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

    public static void main(String[] args) {
        List<Project> projects = new ArrayList<>();
        Project project1 = new Project(new AID(), "Project 1", "Description 1",10);
        project1.addMessage(new InternalMessage(new AID(), new AID(), "Hi"));
        project1.addMessage(new InternalMessage(new AID(), new AID(), "Hello"));

        Project project2 = new Project(new AID(), "Project 2", "Description 2", 5);
        project2.addMessage(new InternalMessage(new AID(), new AID(), "Are you fine?"));
        project2.addMessage(new InternalMessage(new AID(), new AID(), "YES!"));

        projects.add(project1);
        projects.add(project2);
        MessageGui gui = new MessageGui(projects);
        gui.showGui();
    }


}
