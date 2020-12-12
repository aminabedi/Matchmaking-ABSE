package ase;

import javax.swing.*;
import java.awt.*;

public class ProposerAgentGUI {
    ProposerAgent agent;
    ProposerAgentFrame frame;

    class ProposerAgentFrame extends JFrame {
        JTextArea proposalText;
        JButton sendButton;

        public ProposerAgentFrame() throws HeadlessException {
            super("Proposer");
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            setSize(450, 360);
            setLocationByPlatform(true);

            setLayout(new BorderLayout());
            sendButton = new JButton("Send");

            proposalText = new JTextArea();
            proposalText.setText("Plant 100 more trees in Calgary NW area");
            sendButton.addActionListener(e -> {
                agent.propose(proposalText.getText());
                showMessage("Proposal sent");
                setEnabled(false);
            });

            add(proposalText, BorderLayout.CENTER);
            add(sendButton, BorderLayout.SOUTH);
        }
    }

    public ProposerAgentGUI(ProposerAgent agent) {
        this.agent = agent;
        this.frame = new ProposerAgentFrame();
        this.frame.setVisible(true);
    }

    void kill() {
        this.frame.dispose();
        this.frame.setVisible(false);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }


    public void askFinalization() {
        if (JOptionPane.showConfirmDialog(
                frame,
                "Finalize proposal?",
                "Finalize",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            agent.finalizeProposal();
        }
    }

    public void setEnabled(boolean b) {
        frame.proposalText.setEnabled(b);
        frame.sendButton.setEnabled(b);
    }

}
