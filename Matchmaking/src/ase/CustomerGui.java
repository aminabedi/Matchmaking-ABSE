package ase;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jade.core.AID;

public class CustomerGui {

    JFrame jFrame;
    AID selectedProvider = null;

    public CustomerGui(CustomerAgent myAgent, Set<AID> providers) {
        jFrame = new JFrame("Welcome " + myAgent.getLocalName());
        jFrame.setSize(400, 400);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        DefaultListModel<String> l1 = new DefaultListModel<>();
        for (AID provider : providers) {
            l1.addElement(provider.getLocalName());
        }
        JList<String> list = new JList<>(l1);
        list.setFixedCellHeight(30);
        list.setFixedCellWidth(100);

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
            	System.out.println("HERE");
                if (!e.getValueIsAdjusting()) {
                    System.out.println(list.getSelectedIndex());
                    for (AID provider : providers) {
                    	System.out.println(provider.getLocalName() + "|" + list.getSelectedValue());
                        if(provider.getLocalName().equals(list.getSelectedValue())) {
                        	selectedProvider = provider;
                        }
                    }
                }
            }
        });

        JTextArea jTextAreaDescription = new JTextArea("Project Description");
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
            	if(selectedProvider==null) {
            		return;
            	}
                Project project = new Project(jTextFieldName.getText(), jTextAreaDescription.getText(),
                        Integer.parseInt(bid.getText()));
                System.out.println(jTextFieldName.getText()+ "  "+  project.toString());
                myAgent.sendProposal(project, selectedProvider);
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

}
