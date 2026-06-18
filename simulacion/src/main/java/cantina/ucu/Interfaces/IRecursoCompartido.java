package cantina.ucu.Interfaces;

import java.util.Queue;

public interface IRecursoCompartido {
    
    Queue<IPedido> pendientes = null;
    int cantidad = (Integer) null;
    
    void atenderPedido(IPedido pedido);

    int getCantidad();
}
