package Infrastructure.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectionBuilder
{
    private  String URL= "";
    private  String USER = "";
    private  String PASSWORD = "";

    public  Connection build()
    {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }catch (java.sql.SQLException ex){
            System.out.println("Connection Failed: " + ex.getMessage());
            return null;
        }
    }

    public DatabaseConnectionBuilder withUrl(String url) {
        this.URL = url;
        return  this;
    }
    public DatabaseConnectionBuilder withUser(String user){
        this.USER = user;
        return  this;
    }

    public DatabaseConnectionBuilder withPassword(String password){
        this.PASSWORD = password;
        return  this;
    }
}
