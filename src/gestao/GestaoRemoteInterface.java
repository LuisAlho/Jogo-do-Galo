/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestao;

import client.logic.ObservableGameRemote;
import client.logic.ObservableGameRemoteInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.rmi.server.RemoteRef;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import util.model.Player;

/**
 *
 * @author luisalho
 */
public interface GestaoRemoteInterface extends Remote {

    boolean login(String user, String password) throws RemoteException;
    
    boolean registerUser(String user, String password, String name) throws RemoteException;
    
    void getLoggedInPlayers() throws RemoteException;
    
    String getBDServerIp() throws RemoteException;
    
    public boolean logoutUser(String username) throws RemoteException;
    
    public void getRemoteObject(ObservableGameRemoteInterface remoteObject, String username) throws RemoteException;
    
}
