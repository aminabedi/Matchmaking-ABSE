package ase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserGui {

    UserManagerAgent userManagersAgent;
    JFrame jFrame;

    public UserGui(UserManagerAgent userManagerAgent) {
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

        String[] type = {User.CUSTOMER, User.PROVIDER};
        JComboBox comboBox_userType = new JComboBox(type);

        JButton button_login = new JButton("Login");
        button_login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String output = "";
                output += "UserName: " + textField_userName.getText();
                output += "\nPassword: " + textField_password.getText();
                output += "\nType: " + comboBox_userType.getSelectedItem().toString();

                System.out.println(output);
                userManagersAgent.login(textField_userName.getText(), textField_password.getText(),
                        comboBox_userType.getSelectedItem().toString());
            }
        });

        JButton button_register = new JButton("Register");
        
        button_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                userManagerAgent.registerUser(textField_userName.getText(), textField_password.getText(), comboBox_userType.getSelectedItem().toString());
//                System.out.println("User: "+textField_userName.getText()+" is registered successfully");
                RegistrationGui registrationGui = new RegistrationGui(userManagerAgent);
                registrationGui.showGui();
            }
        });

        JButton button_guest = new JButton("Guest");
        button_guest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuestGui guestGui = new GuestGui(userManagerAgent.searchForService("project-provide"));
                guestGui.showGui();
            }
        });

        textField_userName.setPreferredSize(new Dimension(200, 24));
        textField_password.setPreferredSize(new Dimension(200, 24));

        jPanel.add(textField_userName);
        jPanel.add(textField_password);
        jPanel.add(comboBox_userType);
        jPanel.add(button_login);
        jPanel.add(button_register);
        jPanel.add(button_guest);

        this.jFrame.add(jPanel);
    }

    public void showGui() {

        this.jFrame.setVisible(true);
    }

    public void dispose() {
        this.jFrame.dispose();
    }

    public void showWrongCredential() {
        JOptionPane.showMessageDialog(jFrame, "Wrong Credential", "ERROR",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        UserGui userGui = new UserGui(null);
        userGui.showGui();
    }
}
