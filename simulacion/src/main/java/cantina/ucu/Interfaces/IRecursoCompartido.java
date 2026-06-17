package cantina.ucu.Interfaces;

import java.util.Queue;

public interface IRecursoCompartido {
    
    Queue<IPedido> pendientes = null;
    
    void atenderPedido(IPedido pedido);
}
