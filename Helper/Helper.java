package Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static int screenCenterLocation(String axis, Dimension size) {
        int point = 0;
        switch (axis) {
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
                break;
        }
        return point;

    }

    public static boolean confirm(String str) {
        String msg;
        switch (str) {
            case "sure":
                msg = "Are you sure you want to perform this operation?";
                break;
            default:
                msg = str;
        }
        return JOptionPane.showConfirmDialog(null,msg,"Are you sure?",JOptionPane.YES_NO_OPTION) == 0;
    }

    public static void setLayout() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {


            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }
            break;

        }
    }

    public static void showMessage(String str) {
        String displayOnMessage;
        String title ;

        switch (str) {
            case "fill":
                displayOnMessage = "Please fill in all fields!";
                title = "Error!";
                break;
            case "done":
                displayOnMessage = "Process Successful!";
                title = "Result";
                break;
            case "error":
                displayOnMessage = "Error occured!";
                title = "Error";
                break;
            default:
                displayOnMessage = str;
                title = "Info Message";
        }
        JOptionPane.showMessageDialog(null,displayOnMessage,title,JOptionPane.INFORMATION_MESSAGE);
    }

}
