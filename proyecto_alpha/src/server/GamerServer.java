package server;

import interfaces.Player;
import interfaces.Registro;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class GamerServer implements Registro{

    public static void main(String[] args){
        System.setProperty("java.security.policy","/home/vfloresp/Documents/ITAM/proyecto_alpha/proyecto_alpha/src/server/server.policy");

        if(System.getSecurityManager()==null){
            System.setSecurityManager(new SecurityManager());
        }

        try{
            LocateRegistry.createRegistry(1099);
            String name = "Registro";
            GamerServer engine = new GamerServer();
            Registro stub = (Registro) UnicastRemoteObject.exportObject(engine,0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name,stub);
            System.out.println("Servicio desplegado!");

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }




    @Override
    public Player registro(String nombre) throws RemoteException {
        return new Player(nombre);
    }
}
