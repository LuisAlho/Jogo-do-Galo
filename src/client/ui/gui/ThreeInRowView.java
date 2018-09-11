package client.ui.gui;

import client.logic.ObservableGame;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;



public class ThreeInRowView extends JFrame implements Observer
{
    ObservableGame game;
    ThreeInRowGamePanel panel;
    
   
    public ThreeInRowView(ObservableGame j, String username)
    {
        super("Three in a row");
        
        this.setLocationRelativeTo(null);

        game = j;            
        game.addObserver(this);
        game.startRemoteObject(username);
        game.setUsername(username);
        game.startConnectionToServer();

        panel=new ThreeInRowGamePanel(game, username);
  
        addComponents();
 
        setVisible(true);
        this.setSize(700, 500);
        this.setMinimumSize(new Dimension(650, 450));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        validate();
        
        try {
            game.getServiceGestao().getLoggedInPlayers();
        } catch (RemoteException ex) {
            System.out.println("Error getting users: " + ex);
        }
        
        
    }
  
    
    private void addComponents()
    {
        Container cp = getContentPane();
        
        cp.setLayout(new BorderLayout());
        cp.add(panel,BorderLayout.CENTER);
        
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
