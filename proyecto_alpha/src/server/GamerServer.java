package server;

import interfaces.Registro;
import interfaces.Player;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GamerServer implements Registro{
    private int countPlayer = 0;

    public static void main(String[] args){
        System.setProperty("java.security.policy","/home/vfloresp/Documents/ITAM/proyecto_alpha/proyecto_alpha/src/server/server.policy");
        MulticastSocket s =null;
        InetAddress group = null;

        if(System.getSecurityManager()==null){
            System.setSecurityManager(new SecurityManager());
        }

        try{
            //Desplegar servicio de registro RMI
            LocateRegistry.createRegistry(1099);
            String name = "Registro";
            GamerServer engine = new GamerServer();
            Registro stub = (Registro) UnicastRemoteObject.exportObject(engine,0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name,stub);
            System.out.println("Servicio desplegado!");

            //Crear grupo mulitcast
            group = InetAddress.getByName("228.5.6.7");
            s = new MulticastSocket(6789);
            s.joinGroup(group);
            //Enviar cada 5s un nuevo monstruo
            while (true){
                String message= Integer.toString((int) (Math.random()*(10-1)+1));
                byte[] m = message.getBytes();

                DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
                s.send(messageOut);
                System.out.println(message);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GamerServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }


        } catch (RemoteException | UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(s != null){
                try {
                    s.leaveGroup(group);
                } catch (IOException ex) {
                    Logger.getLogger(GamerServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                s.close();
            }
        }
    }




    @Override
    public Player registro(String nombre) throws RemoteException {
        countPlayer ++;
        return new Player(nombre, countPlayer);
    }
}
