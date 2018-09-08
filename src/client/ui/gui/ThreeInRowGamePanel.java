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
    
    LoginPanel loginPanel;
    RegisterPanel registerPanel;

    public ThreeInRowGamePanel(ObservableGame game)
    {
        this.game=game;
                
        setupComponents();
        setupLayout();
    }

    private void setupComponents()
    {
        optionPanel=new StartOptionPanel(game);
        theGrid=new GameGrid(game);
        pd1=new PlayerData(game,1);
        pd2=new PlayerData(game,2);
        
        loginPanel = new LoginPanel(game);
        registerPanel = new RegisterPanel(game);
   
    }

    private void setupLayout()
    {
        JPanel pCenter, pSouth, pLogin, pRegister;

        setLayout(new BorderLayout());
        
        pLogin=new JPanel();
        pLogin.setLayout(new BorderLayout());
        pLogin.add(loginPanel,BorderLayout.CENTER);
        
        pRegister=new JPanel();
        pRegister.setLayout(new BorderLayout());
        pRegister.add(registerPanel,BorderLayout.CENTER);

        pCenter=new JPanel();
        pCenter.setLayout(new BorderLayout());
        pCenter.add(theGrid,BorderLayout.NORTH);
        
        pSouth=new JPanel();
        pSouth.add(pd1);
        pSouth.add(pd2);
        pCenter.add(pSouth,BorderLayout.SOUTH);
 
                       
        add(pCenter,BorderLayout.CENTER);
        
        add(optionPanel,BorderLayout.EAST);        
        
        add(pRegister, BorderLayout.CENTER);
        add(pLogin, BorderLayout.CENTER);
        
        validate();
    }
    
}
