/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestao;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author luisalho
 */
public interface GestaoRemoteInterface extends Remote {

    int add() throws RemoteException;
    
}
