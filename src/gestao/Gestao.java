package gestao;


import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.MyDBConnection;

public class Gestao extends UnicastRemoteObject implements GestaoRemoteInterface {
    
    private final int regestryPort = 1050;
    private int port;
    private String url;
    
    private MyDBConnection db;
    private Registry registry;
    
    
    /**
     * Function main
     * 
     * @param args -> port
     */
    public static void main(String [] args) {
        
        int port = 1050;
        String url = "localhost";
        
        if(args.length != 2){
            System.out.println("Invalid arguments! \nEx: java Gestao 'port'");
            System.exit(0);
        }else{ 
            url = args[0];
            port = Integer.parseInt(args[1]);
        }

        Gestao gestao;
        try {
            gestao = new Gestao(url, port);
            
            if(!gestao.start()){
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
     */
    public Gestao(String url, int port) throws RemoteException{
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
        
        
        try {
            //Create registry
            LocateRegistry.createRegistry(regestryPort);
            
            //locate registry
            registry = LocateRegistry.getRegistry("localhost");
            
            //Start DB connection
            db = MyDBConnection.getInstance(url, port);
            
            //Bind remote service to registry
            registry.bind("Gestao", this);
            
            
            
        } catch (RemoteException ex) {
            ex.printStackTrace();
            Logger.getLogger(Gestao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (AlreadyBoundException ex) {
            Logger.getLogger(Gestao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    @Override
    public int add() throws RemoteException {
        
        throw new UnsupportedOperationException("Not supported yet."); 
        //To change body of generated methods, choose Tools | Templates.
    }
}
