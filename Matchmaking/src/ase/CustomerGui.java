package ase;

import jade.core.AID;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Set;

public class CustomerGui {

    JFrame jFrame;
    AID selectedProvider = null;
    DefaultListModel<String> providersList;
    List<Project> projects;
    DefaultListModel<String> projectsListModel;

    public CustomerGui(CustomerAgent myAgent, Set<AID> providers, List<Project> projects) {
        jFrame = new JFrame("Welcome " + myAgent.getLocalName());
        jFrame.setSize(700, 400);
        this.projects = projects;

        this.jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                myAgent.killAgent(myAgent.getLocalName());
            }
        });

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setSize(100, 400);

        providersList = new DefaultListModel<>();
        for (AID provider : providers) {
            String text = "Name: ";
            Provider currentProvider = UserManagerAgent.getProvider(provider.getLocalName().split(":")[1]);
            text += currentProvider.getUsername() + ", ";
            text += "Role: ";
            text += currentProvider.getRole()+", ";
            text+= "Skill: ";
            text+=currentProvider.getSkill()+", ";
            providersList.addElement(text);
        }
        JList<String> list = new JList<>(providersList);
        list.setFixedCellHeight(50);
        list.setFixedCellWidth(300);

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.println("HERE");
                if (!e.getValueIsAdjusting()) {
                    System.out.println(list.getSelectedIndex());
                    for (AID provider : providers) {
                        System.out.println(provider.getLocalName() + "|" + list.getSelectedValue());
                        if (provider.getLocalName().equals(list.getSelectedValue())) {
                            selectedProvider = provider;
                        }
                    }
                }
            }
        });
        leftPanel.add(list, BorderLayout.NORTH);

        projectsListModel = new DefaultListModel<>();

        for (Project project : this.projects) {
            projectsListModel.addElement(project.getName());
        }

        JList<String> projectList = new JList<>(projectsListModel);
        projectList.setFixedCellHeight(50);
        projectList.setFixedCellWidth(100);

        projectList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    ProjectDetailGui projectDetailGui = new ProjectDetailGui(myAgent, CustomerGui.this.projects.get(index));
                    projectDetailGui.showGui();
                    System.out.println("Clicked: " + index);
                }
            }
        });

        leftPanel.add(projectList, BorderLayout.CENTER);
        jPanel.add(leftPanel, BorderLayout.WEST);

        JTextArea jTextAreaDescription = new JTextArea("Project Description");
        jTextAreaDescription.setRows(20);
        jTextAreaDescription.setColumns(20);

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new BorderLayout());

        JTextField jTextFieldName = new HintTextField("Name");
        jPanel1.add(jTextFieldName, BorderLayout.NORTH);
        jPanel1.add(jTextAreaDescription, BorderLayout.CENTER);

        jPanel.add(jPanel1, BorderLayout.CENTER);

        HintTextField bid = new HintTextField("BID:");
        bid.setPreferredSize(new Dimension(200, 24));
        JButton jButtonSend = new JButton("Create");
        jButtonSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedProvider == null) {
                    return;
                }
                Project project = new Project(jTextFieldName.getText(), jTextAreaDescription.getText(),
                        Integer.parseInt(bid.getText()), selectedProvider, myAgent.getAID());
                System.out.println(jTextFieldName.getText() + "  " + project.toString());
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

    public void addProject(Project project) {
        this.projects.add(project);
        projectsListModel.addElement(project.getName());
    }

}
