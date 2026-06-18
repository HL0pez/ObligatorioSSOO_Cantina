package cantina.ucu.Implementaciones.RecursosCompartidos;

import cantina.ucu.Interfaces.IRecursoCompartido;

public class CajaRegistradora implements IRecursoCompartido {

    private int cantidad;
    private int tiempoOcupada;

    public CajaRegistradora(int cantidad){
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getTiempoOcupada() {
        return tiempoOcupada;
    }

    @Override
    public void setTiempoOcupada(int tiempoOcupada) {
        this.tiempoOcupada = tiempoOcupada;
    }
    
}
