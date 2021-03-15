package Multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MulticastSender {

    public static void main(String args[]){

        MulticastSocket s =null;
        InetAddress group = null;


        try {

            group = InetAddress.getByName("228.5.6.7");
            s = new MulticastSocket(6789);

            s.joinGroup(group);

            while (true){

                String message= (new Date()).toString();

                byte [] m = message.getBytes();
                DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
                s.send(messageOut);
                System.out.println("Heartbeat");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MulticastSender.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        catch (SocketException e){
            System.out.println("Socket: " + e.getMessage());
        }
        catch (IOException e){
            System.out.println("IO: " + e.getMessage());
        }
        finally {
            if(s != null){
                try {
                    s.leaveGroup(group);
                } catch (IOException ex) {
                    Logger.getLogger(MulticastSender.class.getName()).log(Level.SEVERE, null, ex);
                }
                s.close();
            }
        }

    }
}
