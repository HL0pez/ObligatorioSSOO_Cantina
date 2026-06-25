package cantina.ucu.Interfaces;

import java.util.List;
import java.util.PriorityQueue;

public interface ICantina {

    public IPedido procesarPedido();

    public void agregarPedido(IPedido pedido);


    public IRecursoCompartido getCafetera();

    public PriorityQueue<IPedido> getPedidosPendientes();

    public IRecursoCompartido getCaja();

    public List<Thread> getBaristas();

    public void recalcularPrioridad();

    public boolean estaAbierta();

    public void simulacion(int segundos);

    public void cerrar();

    public void abrir();

    public Thread getReloj();
}
