package frontend;

import interfaces.Registro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistroJugador extends JFrame{

    public RegistroJugador(){
        JFrame Inicioframe= new JFrame("¡Pegale al monstruo!");
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT,2,2);
        setLayout(layout);
        JLabel descripcion = new JLabel("Ingrese su nombre:");
        JTextField textField = new JTextField(20);
        JButton registrar = new JButton("Aceptar");
        registrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                String nombre = textField.getText();
                System.out.println(nombre);
            }
        });
        add(descripcion);
        add(textField);
        add(registrar);

        setSize(300,300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /*public static void main(String[] args){
        RegistroJugador aplicacion = new RegistroJugador();
    }*/


}
