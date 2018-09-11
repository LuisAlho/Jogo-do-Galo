/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.logic;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Alho
 */
public interface ObservableGameRemoteInterface extends Remote {
    
    public void setListUsers(List<util.model.Player> player) throws RemoteException;
    
}
