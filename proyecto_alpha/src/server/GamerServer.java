package server;

import interfaces.Player;
import interfaces.Registro;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class GamerServer implements Registro{


    @Override
    public Player registro(String nombre) throws RemoteException {
        return null;
    }
}
