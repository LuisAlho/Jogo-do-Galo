package server;

import gestao.Gestao;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends UnicastRemoteObject implements ServerRemoteInterface {
    
    private final String gestaoIp;


    public static void main(String [] args) {
        
        String ip = "localhost";
        
        if(args.length != 1){
            System.out.println("Invalid arguments! \nEx: java Server 'ip_gestao'");
            System.exit(0);
        }else{ 
            ip = args[0];
        }
        try {
            
            Server server = new Server(ip);
            server.start();
            
        } catch (RemoteException ex) {
            System.err.println("Error locating RMI registry!\nExit from Server....");
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        } catch (NotBoundException ex) {
            System.err.println("Error finding remote service!\nExit from Server....");
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Server(String ip) throws RemoteException {
        super();
        this.gestaoIp = ip;
    }
    
    public void start() throws RemoteException, NotBoundException{
        System.out.println("Starting server....");
        
        Registry registry = LocateRegistry.getRegistry(this.gestaoIp);
        
        Gestao gestaoRemote = (Gestao) registry.lookup("Gestao");
        
    }

    @Override
    public String add() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

