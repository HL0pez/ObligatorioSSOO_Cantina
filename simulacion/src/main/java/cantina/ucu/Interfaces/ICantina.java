package cantina.ucu.Interfaces;

import java.util.PriorityQueue;
import java.util.Queue;

import cantina.ucu.Implementaciones.RecursosCompartidos.Cafetera;
import cantina.ucu.Implementaciones.RecursosCompartidos.CajaRegistradora;

public interface ICantina {

    public void procesarPedido();

    public void agregarPedido(IPedido pedido);


    public Cafetera getCafetera();

    public PriorityQueue<IPedido> getPedidosPendientes();

    public CajaRegistradora getCaja();

    public Queue<Runnable> getBaristas();

    public void recalcularPrioridad();

}
