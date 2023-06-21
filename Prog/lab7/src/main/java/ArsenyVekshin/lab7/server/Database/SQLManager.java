package ArsenyVekshin.lab7.server.Database;

import java.sql.*;

public class SQLManager {
    private final String url = "jdbc:postgresql://localhost:5432/studs";
    private final String user = "s367133";
    private String password = "default";

    public SQLManager() {}

    public void setDatabasePass(String pass){
        password = pass;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
    public boolean send(PreparedStatement sql) {
        try {
            sql.executeUpdate();
            sql.getConnection().close();
        } catch(SQLException ex) {
            return false;
        }
        return true;
    }
    public boolean sendRaw(String sql) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            connection.close();
        } catch(SQLException ex) {
            return false;
        }
        return true;
    }

    public ResultSet get(PreparedStatement sql) {
        ResultSet result = null;
        try {
            result = sql.executeQuery();
            sql.getConnection().close();
        } catch (SQLException ex) {}
        return result;
    }

    public ResultSet getRaw(String sql) {
        ResultSet result = null;
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            result = statement.executeQuery(sql);
            connection.close();
        } catch (SQLException ex) {}
        return result;
    }
}
