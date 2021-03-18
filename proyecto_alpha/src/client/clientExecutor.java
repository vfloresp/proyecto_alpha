package client;

import client.GameClient;

public class clientExecutor {

    public static void main(String[] args){
        GameClient jugador1 = new GameClient();
        GameClient jugador2 = new GameClient();
        GameClient jugador3 = new GameClient();
        jugador1.start();
        jugador2.start();
        jugador3.start();


    }


}
