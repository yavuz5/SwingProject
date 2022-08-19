package View;

import Helper.Helper;
import Model.Course;
import Model.Operator;
import Model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Helper.*;


public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel label_intro;
    private JButton button_exit;
    private JPanel panel_user_list;
    private JScrollPane ScrollUserList;
    private JTable userPage_userList;
    private JTable patikaPage_userList;
    private JPanel user_form;
    private JTextField textfield_name;
    private JTextField textfield_username;
    private JTextField textfield_password;
    private JComboBox combo_usertype;
    private JButton insert_member;
    private JTextField field_userID;
    private JButton button_user_delete;
    private JTextField serach_name_surname;
    private JTextField search_username;
    private JComboBox serach_combo_box;
    private JButton button_serach;
    private JScrollPane scroll_patika_list;
    private JPanel patikaPage_panel_add;
    private JTextField textField_patikaName;
    private JButton button_patikaAdd;
    private JPanel panel_courseList;
    private JPanel panel_patikalar;
    private JScrollPane scroll_coursePage;
    private JTable coursePage_userList;
    private JPanel panel_course_right;
    private JTextField textfield_lesson_name;
    private JTextField textfield_language_name;
    private JComboBox comboBox_course_name;
    private JComboBox comboBox_teacher_name;
    private JButton button_course_add;
    private final Operator operator;
    private DefaultTableModel model_userPage;
    private Object[] userPage_row;
    private DefaultTableModel model_patikaPage;
    private Object[] patikaPage_row;
    private JPopupMenu patikaPageMenu;
    private DefaultTableModel model_coursePage;
    private Object[] coursePage_row;

    public void loadUserdata() {
        DefaultTableModel clearModel = (DefaultTableModel) userPage_userList.getModel();
        clearModel.setRowCount(0);

        for (User user : User.getList()) {
            userPage_row[0] = user.getId();
            userPage_row[1] = user.getName();
            userPage_row[2] = user.getUser_name();
            userPage_row[3] = user.getPassword();
            userPage_row[4] = user.getType();
            model_userPage.addRow(userPage_row);
        }

    }

    public void loadUserdata(List<User> userPage_userList) {
        DefaultTableModel clearModel = (DefaultTableModel) this.userPage_userList.getModel();
        clearModel.setRowCount(0);

        for (User user : userPage_userList) {
            userPage_row[0] = user.getId();
            userPage_row[1] = user.getName();
            userPage_row[2] = user.getUser_name();
            userPage_row[3] = user.getPassword();
            userPage_row[4] = user.getType();
            model_userPage.addRow(userPage_row);
        }
    }


    public OperatorGUI(Operator operator) {
        this.operator = operator;
        add(wrapper);
        setSize(1000, 500);
        int x = Helper.screenCenterLocation("x", getSize());
        int y = Helper.screenCenterLocation("y", getSize());
        setLocation(x, y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(TitleList.HansLanda.name());
        setVisible(true);


        label_intro.setText("Hosgeldin : " + operator.getName());

        model_userPage = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        //ModelUserList
        userPage_view();


        userPage_userList.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                try {
                    String selected_user_id = userPage_userList.getValueAt(userPage_userList.getSelectedRow(), 0).toString();
                    System.out.println(selected_user_id);
                    field_userID.setText(selected_user_id);
                } catch (Exception ex) {
                    ;
                }
            }
        });

        userPage_userList.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int userID = Integer.parseInt(userPage_userList.getValueAt(userPage_userList.getSelectedRow(), 0).toString());
                String name = userPage_userList.getValueAt(userPage_userList.getSelectedRow(), 1).toString();
                String user_name = userPage_userList.getValueAt(userPage_userList.getSelectedRow(), 2).toString();
                String password = userPage_userList.getValueAt(userPage_userList.getSelectedRow(), 3).toString();
                String type = userPage_userList.getValueAt(userPage_userList.getSelectedRow(), 4).toString();

                if (User.update(name, user_name, password, type, userID)) {
                    User findUser = User.getFetch(userID);
                    if (!Objects.isNull(findUser)) {
                        Helper.showMessage("done");
                    }
                }
                loadUserdata();
                loadCourseList();
                loadUserdata();

            }
        });

        patikaPageMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Update");
        JMenuItem deleteMenu = new JMenuItem("Delete");
        patikaPageMenu.add(updateMenu);
        patikaPageMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int select_id = Integer.parseInt(patikaPage_userList.getValueAt(patikaPage_userList.getSelectedRow(), 0).toString());
            UpdatePatikaGUI updatePatikaGUI = new UpdatePatikaGUI(Patika.getFetch(select_id));
            updatePatikaGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                    loadCourseList();
                }
            });
        });


        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int selected_id = Integer.parseInt(patikaPage_userList.getValueAt(patikaPage_userList.getSelectedRow(), 0).toString());
                if (Patika.delete(selected_id)) {
                    Helper.showMessage("done");
                } else {
                    Helper.showMessage("error");
                }
                loadPatikaModel();
                loadPatikaComboBox();
                loadCourseList();
            }
        });

        patikaPage_view();

        coursePage_view();

        loadPatikaComboBox();

        loadEducatorComboBox();


        insert_member.addActionListener(e -> {
            JOptionPaneButtonText.optionPaneButton();
            TextControl textControl = new TextControl(textfield_name.getText(), textfield_username.getText(), textfield_password.getText());
            if (!textControl.checkText()) {
                Helper.showMessage("fill");
            } else {
                String name = textfield_name.getText();
                String user_name = textfield_username.getText();
                String password = textfield_password.getText();
                String type = combo_usertype.getSelectedItem().toString();

                if (User.add(name, user_name, password, type)) {
                    Helper.showMessage("Done");
                    textfield_username.setText(null);
                    textfield_name.setText(null);
                    textfield_password.setText(null);
                    loadUserdata();
                } else {
                    Helper.showMessage("error");
                }
            }
        });

        button_user_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (field_userID.getText().isEmpty()) {
                    Helper.showMessage("fill");
                } else {
                    if (Helper.confirm("sure")) {
                        int user_id = Integer.parseInt(field_userID.getText());
                        if (User.delete(user_id)) {
                            Helper.showMessage("done");
                            field_userID.setText(null);
                        } else {
                            Helper.showMessage("error");
                        }
                        loadUserdata();
                        loadCourseList();
                    }
                }
            }
        });

        button_serach.addActionListener(e -> {
            String name = serach_name_surname.getText();
            String user_name = search_username.getText();
            String type = serach_combo_box.getSelectedItem().toString();

            String searching = User.searchQuery(name, user_name, type);
            ArrayList<User> userArrayList = User.searchUserList(searching);
            loadUserdata(userArrayList);

        });

        button_exit.addActionListener(e -> {
            dispose();      // Closes the pop up frame
            LoginGUI loginGUI = new LoginGUI();
        });

        button_patikaAdd.addActionListener(e -> {
            if (textField_patikaName.getText().isEmpty()) {
                Helper.showMessage("fill");
            } else {
                if (Patika.add(textField_patikaName.getText())) {
                    Helper.showMessage("done");
                    loadPatikaModel();
                    textField_patikaName.setText(null);
                } else {
                    Helper.showMessage("error");
                }
            }
        });
        button_course_add.addActionListener(e -> {
            Item patikaItem = (Item) comboBox_course_name.getSelectedItem();
            Item userItem = (Item) comboBox_teacher_name.getSelectedItem();

            if (Objects.isNull(patikaItem) || Objects.isNull(userItem)) {
                Helper.showMessage("fill");
            } else {
                if (Course.add(userItem.getKey(), patikaItem.getKey(), textfield_lesson_name.getText(), textfield_language_name.getText())) {
                    Helper.showMessage("done");
                    textField_patikaName.setText(null);
                    textfield_lesson_name.setText(null);
                } else {
                    Helper.showMessage("error");
                }
                loadCourseList();
            }
        });
    }

    private void coursePage_view() {
        model_coursePage = new DefaultTableModel();
        Object[] col_coursList = {"10", "Lesson Name", "Programmer Language", "Patika", "Educator"};
        coursePage_row = new Object[col_coursList.length];
        model_coursePage.setColumnIdentifiers(col_coursList);
        loadCourseList();
        coursePage_userList.setModel(model_coursePage);
        coursePage_userList.getColumnModel().getColumn(0).setMaxWidth(100);
        coursePage_userList.getTableHeader().setReorderingAllowed(false);

    }

    private void loadCourseList() {
        DefaultTableModel clearModel = (DefaultTableModel) coursePage_userList.getModel();
        clearModel.setRowCount(0);


        for (Course instance : Course.getList()) {
            coursePage_row[0] = instance.getId();
            coursePage_row[1] = instance.getName();
            coursePage_row[2] = instance.getLanguage();
            coursePage_row[3] = instance.getPatika().getName();
            coursePage_row[4] = instance.getEducator().getName();
            model_coursePage.addRow(coursePage_row);
        }
        loadEducatorComboBox();
    }

    private void patikaPage_view() {
        model_patikaPage = new DefaultTableModel();
        Object[] patikaPageColumns = {"ID", "Patika Name"};
        model_patikaPage.setColumnIdentifiers(patikaPageColumns);
        patikaPage_row = new Object[5];
        loadPatikaModel();
        patikaPage_userList.setModel(model_patikaPage);
        patikaPage_userList.setComponentPopupMenu(patikaPageMenu);
        patikaPage_userList.getTableHeader().setReorderingAllowed(false);
        patikaPage_userList.getColumnModel().getColumn(0).setMaxWidth(100);     // Sutun barinin boyutunu belirliyor.

        patikaPage_userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = patikaPage_userList.rowAtPoint(point);
                patikaPage_userList.setRowSelectionInterval(selected_row, selected_row);
            }
        });
    }

    private void userPage_view() {
        model_userPage = new DefaultTableModel();
        Object[] col_userList = {"ID", "NAME", "USERNAME", "PASSWORD", "TYPE"};
        model_userPage.setColumnIdentifiers(col_userList);
        userPage_row = new Object[col_userList.length];
        loadUserdata();
        userPage_userList.setModel(model_userPage);
        userPage_userList.getTableHeader().setReorderingAllowed(false);        // const column not move
    }

    private void loadPatikaModel() {
        DefaultTableModel clearModel = (DefaultTableModel) patikaPage_userList.getModel();
        clearModel.setRowCount(0);
        for (Patika obj : Patika.getList()) {
            patikaPage_row[0] = obj.getID();
            patikaPage_row[1] = obj.getName();
            model_patikaPage.addRow(patikaPage_row);
        }
        loadPatikaComboBox();
    }

    public void loadPatikaComboBox() {
        comboBox_course_name.removeAllItems();
        comboBox_course_name.addItem(null);
        for (Patika obj : Patika.getList()) {
            comboBox_course_name.addItem(new Item(obj.getID(), obj.getName()));
        }
    }

    public void loadEducatorComboBox() {
        comboBox_teacher_name.removeAllItems();
        comboBox_teacher_name.addItem(null);

        for (User obj : User.getList()) {
            if (obj.getType().equals("King")) {
                comboBox_teacher_name.addItem(new Item(obj.getId(), obj.getName()));
            }
        }
    }


    public static void main(String[] args) {
        Helper.setLayout();
        Operator operator = new Operator(1, "Ragnar Lothbrok", "hans_landa", "1872",
                "Walhalla");
        OperatorGUI operatorGUI = new OperatorGUI(operator);

    }

}
