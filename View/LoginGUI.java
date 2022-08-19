package View;

import Helper.Helper;
import Model.Operator;
import Model.User;

import javax.swing.*;
import java.util.Objects;

public class LoginGUI extends  JFrame{
    private JPanel wrapper;
    private JPanel wrapper_top;
    private JTextField textField_userName;
    private JPasswordField textField_user_password;
    private JButton button1;

    public LoginGUI() {
        add(wrapper);
        setSize(650,650);
        setLocation(Helper.screenCenterLocation("x",getSize()),Helper.screenCenterLocation("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(TitleList.Frame.name());
        setVisible(true);
        setResizable(false);
        button1.addActionListener(e -> {
            if(textField_userName.getText().isEmpty() || textField_user_password.getText().isEmpty()) {
                Helper.showMessage("fill");
            } else {
                Operator user = User.getFetch(textField_userName.getText(),textField_user_password.getText());
                if(Objects.isNull(user)) {
                    Helper.showMessage("User not found!");
                } else{
                    dispose();
                    OperatorGUI operatorGUI = new OperatorGUI( user);

                }
            }
        });
    }

    public static void main(String[] args) {
        LoginGUI loginGUI = new LoginGUI();

    }
}
