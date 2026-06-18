package cantina.ucu.Interfaces;

import java.util.Queue;

public interface IRecursoCompartido {
    
    int cantidad = (Integer) null;
    
    void atenderPedido(IPedido pedido);

    int getCantidad();
}
