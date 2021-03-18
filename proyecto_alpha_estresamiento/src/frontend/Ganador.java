package frontend;

import javax.swing.*;
import java.awt.*;

public class Ganador extends JFrame{
    JLabel ganador = new JLabel("");

    public Ganador(){
        JFrame Inicioframe= new JFrame("¡Pegale al monstruo!");
        FlowLayout layout = new FlowLayout(FlowLayout.CENTER,2,2);
        setLayout(layout);
        JLabel nuevoJuego = new JLabel("<html><br/>¡Se inciara una nueva partida!</html>");
        add(ganador);
        add(nuevoJuego);
        setSize(300,300);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void msgGanador(String message){
        ganador.setText("<html><br/>"+message+"<br/></html>");
    }

    public void cerrarMsg(){
        dispose();
    }

    public static void main(String[] args){
        Ganador ganador = new Ganador();
        ganador.msgGanador("El ganador es Chuchito ");
    }
}
