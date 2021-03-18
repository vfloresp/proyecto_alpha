package frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class Tablero extends JFrame implements ActionListener {
    private final JCheckBox  hoyo1 = new JCheckBox("Hoyo 1" );
    private final JCheckBox  hoyo2 = new JCheckBox("Hoyo 2" );
    private final JCheckBox  hoyo3 = new JCheckBox("Hoyo 3" );
    private final JCheckBox  hoyo4 = new JCheckBox("Hoyo 4" );
    private final JCheckBox  hoyo5 = new JCheckBox("Hoyo 5");
    private final JCheckBox  hoyo6 = new JCheckBox("Hoyo 6");
    private final JCheckBox  hoyo7 = new JCheckBox("Hoyo 7");
    private final JCheckBox  hoyo8 = new JCheckBox("Hoyo 8");
    private final JCheckBox  hoyo9 = new JCheckBox("Hoyo 9" );
    public Boolean accion = false;

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

        hoyo1.addActionListener(this);
        hoyo2.addActionListener(this);
        hoyo3.addActionListener(this);
        hoyo4.addActionListener(this);
        hoyo5.addActionListener(this);
        hoyo6.addActionListener(this);
        hoyo7.addActionListener(this);
        hoyo8.addActionListener(this);
        hoyo9.addActionListener(this);

        setSize(300,300);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //Función para seleccionar un hoyo del tablero
    public void seleccionar(String i){
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

    public void limpiarTablero(){
        hoyo1.setSelected(false);
        hoyo2.setSelected(false);
        hoyo3.setSelected(false);
        hoyo4.setSelected(false);
        hoyo5.setSelected(false);
        hoyo6.setSelected(false);
        hoyo7.setSelected(false);
        hoyo8.setSelected(false);
        hoyo9.setSelected(false);
    }

    public boolean validarAccion(String i){
        boolean res = false;
        switch (i) {
            case "1" -> res = !hoyo1.isSelected();
            case "2" -> res = !hoyo2.isSelected();
            case "3" -> res = !hoyo3.isSelected();
            case "4" -> res = !hoyo4.isSelected();
            case "5" -> res = !hoyo5.isSelected();
            case "6" -> res = !hoyo6.isSelected();
            case "7" -> res = !hoyo7.isSelected();
            case "8" -> res = !hoyo8.isSelected();
            case "9" -> res = !hoyo9.isSelected();
        }
        return res;
    }

    public void actionPerformed(ActionEvent e) {
        accion = true;
    }

    public Boolean getAccion() {
        return accion;
    }

    public void setAccion(Boolean accion) {
        this.accion = accion;
    }

    public static void main(String args[]){
        Tablero aplicacion = new Tablero();
    }
}
