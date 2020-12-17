package ase;

import jade.core.Agent;

import javax.swing.*;
import java.awt.*;

public class ProjectDetailGui {

    JFrame jFrame;

    ProjectDetailGui(Agent agent, Project project) {

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
        jPanel.add(new JButton("Next"),BorderLayout.SOUTH);
        this.jFrame.add(jPanel);
    }

    public void showGui() {
        jFrame.setVisible(true);
    }
}
