package Model;

import Helper.DBconnecter;
import Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String user_name;
    private String password;
    private String type;

    public User() {
    }

    public User(int id, String name, String user_name, String password, String type) {
        this.id = id;
        this.name = name;
        this.user_name = user_name;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //----------------------------------- SET GET METHODS && CONSTRUCTORS ----------------------------------------

    public static ArrayList<User> getList() {
        ArrayList<User> usersList = new ArrayList<>();
        try {
            Statement st = DBconnecter.getInstance().createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM usertype ORDER BY id");
            User user;
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUser_name(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getString("type"));
                usersList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public static boolean add(String name, String user_name, String password, String type) {

        User findUser = User.getFetch(user_name);

        if (!Objects.isNull(findUser)) {
            Helper.showMessage("username is already exist.");
            return false;
        }

        String query = "INSERT INTO usertype(name,username,password,type) VALUES (?,?,?,?)";
        boolean key = false;
        try {
            PreparedStatement preparedStatement = DBconnecter.getInstance().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, user_name);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, type);

            key = preparedStatement.executeUpdate() != -1;
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return key;
    }

    public static User getFetch(String username) {
        User obj = null;
        String query = "SELECT * FROM usertype WHERE username = ?";

        try {
            PreparedStatement preparedStatement = DBconnecter.getInstance().prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUser_name(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static User getFetch(int id) {
        User obj = null;
        String query = "SELECT * FROM usertype WHERE id = ?";

        try {
            PreparedStatement preparedStatement = DBconnecter.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUser_name(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static Operator getFetch(String uname,String password) {
        Operator obj = null;
        String query = "SELECT * FROM usertype WHERE username = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = DBconnecter.getInstance().prepareStatement(query);
            preparedStatement.setString(1, uname);
            preparedStatement.setString(2,password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                obj = new Operator(rs.getInt("id"),rs.getString("name"),rs.getString("username"),rs.getString("password"),rs.getString("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  obj;
    }

    public static boolean delete(int id) {
        String query = "DELETE FROM usertype WHERE id = ?";
        try {
            PreparedStatement deleteMember = DBconnecter.getInstance().prepareStatement(query);
            deleteMember.setInt(1, id);
            ArrayList<Course> courseArrayList = Course.getListbyUser(id);
            courseArrayList.stream().forEach(course ->
                    Course.delete(course.getId()));
            return deleteMember.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public static boolean update(String name, String user_name, String password, String type, int id) {
        String query = "UPDATE usertype SET name=?,username=?,password=?,type=? WHERE id=?";
        User findUser = User.getFetch(user_name);
        if (!Objects.isNull(findUser) && findUser.getUser_name().equals("name")) {
            Helper.showMessage("This username is already exist!");
            return false;
        }

        try {
            PreparedStatement preparedStatement = DBconnecter.getInstance().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, user_name);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, type);
            preparedStatement.setInt(5, id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static String searchQuery(String name, String username, String type) {

        String query = "SELECT * FROM usertype WHERE username ILIKE '%{{username}}%'" +
                " AND name ILIKE '%{{name}}%' ";

        query = query.replace("{{username}}", username);
        query = query.replace("{{name}}", name);
        if (!type.isEmpty()) {
            query += " AND type='{{type}}'";
            query = query.replace("{{type}}", type);
        }

        System.out.println(query);

        return query;
    }

    public static ArrayList<User> searchUserList(String query) {
        ArrayList<User> serachList = new ArrayList<>();
        try {
            Statement st = DBconnecter.getInstance().createStatement();
            ResultSet resultSet = st.executeQuery(query);
            User user;
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUser_name(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getString("type"));
                serachList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serachList;
    }

}
