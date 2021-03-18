package server;

import frontend.Ganador;
import interfaces.Player;
import interfaces.Registro;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.*;

public class GamerServer implements Registro{
    private static Map<Integer,Player> players = new HashMap<Integer,Player>();
    private static int puntuacionGanadora = 3;

    public static void main(String[] args){

        System.setProperty("java.security.policy","/home/vfloresp/Documents/ITAM/proyecto_alpha/proyecto_alpha/src/server/server.policy");
        MulticastSocket s =null;
        InetAddress group = null;
        Socket st = null;

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

            //VARIABLES TCPSERVER
            int serverPort = 7896;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            listenSocket.setSoTimeout(5000);


            //Enviar cada 5s un nuevo monstruo
            System.out.println("Esperando jugadores");
            while (true) {
                sleep(10);
                if(!players.isEmpty()) {
                    sleep(1000);
                    Player hayGanador = checarGanador();
                    if(hayGanador != null){
                        System.out.println("El ganador es "+hayGanador.getNombre());
                        reiniciarPartida();
                        String message = "El ganador es " + hayGanador.getNombre();
                        byte[] m = message.getBytes();
                        DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
                        s.send(messageOut);
                        sleep(5000);
                    }
                    String message = Integer.toString((int) (Math.random() * (10 - 1) + 1));
                    byte[] m = message.getBytes();
                    DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
                    s.send(messageOut);
                    System.out.println("mensaje enviado: " + message);
                    long startTime = System.currentTimeMillis();


                    try {
                        Socket clientSocket = listenSocket.accept();
                        Connection c = new Connection(clientSocket);
                        c.start();
                        while(c.getIdPlayer() == -1){
                            sleep(10);
                        }
                        System.out.println("respuesta run " +c.getIdPlayer());
                        if(c.getIdPlayer() != 0)
                            darPunto(c.getIdPlayer());
                    }catch (java.io.InterruptedIOException e){
                        System.out.println("Interrupcion timeout");
                    }

                    while((System.currentTimeMillis()-startTime)<5000) {
                        sleep(10);
                    }
                }
            }

        } catch (RemoteException | UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
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

    public static void darPunto(int idPlayer){
        Player play = players.get(idPlayer);
        play.setPuntuacion(play.getPuntuacion()+1);
    }

    public static Player checarGanador(){
        Player ganador = null;
        for(Map.Entry<Integer,Player> entry : players.entrySet()){
            Player player = entry.getValue();
            if(player.getPuntuacion()==puntuacionGanadora){
                ganador =  player;
            }
        }
        return ganador;
    }

    public static void reiniciarPartida(){
        for(Map.Entry<Integer,Player> entry : players.entrySet()){
            entry.getValue().setPuntuacion(0);
        }
    }

    @Override
    public Player registro(String nombre) throws RemoteException {
        int idPlayer;
        if(players.isEmpty()){
            idPlayer = 1;
        }else {
            idPlayer = players.size() + 1;
        }
        Player newPlayer = new Player(nombre, idPlayer);
        players.put(newPlayer.getId(), newPlayer);
        return newPlayer;
    }
}

class Connection extends Thread {
    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;
    int idPlayer = -1;
    public Connection (Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out =new DataOutputStream(clientSocket.getOutputStream());
        } catch(IOException e)  {System.out.println("Conexión: "+e.getMessage());}
    }

    @Override
    public void run(){
        try {
            idPlayer = in.readInt();
            //System.out.println("mensaje recibido: "+ idPlayer);
        }
        catch(EOFException e) {
            System.out.println("EOF:"+e.getMessage());
        }
        catch(IOException e) {
            System.out.println("IO:"+e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e){
                System.out.println(e);
            }
        }
    }

    public int getIdPlayer() {
        return idPlayer;
    }
}

