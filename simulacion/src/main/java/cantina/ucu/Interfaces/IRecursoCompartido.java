package cantina.ucu.Interfaces;

import java.util.Queue;

public interface IRecursoCompartido {
    
    void atenderPedido(IPedido pedido);

    int getCantidad();
}
