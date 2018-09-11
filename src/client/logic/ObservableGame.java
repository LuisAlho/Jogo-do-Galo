
package client.logic;

import java.util.Observable;
import client.logic.states.IStates;
import gestao.Gestao;
import gestao.GestaoRemoteInterface;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import util.Message;

/** 
 * @author Jose Marinho
 *
 */

public class ObservableGame extends Observable
{
    String ip;
    GameModel gameModel;
    GestaoRemoteInterface gestao;
    ObservableGameRemote remote;
    Socket socket;
    String username;
    
    
    
    public ObservableGame(String ip)
    {
        this.ip = ip;
        // add service gestao

        try {
            String objectUrl = "rmi://localhost/Gestao";
            gestao = (GestaoRemoteInterface)Naming.lookup(objectUrl);
                 
        } catch (NotBoundException ex) {
            Logger.getLogger(ObservableGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ObservableGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ObservableGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //TODO connect to server
 
        //startConnectionToServer();

        
        gameModel = new GameModel();
    }

    public GameModel getGameModel()
    {
        //TODO procurar o game model no rmi
        
        return gameModel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public void startConnectionToServer(){
    
        Message msg;
        try {
            socket = new Socket(ip, 4555);
            //socket.setSoTimeout(2000);

            msg = new Message();
            msg.setText("Ola");
            msg.setType("INIT");
            msg.setUsername(username);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            out.writeObject(msg);
            out.flush();

        } catch (IOException ex) {
            Logger.getLogger(ObservableGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void startRemoteObject(String username){
        try {
            remote = new ObservableGameRemote(this);
            //remote.startRemoteService(username);
            
            System.out.println("Start Remote Object: " + ((ObservableGameRemoteInterface)remote));
            gestao.getRemoteObject((ObservableGameRemoteInterface)remote, username);
            System.out.println("End Remote Object");
                        
        } catch (RemoteException ex) {
            System.out.println("Error get observable remote object: " + ex);
        }
    }
    
    public void getUsersList(List<util.model.Player> p){
        
        setChanged();
        notifyObservers(p);
    }
    
    public GestaoRemoteInterface getServiceGestao()
    {
        return gestao;
    }

    public void setGameModel(GameModel gameModel)
    {        
        this.gameModel = gameModel;
        
        setChanged();
        notifyObservers();
    }   
    
    public GameData getGameData() {
        
        System.out.println("GET GAMEDATA OBSERVABLE");
        GameData msg;
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            msg = (GameData)in.readObject();
            
            return msg;
            
        } catch (IOException ex) {
            Logger.getLogger(ObservableGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ObservableGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public IStates getState()
    {
        return gameModel.getState();
    }        
    
     // Methods retrieve data from the game
    
    public String gridToString()
    {
        return gameModel.gridToString();
    }                    

    public int getNumPlayers()
    {
        return gameModel.getNumPlayers();
    }
    
    public Player getCurrentPlayer() 
    {
        return gameModel.getCurrentPlayer();
    }

    public Player getNotCurrentPlayer() 
    {
        return gameModel.getNotCurrentPlayer();
    }
    
    public Player getPlayer1()
    {
        return gameModel.getPlayer1();
    }

    public Player getPlayer2()
    {
        return gameModel.getPlayer2();
    }

    public Token getToken(int line, int column) 
    {
        return gameModel.getToken(line, column);
    }
    
    public String grelhaToString()
    {
        return gameModel.gridToString();
    }

    public int getNumCurrentPlayer()
    {
        return gameModel.getNumCurrentPlayer();
    }

    public String getCurrentPlayerName()
    {
        return gameModel.getCurrentPlayerName();
    }
    
    public boolean isOver() 
    {
        return gameModel.isOver();
    }
    
    public boolean hasWon(Player player) 
    {
        return gameModel.hasWon(player);
    }
    
    // Methods that are intended to be used by the user interfaces and that are delegated in the current state of the finite state machine 
    
    public void setNumberPlayers(int num)
    {
        gameModel.setNumberPlayers(num);
        
        setChanged();
        notifyObservers();
    }

    public void setPlayerName(int num, String name) 
    {
        gameModel.setPlayerName(num, name);
        
        setChanged();
        notifyObservers();
    }

    public void startGame(String player)
    {
   
        Message msg;
       
        try {
            System.out.println("Socket Open: " + socket.isConnected());
            
            msg = new Message();
            msg.setText("Ola");
            msg.setType("INVITE");
            msg.setUsername(username);
            msg.setPlayer(player);                    
            
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            
            out.writeObject(msg);
            out.flush();
            
            
            System.out.println("Waiting Response: ");
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            msg = (Message)in.readObject();
            System.out.println("Message response: " + msg.getType());
            JOptionPane.showMessageDialog(null, "Received Invite " + msg.getUsername());
            
            
            msg = new Message();
            msg.setText("Ola");
            msg.setType("ACCEPTED");
            msg.setUsername(username);
            msg.setPlayer(player);
            
            out = new ObjectOutputStream(socket.getOutputStream());
            
            out.writeObject(msg);
            out.flush();
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(ObservableGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ObservableGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //gameModel.startGame();

        //setChanged();
        //notifyObservers();
        
    }

    public void placeToken(int line, int column)
    {
        gameModel.placeToken(line, column);
        
        setChanged();
        notifyObservers();
    }

    public void returnToken(int line, int column)
    {
        gameModel.returnToken(line, column);
        
        setChanged();
        notifyObservers();
    }

    public void quit()
    {
        gameModel.quit();
        
        setChanged();
        notifyObservers();
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


}
