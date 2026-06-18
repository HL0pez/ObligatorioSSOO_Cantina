package cantina.ucu.Interfaces;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.Semaphore;

public interface ICantina {

    public void procesarPedido();

    public void agregarPedido(IPedido pedido);


    public IRecursoCompartido getCafetera();

    public PriorityQueue<IPedido> getPedidosPendientes();

    public IRecursoCompartido getCaja();

    public Queue<Runnable> getBaristas();

    public Stack<IPedido> getPedidosCompletados();

    public Semaphore getSemaforoCaja();

    public Semaphore getSemaforoCafetera();

    public Semaphore getSemaforoBarista();
}
