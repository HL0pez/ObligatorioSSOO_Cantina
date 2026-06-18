package cantina.ucu.Implementaciones;

import java.util.PriorityQueue;
import java.util.Queue;

import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IRecursoCompartido;

public class Cafetera implements IRecursoCompartido {

    PriorityQueue<IPedido> pendientes = null;

    public Cafetera(){
        this.pendientes = new PriorityQueue<>();
    }
    @Override
    public void atenderPedido(IPedido pedido) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atenderPedido'");
    }
    
}
