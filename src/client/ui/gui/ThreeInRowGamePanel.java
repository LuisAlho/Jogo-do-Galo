package client.ui.gui;

import client.logic.ObservableGame;
import java.awt.BorderLayout;
import javax.swing.JPanel; 

/** Painel que contem todos os elementos que aparecem na janela.
 * 
 * @author JMSousa (base)
 *
 */
public class ThreeInRowGamePanel extends JPanel
{	
    ObservableGame game;
    StartOptionPanel optionPanel;
    GameGrid theGrid;
    PlayerData pd1,pd2;
    ChatPanel panelChat;
    
    String username;
    

    public ThreeInRowGamePanel(ObservableGame game, String username )
    {
        this.game=game;
        this.username = username;
        setupComponents();
        setupLayout();
    }

    private void setupComponents()
    {
        
        optionPanel=new StartOptionPanel(game, username);
        theGrid=new GameGrid(game);
        pd1=new PlayerData(game,1);
        pd2=new PlayerData(game,2);
        panelChat = new ChatPanel();
   
    }

    private void setupLayout()
    {
        JPanel pCenter, pSouth;

        setLayout(new BorderLayout());
        
        //pChat = new JPanel();
        //pChat.setLayout(new BorderLayout());
        //pChat.add(panelChat, BorderLayout.EAST);

        pCenter=new JPanel();
        pCenter.setLayout(new BorderLayout());
        pCenter.add(theGrid,BorderLayout.NORTH);
        
        pSouth=new JPanel();
        pSouth.add(pd1);
        pSouth.add(pd2);
        pCenter.add(pSouth,BorderLayout.SOUTH);
 
                       
        add(pCenter,BorderLayout.CENTER);
        add(optionPanel,BorderLayout.EAST);
        add(panelChat,BorderLayout.SOUTH);
        
        validate();
        
    }
    
}
