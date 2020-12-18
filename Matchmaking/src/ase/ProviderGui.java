package ase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class ProviderGui {

    JFrame jFrame;
    DefaultListModel<String> projectsListModel;
    List<Project> projects;
    JLabel creditLabel;
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
        creditLabel = new JLabel("Your credit: " + myAgent.getCredit());
        JPanel jPanelNewMessage = new JPanel();
        jPanelNewMessage.add(creditLabel, BorderLayout.CENTER);

        jPanel.add(jPanelNewMessage, BorderLayout.CENTER);

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

}
