package gestao;


import client.logic.ObservableGameRemote;
import client.logic.ObservableGameRemoteInterface;
import java.rmi.Remote;
import util.model.Player;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteRef;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.MyDBConnection;

public class Gestao extends UnicastRemoteObject implements GestaoRemoteInterface {

    private final int regestryPort = 1050;
    private int port;
    private String url;

    private MyDBConnection db;
    
    Map<String, ObservableGameRemoteInterface> mapUsersRemoteObject = new HashMap();
    
    //Lista de servidores de jogo
    //so deve de guardar o mais antigo??
    //private List serversList;

    public Registry r;
    /**
     * Function main
     *
     * @param args -> port
     */
    public static void main(String[] args) {

        int port = 3306;
        String url = "localhost";
/*
        if (args.length != 2) {
            System.out.println("Invalid arguments! \nEx: java Gestao 'port'");
            System.exit(0);
        } */
        //url = args[0];
        //port = Integer.parseInt(args[1]);
        
        //inici o servico de gestao
        Gestao gestao;
        try {
            gestao = new Gestao(url, port);

            if (!gestao.start()) {
                System.out.println("Error starting service gestao...");
                System.exit(0);
            }

        } catch (RemoteException ex) {
            System.out.println("Error starting service gestao!\nExit service....");
            Logger.getLogger(Gestao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Constructor class Gestao
     *
     * @param url -> Database address
     * @param port -> Database port
     * @throws java.rmi.RemoteException
     */
    public Gestao(String url, int port) throws RemoteException {
        super();
        this.port = port;
        this.url = url;
    }

    /**
     * Start service Gestao
     *
     * @return true if success, false if something went wrong
     */
    public boolean start() {
        System.out.println("Iniciar serividor de gestao...");

        try{
            //Init registry
            try{

            System.out.println("Tentativa de lancamento do registry no porto " + Registry.REGISTRY_PORT + "...");
            r = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
            System.out.println("Registry lancado!");

            }catch(RemoteException e){
                System.out.println("Registry provavelmente ja' em execucao!");
                r = LocateRegistry.getRegistry();
            
            }
            
            // Cria e lanca o servico,
            System.out.println("Servico gestao criado e em execucao ("+this.getRef().remoteToString()+"...");

            // Regista o servico para que os clientes possam encontra'-lo, ou seja,
            // obter a sua referencia remota (endereco IP, porto de escuta, etc.).

            r.bind("Gestao", this);     
            System.out.println("Servico RemoteTime registado no registry...");
            
            //get reference to MySQLDataBase
            db = MyDBConnection.getInstance(url, port);

            }catch(RemoteException e){
                System.out.println("Erro remoto - " + e);
                System.exit(1);
            }catch(Exception e){
                System.out.println("Erro - " + e);
                System.exit(1);
            }     

        return true;
    }

    private void notifyLoggedUsers(List<Player> players){
        
        System.out.println("List to send: " + players.toString());
    
        if(!this.mapUsersRemoteObject.isEmpty()){
            
            mapUsersRemoteObject.entrySet().forEach((entry) -> {
                try {
                    System.out.println(entry.getKey() + "/" + entry.getValue());
                    
                    entry.getValue().setListUsers(players);
                } catch (RemoteException ex) {
                    System.out.println("Remote Exception: " + ex);
                }
            });
        }
        
    }
    
    @Override
    public boolean login(String username, String password) throws RemoteException {
        
        try {
            Player p = db.playerLogin(username, password);
            System.out.println("Player login: " + p);
            if (p != null){
                return true;
            }
            return false;
            
        } catch (SQLException ex) {
            Logger.getLogger(Gestao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    @Override
    public boolean logoutUser(String username) throws RemoteException {
        
        this.mapUsersRemoteObject.remove(username);
        return db.playerLogout(username);

    }
    
    @Override
    public String getBDServerIp() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean registerUser(String user, String password, String name) throws RemoteException {
        
        try {
            System.out.println("Register user");
            
            db.registerPlayer(user, password, name);
            return true;
            
        } catch (SQLException ex) {
            System.out.println("Error inserting on DB..." + ex);
        }
        return false;
    }

    @Override
    public void getLoggedInPlayers() throws RemoteException {
        
        this.notifyLoggedUsers(db.listUsers(true));
    }

    @Override
    public void getRemoteObject(ObservableGameRemoteInterface remoteObject, String username) throws RemoteException {

        try{   
            this.mapUsersRemoteObject.put(username, remoteObject);
        }catch(Exception ex){
            System.out.println("Error casting remote object: " + ex);
        } 
    }

   
    
    
}
