package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Registro extends Remote{

    public Player registro(String nombre) throws RemoteException;

}
