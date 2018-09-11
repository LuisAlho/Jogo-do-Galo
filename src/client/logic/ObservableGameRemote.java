/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.logic;

import gestao.GestaoRemoteInterface;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author Nasyx
 */
public class ObservableGameRemote extends UnicastRemoteObject implements ObservableGameRemoteInterface {

    Registry r;
    String serviceName;
    ObservableGame game;
    
    public ObservableGameRemote(ObservableGame game) throws RemoteException{
        super();
        this.game = game;
    }
    
    //public void startRemoteService(String username){
    
        /*
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
            System.out.println("Servico vista criado e em execucao ("+this.getRef().remoteToString()+"...");

            // Regista o servico para que os clientes possam encontra'-lo, ou seja,
            // obter a sua referencia remota (endereco IP, porto de escuta, etc.).
            serviceName = "Gestao_+" + username;
            r.bind(serviceName, this);
            System.out.println("Servico " + serviceName + "  registado no registry...");

            }catch(RemoteException e){
                System.out.println("Erro remoto - " + e);
                System.exit(1);
            }catch(Exception e){
                System.out.println("Erro - " + e);
                System.exit(1);
            }*/
    //};
    
    //public ObservableGameRemote getRemoteObject(){
    //    return this;
    //}
    

    @Override
    public void setListUsers(List<util.model.Player> player) throws RemoteException {
        System.out.println("List users: " + player.toString());
        game.getUsersList(player);
        
    }


}
