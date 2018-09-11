package client;

import client.logic.ObservableGame;
import client.ui.gui.LoginFrame;
import client.ui.gui.RegisterFrame;
import client.ui.gui.ThreeInRowView;

public class MainWithGui 
{

    public static void main(String[] args)
    {
        String ip = "localhost";
        
        if (args.length != 1) {
            System.out.println("Invalid arguments! \nEx: java Gestao 'port'");
            System.out.println("use deafult ip");
        }else{
            ip = args[0];
        }
            
        
        
        
        //ThreeInRowView GUI = new ThreeInRowView(new ObservableGame());
        LoginFrame GUI = new LoginFrame(new ObservableGame(ip), ip);
        //RegisterFrame reg = new RegisterFrame(new ObservableGame());
    }
}
