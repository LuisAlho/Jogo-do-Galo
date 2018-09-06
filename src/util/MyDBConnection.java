package util;

import util.model.Player;
import java.sql.*;
import java.util.List;

public class MyDBConnection {

    private final static  String nameDB = "myDB";

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

    private Connection connectToDB(String url, int port, String name){

        //TODO create database connection
        return null;
    }
    
    public Player searchPlayerLogin(String name, String password){
        
        //no player found with that credentials
        return null;
    }

    private List<Player> listUsers(){

        //TODO create query to list all users on DB
        return null;
    }

    private List<Player> getUser(int id){

        //TODO create query to get informatuion of one user
        return null;
    }

}
