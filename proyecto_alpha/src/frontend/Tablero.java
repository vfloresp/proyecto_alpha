package frontend;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class Tablero extends JFrame {
    JCheckBox  hoyo1 = new JCheckBox("Hoyo 1" );
    JCheckBox  hoyo2 = new JCheckBox("Hoyo 2" );
    JCheckBox  hoyo3 = new JCheckBox("Hoyo 3" );
    JCheckBox  hoyo4 = new JCheckBox("Hoyo 4" );
    JCheckBox  hoyo5 = new JCheckBox("Hoyo 5");
    JCheckBox  hoyo6 = new JCheckBox("Hoyo 6");
    JCheckBox  hoyo7 = new JCheckBox("Hoyo 7");
    JCheckBox  hoyo8 = new JCheckBox("Hoyo 8");
    JCheckBox  hoyo9 = new JCheckBox("Hoyo 9" );

    public Tablero(){
        JFrame Inicioframe= new JFrame("¡Pegale al monstruo!");

        //Pinta los hoyos
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
        add(hoyo1);
        add(hoyo2);
        add(hoyo3);
        add(hoyo4);
        add(hoyo5);
        add(hoyo6);
        add(hoyo7);
        add(hoyo8);
        add(hoyo9);

        setSize(300,300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //Función para seleccionar un hoyo del tablero
    public void seleccionar(String i){

        System.out.println("Tablero: "+i);
            //hoyo1.setSelected(true);


       switch (i) {
            case "1" -> hoyo1.setSelected(true);
            case "2" -> hoyo2.setSelected(true);
            case "3" -> hoyo3.setSelected(true);
            case "4" -> hoyo4.setSelected(true);
            case "5" -> hoyo5.setSelected(true);
            case "6" -> hoyo6.setSelected(true);
            case "7" -> hoyo7.setSelected(true);
            case "8" -> hoyo8.setSelected(true);
            case "9" -> hoyo9.setSelected(true);
        }

    }

    public static void main(String args[]){
        Tablero aplicacion = new Tablero();
    }
}
