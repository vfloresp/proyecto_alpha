package Multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.sql.SQLOutput;

public class MulticastReceiver {

    public static void main(String args[]){

        MulticastSocket s =null;
        try {
            InetAddress group = InetAddress.getByName("228.5.6.7"); // destination multicast group
            s = new MulticastSocket(6789);
            s.joinGroup(group);

            byte[] buffer = new byte[1000];
            DatagramPacket  messageIn = new DatagramPacket(buffer, buffer.length);

            while(true) {
                s.receive(messageIn);
                System.out.println("Message: " + new String(messageIn.getData())+ " from: " + messageIn.getAddress());
            }


        }
        catch (SocketException e){
            System.out.println("Socket: " + e.getMessage());
        }
        catch (IOException e){
            System.out.println("IO: " + e.getMessage());
        }
        finally {
            if(s != null) s.close();
        }
    }

}
