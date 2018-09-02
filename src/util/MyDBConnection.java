package util;

import util.model.Player;
import java.sql.*;
import java.util.List;

public class MyDBConnection {

    private final static String urlBD = "127.0.0.1";
    private final static int  port = 3306;
    private final static  String nameDB = "myDB";

    private static MyDBConnection instance;

    private Player player;

    private Connection connection;

    public MyDBConnection(){
        connection = connectToDB(urlBD, port, nameDB);
    }

    public static MyDBConnection getInstance() {
        if (instance == null){
            instance = new MyDBConnection();
        }
        return instance;
    }

    private Connection connectToDB(String url, int port, String name){

        //TODO create database connection
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
