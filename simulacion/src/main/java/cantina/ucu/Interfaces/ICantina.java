package cantina.ucu.Interfaces;

import java.util.List;
import java.util.PriorityQueue;

import cantina.ucu.Implementaciones.RecursosCompartidos.Cafetera;
import cantina.ucu.Implementaciones.RecursosCompartidos.CajaRegistradora;

public interface ICantina {

    public IPedido procesarPedido();

    public void agregarPedido(IPedido pedido);


    public Cafetera getCafetera();

    public PriorityQueue<IPedido> getPedidosPendientes();

    public CajaRegistradora getCaja();

    public List<Thread> getBaristas();

    public void recalcularPrioridad();

}
