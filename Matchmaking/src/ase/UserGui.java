import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserGui {

    UserManagersAgent userManagersAgent;
    JFrame jFrame;

    public UserGui(UserManagersAgent userManagersAgent) {
        this.jFrame = new JFrame();
        this.userManagersAgent = userManagersAgent;
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

        textField_userName.setPreferredSize(new Dimension(200, 24));
        textField_password.setPreferredSize(new Dimension(200, 24));

        jPanel.add(textField_userName);
        jPanel.add(textField_password);
        jPanel.add(comboBox_userType);
        jPanel.add(button_login);

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