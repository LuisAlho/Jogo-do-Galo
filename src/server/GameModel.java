package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import server.states.IStates;
import server.states.AwaitBeginning;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jose Marinho
 */

public class GameModel extends Thread implements Serializable
{
    
    private GameData gameData;
    private IStates state;
    private Socket player1, player2;
    ObjectOutputStream out;
    ObjectInputStream in;
    
    public GameModel(Socket player1, Socket player2)
    {
        this.player1 = player1;
        this.player2 = player2;
        
        gameData = new GameData();
        setState(new AwaitBeginning(gameData));
    }

    //public GameData getGameData() {
    public void getGameData() {
        
        try {
            out = new ObjectOutputStream(player1.getOutputStream());
            out.writeObject(gameData);
            out = new ObjectOutputStream(player2.getOutputStream());
            out.writeObject(gameData);
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(GameModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //return gameData;
    }

    public void setGameData(GameData gameData)
    {
        this.gameData = gameData;
    }

    public IStates getStateGame()
    {
        return state;
    }
    
    private void setState(IStates state)
    {
        this.state = state;
    }        
    
     // Methods retrieve data from the game
    
    public String gridToString()
    {
        return gameData.gridToString();
    }                    

    public int getNumPlayers()
    {
        return gameData.getNumPlayers();
    }
    
    public Player getCurrentPlayer() 
    {
        return gameData.getCurrentPlayer();
    }

    public Player getNotCurrentPlayer() 
    {
        return gameData.getNotCurrentPlayer();
    }
    
    public Player getPlayer1()
    {
        return gameData.getPlayer1();
    }

    public Player getPlayer2()
    {
        return gameData.getPlayer2();
    }

    public Token getToken(int line, int column) 
    {
        return gameData.getToken(line, column);
    }
    
    public String grelhaToString()
    {
        return gameData.gridToString();
    }

    public int getNumCurrentPlayer()
    {
        return gameData.getNumCurrentPlayer();
    }

    public String getCurrentPlayerName()
    {
        return gameData.getCurrentPlayer().getName();
    }
    
    public boolean isOver() 
    {
        return gameData.isOver();
    }
    
    public boolean hasWon(Player player) 
    {
        return gameData.hasWon(player);
    }
    
    // Methods that are intended to be used by the user interfaces and that are delegated in the current state of the finite state machine 
    
    public void setNumberPlayers(int num)
    {
        setState(getStateGame().setNumberPlayers(num));
    }

    public void setPlayerName(int num, String name) 
    {
        setState(getStateGame().setName(num, name));
    }

    public void startGame()
    {
        setState(getStateGame().startGame());
    }

    public void placeToken(int line, int column)
    {
        setState(getStateGame().placeToken(line, column));
    }

    public void returnToken(int line, int column)
    {
        setState(getStateGame().returnToken(line, column));
    }

    public void quit()
    {
        setState(getStateGame().quit());
    }

}
