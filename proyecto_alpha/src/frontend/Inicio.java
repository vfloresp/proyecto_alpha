package frontend;

import javax.swing.*;
import java.awt.*;

public class Inicio extends JFrame {




    public Inicio(){
        JFrame Inicioframe= new JFrame("¡Pegale al monstruo!");

        //Pinta los hoyos
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 20));
        for(int i = 1; i <= 9; i++){
            add(new JCheckBox("Hoyo " + i));
        }

        setSize(300,300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String args[]){
        Inicio aplicacion = new Inicio();
    }
}
