package client;

import interfaces.Registro;

import java.rmi.AccessException;
import frontend.RegistroJugador;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GameClient {

    public static void main(String[] args){
        System.setProperty("java.security.policy","/home/vfloresp/Documents/ITAM/proyecto_alpha/proyecto_alpha/src/client/client.policy");
        String name = "Registro";
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry("localhost");
            Registro servidorRegistro = (Registro) registry.lookup(name);
            RegistroJugador menuRegistro = new RegistroJugador();
            System.out.println(servidorRegistro.registro("Chuchito"));
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
