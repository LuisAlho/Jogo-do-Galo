package client;

import client.logic.ObservableGame;
import client.ui.gui.ThreeInRowView;

public class MainWithGui 
{

    public static void main(String[] args)
    {
        ThreeInRowView GUI = new ThreeInRowView(new ObservableGame());

    }
    
}
