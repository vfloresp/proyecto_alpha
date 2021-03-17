package client;

import frontend.RegistroJugador;
import frontend.Tablero;
import interfaces.Player;
import interfaces.Registro;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GameClient {

    public static void main(String[] args){
        System.setProperty("java.security.policy","C:/Users/susy_/IdeaProjects/ProyectoAlphaV1/src/client/client.policy");
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
            }
            Player nuevoJugador = servidorRegistro.registro(menuRegistro.getNombre());
            System.out.println("Bienvendio " + nuevoJugador.getNombre() + "!");

            //Suscripcion a multicast
            InetAddress group = InetAddress.getByName(nuevoJugador.getGrupoMultiCast()); // destination multicast group
            s = new MulticastSocket(nuevoJugador.getPuertoMultiCast());
            s.joinGroup(group);

            //VARIABLES TCPSERVER
            Socket sT = null;
            int serverPort = nuevoJugador.getPuertoTCP();
            sT = new Socket("localhost", serverPort);
            DataInputStream in = new DataInputStream( sT.getInputStream());
            DataOutputStream out = new DataOutputStream( sT.getOutputStream());

            byte[] buffer = new byte[1000];
            DatagramPacket  messageIn = new DatagramPacket(buffer, buffer.length);

            //Recepcion nuevas posiciones
            Tablero tablero = new Tablero();
            while(true) {
                s.receive(messageIn);
                tablero.limpiarTablero();
                tablero.setAccion(false);
                String data = new String(messageIn.getData(), 0, messageIn.getLength());
                //actualizar tablero
                tablero.seleccionar(data);
                //Esperar respuesta jugador
                int dummy = 0;
                long startTime = System.currentTimeMillis();
                while (!tablero.getAccion() && (System.currentTimeMillis()-startTime)<4000) {
                    System.out.println("esperando");
                    //dummy ++;
                }

                if(tablero.validarAccion(data)){
                    //MENSAJE TCP SERVIDOR
                    System.out.println("respuesta correcta");
                    out.writeInt(nuevoJugador.getId());

                }else{
                    System.out.println("respuesta incorrecta");
                }
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

