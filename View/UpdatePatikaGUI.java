package View;

import Helper.Helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdatePatikaGUI extends JFrame{
    private JPanel wrapper;
    private JTextField text_field_patika_name;
    private JButton button_update;
    private Patika patika;

    public UpdatePatikaGUI(Patika patika) {
        this.patika = patika;
        add(wrapper);
        setSize(300,150);
        setLocation(Helper.screenCenterLocation("x",getSize()),Helper.screenCenterLocation("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(TitleList.HansLanda.name());
        setVisible(true);

        text_field_patika_name.setText(patika.getName());

        button_update.addActionListener(e -> {
            if(patika.getName().isEmpty()) {
                Helper.showMessage("fill");
            }
            else {
                if(Patika.update(patika.getID(),text_field_patika_name.getText())) {
                    Helper.showMessage("done");
                }
                dispose();
            }
        });
    }

}
