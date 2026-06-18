package cantina.ucu.Implementaciones.RecursosCompartidos;

import java.util.LinkedList;
import java.util.Queue;

import cantina.ucu.Interfaces.IPedido;
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
    
}
