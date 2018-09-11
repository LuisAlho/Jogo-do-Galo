package server;

import gestao.GestaoRemoteInterface;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Message;

public class Server extends Thread {
    
    private final String gestaoIp;
    private GestaoRemoteInterface gestao;
    private Socket clientSocket;
    private Map<String, Player> mapUsers = new HashMap();
    private Map<String, Socket> mapSockets = new HashMap();


    
    
    public Server(String ip, Socket socket) throws RemoteException, NotBoundException {
        
        this.clientSocket = socket;
        this.gestaoIp = ip;
        
        //Lookup for remote service gestao
        Registry registry = LocateRegistry.getRegistry(this.gestaoIp);

        GestaoRemoteInterface gestaoRemote = (GestaoRemoteInterface) registry.lookup("Gestao");
    }
    

    @Override
    public void run() {
        
        try {
            System.out.println("Receive player...");
            
            while(true){
            
                ObjectInputStream is = new ObjectInputStream(clientSocket.getInputStream());

                Message msg = (Message)is.readObject();
                System.out.println("Message: " + msg.getType());

                switch( msg.getType()){

                    case "INIT":
                        System.out.println("Ip: " + clientSocket.getInetAddress());
                        System.out.println("Port: " + clientSocket.getPort());
                        System.out.println("User: " + msg.getUsername());
                        this.mapUsers.put(msg.getUsername(), new Player(msg.getUsername(), clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort()) );
                        this.mapSockets.put(msg.getUsername(), clientSocket);
                        break;

                    case "INVITE":
                        System.out.println("Player to invite: " + msg.getPlayer());
                        Socket socket = this.mapSockets.get(msg.getPlayer());
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        Message msg2 = new Message();
                        msg2.setType("INVITE");
                        msg2.setUsername(msg.getPlayer());
                        out.writeObject(msg2);
                        break;
                        
                    case "ACCEPTED":
                        
                        System.out.println("Player to accepted: " + msg.getUsername());
                        
                        GameModel game = new GameModel(mapSockets.get(msg.getUsername()), mapSockets.get(msg.getPlayer()));
                        game.start();
                        break;

                    case "START": 

                        break;

                    default: break;

                }
            }
            
            
        } catch (IOException ex) {
            System.out.println("Error IO: " + ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public static void main(String [] args)  {
        
        String ip = "localhost";
        ServerSocket socket;
        Socket clientSocket;
        
        
        
        if(args.length != 1){
            System.out.println("Invalid arguments! \nEx: java Server 'ip_gestao'\n");
            System.out.println("Get default values to IP: localhost\n");
        }else{ 
            ip = args[0];
        }
        
        System.out.println("Starting server....");
        
        try {
            //Listen to GUI to connecto to server service throws socket TCP
            //Wait for two players to connect before launch game

            socket = new ServerSocket(4555);
            
            while(true){
                clientSocket = socket.accept();
                
                new Server(ip, clientSocket).start();
                
            }

        } catch (IOException ex) {
            System.out.println("Error open server socket... " + ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }

}

