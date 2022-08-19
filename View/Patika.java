package View;

import Helper.DBconnecter;
import Model.Course;

import java.sql.*;
import java.util.ArrayList;

public class Patika {
    private int ID;
    private String name;

    public Patika(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<Patika> getList() {
        ArrayList<Patika> patikaArrayList = new ArrayList<>();
        Patika obj;

        try {
            Statement st = DBconnecter.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM persons ORDER BY id");

            while (rs.next()) {
                obj = new Patika(rs.getInt("id"), rs.getString("name"));
                patikaArrayList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patikaArrayList;
    }

    public static boolean add(String name) {
        String query = "INSERT INTO persons(name) VALUES (?)";
        try {
            PreparedStatement preparedStatement = DBconnecter.getInstance().prepareStatement(query);
            preparedStatement.setString(1, name);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean update(int id, String name) {
        String query = "UPDATE persons SET name = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = DBconnecter.getInstance().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);

            return preparedStatement.executeUpdate() != -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Patika getFetch(int id) {
        Patika obj = null;
        String query = "SELECT * FROM persons WHERE id = ?";

        try {
            PreparedStatement preparedStatement = DBconnecter.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                obj = new Patika(rs.getInt("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM persons WHERE id = ?";
        ArrayList<Course> courseArrayList = Course.getList();
        courseArrayList.stream().forEach(course -> {
            if (course.getPatika().getID() == id) {
                Course.delete(course.getId());
            }
        });
        try {
            PreparedStatement preparedStatement = DBconnecter.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
