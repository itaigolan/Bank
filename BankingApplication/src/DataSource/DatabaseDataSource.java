package DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseDataSource extends DataSource{
    
    final private String databaseUrl;
    private Connection con;
    
    public DatabaseDataSource(String databaseUrl) {
        super("Database");
        this.databaseUrl = databaseUrl;
    }
    
    //Connect to database
    private void establishConnection(){ 
        try {
            con = DriverManager.getConnection(databaseUrl,"root","mysql");
        } catch (Exception in) {
            System.out.println(databaseUrl + " not found.");
        }
            
    }
    
    //Close database connection
    public void closeConnection(){
        try {
            con.close();
        } catch (SQLException ex) {
            System.out.println(databaseUrl + " connection was not established.");
        }
    }
    
    public Connection getConnection(){
        return con;
    }
}
