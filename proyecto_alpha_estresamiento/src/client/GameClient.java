package client;

import frontend.Ganador;
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

public class GameClient  extends Thread{
    private MulticastSocket s =null;
    String name = "Registro";
    Registry registry = null;
    Socket sT = null;
    private String nombre;

    public static void main(String[] args){
        System.setProperty("java.security.policy","/home/vfloresp/Documents/ITAM/proyecto_alpha/proyecto_alpha/src/client/client.policy");
        MulticastSocket s =null;
        String name = "Registro";
        Registry registry = null;
        Socket sT = null;
    }

    public GameClient(String nombre){
        this.nombre = nombre;

    }

    @Override
    public void run() {
        try {
            //Registro de un nuevo jugador
            registry = LocateRegistry.getRegistry("localhost");
            Registro servidorRegistro = (Registro) registry.lookup(name);
            Player nuevoJugador = servidorRegistro.registro(nombre);


            //Suscripcion a multicast
            InetAddress group = InetAddress.getByName(nuevoJugador.getGrupoMultiCast()); // destination multicast group
            s = new MulticastSocket(nuevoJugador.getPuertoMultiCast());
            s.joinGroup(group);

            //VARIABLES TCPSERVER
            int serverPort = nuevoJugador.getPuertoTCP();

            byte[] buffer = new byte[1000];
            DatagramPacket  messageIn = new DatagramPacket(buffer, buffer.length);

            //Recepcion nuevas posiciones
            while(true) {
                s.receive(messageIn);
                String data = new String(messageIn.getData(), 0, messageIn.getLength());
                if(data.length()<2){
                }else{
                    Thread.sleep(2000);
                    s.receive(messageIn);
                    data = new String(messageIn.getData(), 0, messageIn.getLength());
                }

                //Esperar respuesta jugador
                int simulacionTiro = (int) (Math.random()*(4001-1000)+1000 ) ;
                Thread.sleep(simulacionTiro);
                //MENSAJE TCP SERVIDOR
                double prob = Math.random();
                if(prob<0.75){
                    System.out.println("respuesta correcta " +nuevoJugador.getId());
                    sT = new Socket("localhost", serverPort);
                    DataInputStream in = new DataInputStream( sT.getInputStream());
                    DataOutputStream out = new DataOutputStream( sT.getOutputStream());
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
            if(s != null ) {
                s.close();

            }


        }
    }
}

