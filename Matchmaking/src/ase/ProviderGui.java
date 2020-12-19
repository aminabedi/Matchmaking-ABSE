package ase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;



public class ProviderGui {

    JFrame jFrame;
    DefaultListModel<String> projectsListModel;
    List<Project> projects;
    JLabel creditLabel;
    JLabel premiumLabel;
    ProviderAgent myAgent;

    public ProviderGui(ProviderAgent myAgent, List<Project> projects) {
    	this.myAgent = myAgent;
        jFrame = new JFrame("Welcome " + myAgent.getLocalName());

        this.jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                myAgent.killAgent(myAgent.getLocalName());
            }
        });
        this.projects = projects;
        jFrame.setSize(400, 400);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        creditLabel = new JLabel();
        premiumLabel = new JLabel();
        updateCredit();
        updatePremium();
        
        JPanel jPanelNewMessage = new JPanel();
        jPanelNewMessage.add(creditLabel, BorderLayout.CENTER);
        jPanelNewMessage.add(premiumLabel, BorderLayout.SOUTH);

        jPanel.add(jPanelNewMessage, BorderLayout.CENTER);

        if(!myAgent.getProvider().isPremium){
            JButton premiumButton = new JButton("Go premium!");
            premiumButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(myAgent.goPremium()){
                        premiumButton.setVisible(false);
                    }
                }
            });
            jPanelNewMessage.add(premiumButton, BorderLayout.SOUTH);
        }
        JButton withdrawButton = new JButton("Witdraw service agreement");
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myAgent.withdraw();     
            }
        });
        jPanelNewMessage.add(withdrawButton, BorderLayout.SOUTH);
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setSize(100, 400);

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
                    ProjectDetailGui projectDetailGui = new ProjectDetailGui(myAgent, projects.get(index));
                    projectDetailGui.showGui();
                    System.out.println("Clicked: " + index);
                }
            }
        });

        leftPanel.add(projectList, BorderLayout.CENTER);
        jPanel.add(leftPanel, BorderLayout.WEST);

        jFrame.add(jPanel);
    }

    public void showGui() {
        jFrame.setVisible(true);
    }

    public void dispose() {
        this.jFrame.dispose();
    }

    public void addProject(Project project) {
        projectsListModel.addElement(project.getName());
        this.projects.add(project);
    }
    
    public void updateCredit() {
    	creditLabel.setText("Your credit: " + myAgent.getCredit());
    }

    public void updatePremium(){
        premiumLabel.setText("You are" + (myAgent.getProvider().isPremium?" ":" not ")+ "a premium user");
    }

    public void updateProjects(List<Project> projects){
        System.out.println("UPDATING PROJECTS");
        this.projects = projects;
        projectsListModel.clear();
        for (Project project : this.projects) {
            projectsListModel.addElement(project.getName());
        }
    }
}
