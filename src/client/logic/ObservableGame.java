
package client.logic;

import java.util.Observable;
import client.logic.states.IStates;
import gestao.GestaoRemoteInterface;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 * @author Jose Marinho
 *
 */

public class ObservableGame extends Observable
{
    GameModel gameModel;
    GestaoRemoteInterface gestao;
    
    public ObservableGame()
    {
        
        //TODO procurar o servidor de gestao
        /*
        try{
            
            Registry r;
            
            try{
                
                r = LocateRegistry.getRegistry();          
                System.out.println("Registry lancado!");
                                
            }catch(RemoteException e){
                System.out.println("Registry provavelmente ja' em execucao!");
                
            }
            
            // Cria e lanca o servico,
            
            RemoteTimeService timeService = new RemoteTimeService();
            
            System.out.println("Servico RemoteTime criado e em execucao ("+timeService.getRef().remoteToString()+"...");
            
            // Regista o servico para que os clientes possam encontra'-lo, ou seja,
            // obter a sua referencia remota (endereco IP, porto de escuta, etc.).
             
            r.bind("RemoteTime", timeService);     
                   
            System.out.println("Servico RemoteTime registado no registry...");
            
        }catch(RemoteException e){
            System.out.println("Erro remoto - " + e);
            System.exit(1);
        }catch(Exception e){
            System.out.println("Erro - " + e);
            System.exit(1);
        }     */  
        
        //TODO add ervice gestao
                    
            
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
        
        
        gameModel = new GameModel();
    } 

    public GameModel getGameModel()
    {
        //TODO procurar o game model no rmi
        
        return gameModel;
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
        return gameModel.getGameData();
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

    public void startGame()
    {
        gameModel.startGame();
        
        setChanged();
        notifyObservers();
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


}
