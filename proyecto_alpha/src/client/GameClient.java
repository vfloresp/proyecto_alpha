package client;

import interfaces.*;

import java.io.ByteArrayInputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.rmi.AccessException;
import frontend.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.IOException;
import java.util.Arrays;

public class GameClient {

    public static void main(String[] args){
        System.setProperty("java.security.policy","D:/tabat/Documents/12/ProyectoAlpha/proyecto_alpha/proyecto_alpha/src/client/client.policy");
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
                String data = new String(messageIn.getData(), 0, messageIn.getLength());
                System.out.println("Gamer server: " + data);
                tablero.seleccionar(data);

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
