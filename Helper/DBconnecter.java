package Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static Helper.Config.DB_URL;

public class DBconnecter {
    private Connection connect = null;

    private Connection connection() {
        try {
            this.connect = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connect;
    }

    public static Connection getInstance(){
        DBconnecter dBconnecter = new DBconnecter();
        return dBconnecter.connection();
    }

}

