package interfaces;

import java.io.Serializable;

public class Player implements Serializable{
    private String nombre;
    private int puertoTCP;
    private int puertoMultiCast;
    private String grupoMultiCast;
    private int puntuacion;
    private int id;

    public Player(String nombre, int id){
        this.nombre = nombre;
        this.puertoTCP = 7896;
        this.puertoMultiCast = 6789;
        this.puntuacion = 0;
        this.grupoMultiCast ="228.5.6.7";
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuertoTCP() {
        return puertoTCP;
    }

    public void setPuertoTCP(int puertoTCP) {
        this.puertoTCP = puertoTCP;
    }

    public int getPuertoMultiCast() {
        return puertoMultiCast;
    }

    public void setPuertoMultiCast(int puertoMultiCast) {
        this.puertoMultiCast = puertoMultiCast;
    }

    public String getGrupoMultiCast() {
        return grupoMultiCast;
    }

    public void setGrupoMultiCast(String grupoMultiCast) {
        this.grupoMultiCast = grupoMultiCast;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    public String toString() {
        return "Player{" +
                "nombre='" + nombre + '\'' +
                ", puertoTCP=" + puertoTCP +
                ", puertoMultiCast=" + puertoMultiCast +
                ", grupoMultiCast='" + grupoMultiCast + '\'' +
                ", puntuacion=" + puntuacion +
                '}';
    }
}
