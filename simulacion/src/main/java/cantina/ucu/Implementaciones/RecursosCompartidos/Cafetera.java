package cantina.ucu.Implementaciones.RecursosCompartidos;

import java.util.LinkedList;
import java.util.Queue;

import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IRecursoCompartido;

public class Cafetera implements IRecursoCompartido {

    private int cantidad;
    private int tiempoOcupada;
    
    public Cafetera(int slots){
        this.cantidad = slots;
    }

    @Override
    public void atenderPedido(IPedido pedido) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atenderPedido'");
    }

    public int getCantidad() {
        return cantidad;
    }
    
}
