package ase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationGui {

    JFrame jFrame;
    UserManagerAgent userManagersAgent;

    public RegistrationGui(UserManagerAgent userManagerAgent) {

        this.jFrame = new JFrame();
        this.jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                userManagerAgent.killAgent(userManagerAgent.getLocalName());
            }
        });

        this.userManagersAgent = userManagerAgent;
        this.jFrame.setSize(400, 400);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());

        HintTextField textField_userName = new HintTextField("UserName");
        HintTextField textField_password = new HintTextField("Password");


        String[] type = {User.PROVIDER, User.CUSTOMER};
        JComboBox comboBox_userType = new JComboBox(type);

        String[] skills = {"Java", "PHP", "Python", "Go", "C"};
        JComboBox comboBox_skills = new JComboBox(skills);

        JButton button_register = new JButton("Register");
        button_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userManagerAgent.registerUser(textField_userName.getText(), textField_password.getText(), comboBox_userType.getSelectedItem().toString(), comboBox_skills.getSelectedItem().toString());
                System.out.println("User: " + textField_userName.getText() + " is registered successfully");
            }
        });

        comboBox_userType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox_userType.getSelectedIndex() == 1) {
                    comboBox_skills.setVisible(false);
                } else {
                    comboBox_skills.setVisible(true);
                }
            }
        });

        textField_userName.setPreferredSize(new Dimension(200, 24));
        textField_password.setPreferredSize(new Dimension(200, 24));

        jPanel.add(textField_userName);
        jPanel.add(textField_password);
        jPanel.add(comboBox_userType);
        jPanel.add(comboBox_skills);
        jPanel.add(button_register);

        this.jFrame.add(jPanel);
    }

    public void showGui() {
        this.jFrame.setVisible(true);
    }

    public void dispose() {
        this.jFrame.dispose();
    }
}
