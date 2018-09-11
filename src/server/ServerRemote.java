/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Nasyx
 */
public class ServerRemote extends UnicastRemoteObject implements ServerRemoteInterface {

    public ServerRemote() throws RemoteException{
        super();
    }
    
}
