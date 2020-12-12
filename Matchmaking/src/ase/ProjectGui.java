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

public class ProjectGui {

    JFrame jFrame;

    public ProjectGui(List<User> providers) {
        jFrame = new JFrame();
        jFrame.setSize(400, 400);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        DefaultListModel<String> l1 = new DefaultListModel<>();
        for (User provider : providers) {
            l1.addElement(provider.getUsername());
        }
        JList<String> list = new JList<>(l1);
        list.setFixedCellHeight(30);
        list.setFixedCellWidth(100);

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    System.out.println(list.getSelectedIndex());
                }
            }
        });

        JTextArea jTextAreaDescription = new JTextArea("Project Description:");
        jTextAreaDescription.setRows(20);
        jTextAreaDescription.setColumns(20);
        jPanel.add(list, BorderLayout.WEST);

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new BorderLayout());

        JTextField jTextFieldName = new HintTextField("Name");
        jPanel1.add(jTextFieldName,BorderLayout.NORTH);
        jPanel1.add(jTextAreaDescription, BorderLayout.CENTER);

        jPanel.add(jPanel1,BorderLayout.CENTER);

        HintTextField bid = new HintTextField("BID:");
        bid.setPreferredSize(new Dimension(200, 24));
        JButton jButtonSend = new JButton("Create");
        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Create a project
                Project project = new Project(new AID(), "", jTextAreaDescription.getText(),
                        Integer.parseInt(bid.getText()));
                System.out.println("Created with BID: " + bid.getText());
            }
        });
        JPanel jPanelNewMessage = new JPanel();
        jPanelNewMessage.add(bid, BorderLayout.CENTER);
        jPanelNewMessage.add(jButtonSend, BorderLayout.SOUTH);

        jPanel.add(jPanelNewMessage, BorderLayout.SOUTH);

        jFrame.add(jPanel);
    }

    public void showGui() {
        jFrame.setVisible(true);
    }

    public void dispose() {
        this.jFrame.dispose();
    }

    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        users.add(new User("P1","",User.PROVIDER));
        users.add(new User("P2","",User.PROVIDER));
        users.add(new User("P3","",User.PROVIDER));
        ProjectGui gui = new ProjectGui(users);
        gui.showGui();
    }
}
