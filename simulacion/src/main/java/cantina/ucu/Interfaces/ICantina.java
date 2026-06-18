package cantina.ucu.Interfaces;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.Semaphore;

public interface ICantina {

    IRecursoCompartido cafetera = null;
    Queue<IPedido> pedidosPendientes = null;
    IRecursoCompartido caja = null;
    ArrayList<Runnable> baristas = null;
    Stack<IPedido> pedidosCompletados = null;
    Semaphore semaforoCaja = null;
    Semaphore semaforoCafetera = null;

    public void procesarPedido();
    private void recalcularPrioridad() {
    }
    public void agregarPedido(IPedido pedido);

}
