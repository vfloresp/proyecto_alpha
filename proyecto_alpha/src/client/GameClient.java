package client;

import interfaces.Registro;
import interfaces.Player;
import java.net.*;
import java.nio.ByteBuffer;
import java.rmi.AccessException;
import frontend.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.IOException;

public class GameClient {

    public static void main(String[] args){
        System.setProperty("java.security.policy","/home/vfloresp/Documents/ITAM/proyecto_alpha/proyecto_alpha/src/client/client.policy");
        MulticastSocket s =null;
        String name = "Registro";
        Registry registry = null;
        try {
            //Registro de un nuevo jugador
            registry = LocateRegistry.getRegistry("localhost");
            Registro servidorRegistro = (Registro) registry.lookup(name);
            RegistroJugador menuRegistro = new RegistroJugador();
            while(menuRegistro.getNombre() == null){
                Thread.sleep(1000);
                System.out.println("esperando");
            }
            Player nuevoJugador = servidorRegistro.registro(menuRegistro.getNombre());
            System.out.println(servidorRegistro.registro(menuRegistro.getNombre()));

            //Suscripcion a multicast
            InetAddress group = InetAddress.getByName(nuevoJugador.getGrupoMultiCast()); // destination multicast group
            s = new MulticastSocket(nuevoJugador.getPuertoMultiCast());
            s.joinGroup(group);

            byte[] buffer = new byte[1000];
            DatagramPacket  messageIn = new DatagramPacket(buffer, buffer.length);

            //Recepcion nuevas posiciones
            Tablero tablero = new Tablero();
            while(true) {
                s.receive(messageIn);
                System.out.println("Gamer server: " + new String(messageIn.getData()));
                tablero.seleccionar(new String(messageIn.getData()));
                //actualizar tablero
            }

        } catch (RemoteException | NotBoundException | InterruptedException | UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(s != null) s.close();
        }
    }
}
