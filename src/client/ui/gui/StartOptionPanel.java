
package client.ui.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import client.logic.ObservableGame;
import client.logic.states.AwaitBeginning;
import java.util.List;
;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import util.model.Player;

/** Painel que apresenta as varias opcoes de configuracao e permite iniciar o game
 *  Observa o game para se tornar invisivel/visivel conforme um game esteja em curso ou nao.
 * @author JMSousa (base)
 *
 */

class StartOptionPanel extends JPanel implements Observer
{
    ObservableGame game;
    
    JButton start=new JButton("Start");
    PlayerNameBox player1Name;		
    PlayerNameBox player2Name;
    final JList<String> list;
    DefaultListModel<String> model = new DefaultListModel<>();
    
    String username;

    
    StartOptionPanel(ObservableGame g, String username)
    {
        this.list = new JList<>(model);
        game=g;
        game.addObserver(this);
        this.username = username;
        
        
        setBackground(Color.GREEN);
        setupComponents();
        setupLayout();
        
        setVisible(game.getState() instanceof AwaitBeginning);
        //setVisible(false);
        
        
    }


   private void setupLayout()
    {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        start.setAlignmentX(Component.CENTER_ALIGNMENT); 
        
        add(Box.createVerticalStrut(10));
        add(start);
        
        player1Name.setMinimumSize(new Dimension(120,20));
        player2Name.setMinimumSize(new Dimension(120,20));
        
        player1Name.setAlignmentX(Component.CENTER_ALIGNMENT);
        player2Name.setAlignmentX(Component.CENTER_ALIGNMENT);
        player1Name.setOpaque(false);
        player2Name.setOpaque(false);
        
        add(Box.createVerticalStrut(10));
        add(player1Name);
        
        add(Box.createVerticalStrut(10));
        add(player2Name);
        
        player1Name.setText(username);
        player1Name.setEnabled(false);
        
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(list));
        
        
        list.addListSelectionListener(new ListSelectionListener(){
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //add stuff to do with the selection user
                
                 if(e.getValueIsAdjusting()){
                     player2Name.setText(list.getSelectedValue());
                     
                 }
            }
        
        });
        
        validate();
    }
 
    
    private void setupComponents()
    {
        player1Name = new PlayerNameBox(game,1);	
        player2Name = new PlayerNameBox(game,2);

        start.addActionListener(new ActionListener(){        
            @Override
            public void actionPerformed(ActionEvent ev){
                game.setPlayerName(1, player1Name.getText());
                game.setPlayerName(2, player2Name.getText());
                game.startGame(player2Name.getText());
            }
        });
        
    }
    
    //Update listUsersLoggedIn
    
    private void setValue(List<Player> p) {
        model.removeAllElements();
        //list.setListData((Vector) p);
        for (Player p1 : p) {
            if(!p1.getUsername().equals(this.username))
                model.addElement(p1.getName());
            
        }
        //list.updateUI();
    }
    
    @Override
    public void update(Observable o, Object arg)
    {
        if (arg instanceof List && arg != null  ){
            this.setValue((List<Player>) arg);
        } else {
            
        }
        setVisible(game.getState() instanceof AwaitBeginning);
    }

}
