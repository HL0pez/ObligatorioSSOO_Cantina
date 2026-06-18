package cantina.ucu.Interfaces;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.Semaphore;

public interface ICantina {

    public void procesarPedido();
    private void recalcularPrioridad() {
    }
    public void agregarPedido(IPedido pedido);

}
