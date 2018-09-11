package util;

import util.model.Player;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyDBConnection implements java.sql.Driver{
    
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    //private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:MKYONG";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    private final static  String nameDB = "pd2018";
    private static MyDBConnection instance;
    private Player player;
    private Connection connection;

    public MyDBConnection(String urlBD, int port){
        connection = connectToDB(urlBD, port, this.nameDB);
    }

    public static MyDBConnection getInstance(String urlBD, int port) {
        if (instance == null){
            instance = new MyDBConnection(urlBD, port);
        }
        return instance;
    }

    private Connection connectToDB(String url, int port, String bdName){
        
        Connection dbConnection = null;
        
        //jdbc:mysql://localhost/test?" +"user=minty&password=greatsqldb"
        
        String DB_CONNECTION = "jdbc:mysql://" + url +"/"+ bdName+"?user=" + DB_USER + "&password=" + DB_PASSWORD ;
        System.out.println(DB_CONNECTION);
        try {
            Class.forName(DB_DRIVER).newInstance();
        } catch (ClassNotFoundException e) {
            System.out.println("No driver found.... baddd");
            System.out.println(e.getMessage());
        } catch (InstantiationException ex) {
            Logger.getLogger(MyDBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MyDBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("Conncetion estabelished");
        return dbConnection;


    }
    
    public Player playerLogin(String username, String password) throws SQLException {
        
        Player p =  new Player();
        
        String selectTableSQL = "SELECT idplayer, password, name, username FROM player WHERE (password like '" + password + "') AND (username like '" + username + "')";
        System.out.println(selectTableSQL);
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(selectTableSQL);
        if (!rs.isBeforeFirst() ) { 
            System.out.println("No data"); 
            return null;
        }
        
        while (rs.next()) {
            p.setName(rs.getString("name"));
            p.setUsername(rs.getString("username"));
        }
        
        if(this.setUserLoggedIn(username)){
            System.out.println("Login: " + p.toString());
            p.setLoggedIn(1);
            return p;
        }
        return null;
    }
    
    public boolean playerLogout(String username){
        
        try {
            String sql = "UPDATE player SET loggedIn = 0 WHERE username like '" + username + "'";
            
            Statement statement = connection.createStatement();
            int rs = statement.executeUpdate(sql);
            if(rs == 0) 
                return false;
            return true;
        } catch (SQLException ex) {
            System.out.println("error updating table player.. :" + ex);
            return false;
        }
    }
    
    public boolean registerPlayer(String username, String password, String name) throws SQLException {
        
        //TODO insert player on database
        String selectTableSQL = "INSERT INTO player(name, username, password) VALUES (?,?,?,?)" ;

        PreparedStatement pst = connection.prepareCall(selectTableSQL);
        pst.setString(1, name);
        pst.setString(2, username);
        pst.setString(3, password);
        pst.setInt(4, 1);

        int rs = pst.executeUpdate();
        if(rs == 0) 
            return false;
        return true;
    }
    
    private boolean setUserLoggedIn(String username){
        
        try {
            String sql = "UPDATE player SET loggedIn = 1 WHERE username like '" + username + "'";
            
            Statement statement = connection.createStatement();
            int rs = statement.executeUpdate(sql);
            if(rs == 0) 
                return false;
            return true;
        } catch (SQLException ex) {
            System.out.println("error updating table player.. :" + ex);
            return false;
        }
    }

    public List<Player> listUsers(boolean loggedIn){
        
        
        List listUser = new ArrayList<Player>();
        Player p = new Player();

        if(loggedIn){
            try {
                //get all users loggedIn
                String sql = "SELECT idplayer, name, username, loggedIn FROM player WHERE loggedIn = 1";
                //String sql = "SELECT * FROM player";
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                System.out.println("RS List logged users: " + rs);                
                if (!rs.isBeforeFirst() ) { 
                    System.out.println("No data"); 
                    return null;
                }
                
                while (rs.next()) {
                    p.setName(rs.getString("name"));
                    p.setUsername(rs.getString("username"));
                    p.setLoggedIn(rs.getInt("loggedIn"));
                    System.out.println("RS Player: " + p.toString());
                    listUser.add(p);
                    p = new Player();
                }                
                return listUser;
            } catch (SQLException ex) {
                Logger.getLogger(MyDBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }else{
            //TODO get list of all users
        }
        
        //TODO create query to list all users on DB
        return null;
    }

    private Player getUser(int id){

        //TODO create query to get informatuion of one user
        return null;
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getMajorVersion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getMinorVersion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean jdbcCompliant() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
