package Model;

import Helper.DBconnecter;
import View.Patika;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Course {

    private int patika_id;
    private String name;
    private String language;
    private Patika patika;
    private User educator;
    private int id;
    private int user_id;


    public Course(int id, int user_id, int patika_id, String name, String language) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.language = language;
        this.patika = Patika.getFetch(patika_id);
        this.educator = User.getFetch(user_id);
    }


    public static ArrayList<Course> getList() {
        ArrayList<Course> courseArray = new ArrayList<>();
        try {
            Statement statement = DBconnecter.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM course ORDER BY id");
            Course course = null;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int user_id = resultSet.getInt("user_id");
                int patika_id = resultSet.getInt("patika_id");
                String name = resultSet.getString("name");
                String language = resultSet.getString("language");
                course = new Course(id,user_id,patika_id,name,language);
                courseArray.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseArray;
    }

    public static ArrayList<Course> getListbyUser(int user_id) {
        ArrayList<Course> courseArray = new ArrayList<>();
        try {
            Statement statement = DBconnecter.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM course WHERE user_id ="+user_id);
            Course course = null;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userID = resultSet.getInt("user_id");
                int patika_id = resultSet.getInt("patika_id");
                String name = resultSet.getString("name");
                String language = resultSet.getString("language");
                course = new Course(id,userID,patika_id,name,language);
                courseArray.add(course);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courseArray;
    }



    public static boolean add(int user_id,int patika_id,String name,String language) {
        String query = "INSERT INTO course(user_id,patika_id,name,language) VALUES(?,?,?,?)";
        try {
            PreparedStatement preparedStatement = DBconnecter.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,user_id);
            preparedStatement.setInt(2,patika_id);
            preparedStatement.setString(3,name);
            preparedStatement.setString(4,language);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM course WHERE id = ?";
        try {
            PreparedStatement deleteMember = DBconnecter.getInstance().prepareStatement(query);
            deleteMember.setInt(1, id);
            return deleteMember.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public void setEducator(User educator) {
        this.educator = educator;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public Patika getPatika() {
        return patika;
    }

    public User getEducator() {
        return educator;
    }
}
