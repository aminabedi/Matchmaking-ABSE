package ase;

import java.awt.BorderLayout;


import javax.swing.*;


public class ProviderGui {

    JFrame jFrame;

    public ProviderGui(ProviderAgent myAgent) {
        jFrame = new JFrame("Welcome " + myAgent.getLocalName());
        jFrame.setSize(400, 400);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        JLabel jButton = new JLabel("Please wait for proposals");
        JPanel jPanelNewMessage = new JPanel();
        jPanelNewMessage.add(jButton, BorderLayout.CENTER);

        jPanel.add(jPanelNewMessage, BorderLayout.CENTER);

        jFrame.add(jPanel);
    }

    public void showGui() {
        jFrame.setVisible(true);
    }

    public void dispose() {
        this.jFrame.dispose();
    }

}
