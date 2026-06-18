package cantina.ucu.Implementaciones;

import java.util.LinkedList;
import java.util.Queue;

import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IRecursoCompartido;

public class Cafetera implements IRecursoCompartido {

    Queue<IPedido> pendientes = null;

    public Cafetera(){
        this.pendientes = new LinkedList<>();
    }

    @Override
    public void atenderPedido(IPedido pedido) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atenderPedido'");
    }
    
}
