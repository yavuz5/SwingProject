package com.Swingapp;

import Helper.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Example extends JFrame {
    private JPanel wrapper;
    private JPanel wrapperTop;
    private JPanel wrapperBottom;
    private JTextField fld_username;
    private JPasswordField fld_password;
    private JButton button_login;

    public Example() {

        Helper.setLayout();

        this.add(wrapper);  //
        setSize(400, 300);                          // Uygulama screen olculeri
        setTitle("Uygulama adi");                               // uygulama adi
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);      // Screeni kapattigimizda uygulamanin sonlanmasi
        setVisible(true);                                       // Gorunebilirlik
        setResizable(false);                                    // Acilan framede buyutme veya kucultme yapilamaz.
        // Toolkit property'i ekranimizin ozelliklerini bize getiren bir gomulu fonksiyon
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2;

        setLocation(x, y);


        button_login.addActionListener(e -> {
            if (fld_username.getText().length() == 0 || fld_password.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Tum alanlari doldurunuz", "Hata", JOptionPane.INFORMATION_MESSAGE);
            }

        });
    }


}
