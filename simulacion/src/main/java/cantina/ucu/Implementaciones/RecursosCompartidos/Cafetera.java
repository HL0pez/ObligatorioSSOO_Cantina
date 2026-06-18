package cantina.ucu.Implementaciones.RecursosCompartidos;

import cantina.ucu.Interfaces.IRecursoCompartido;

public class Cafetera implements IRecursoCompartido {

    private int cantidad;
    private int tiempoOcupada;
    
    public Cafetera(int slots){
        this.cantidad = slots;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getTiempoOcupada() {
        return tiempoOcupada;
    }


    public void setTiempoOcupada(int tiempoOcupada) {
        this.tiempoOcupada = tiempoOcupada;
    }
    
}
