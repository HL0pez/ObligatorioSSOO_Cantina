package cantina.ucu.Implementaciones;

import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;

import cantina.ucu.Implementaciones.RecursosCompartidos.Cafetera;
import cantina.ucu.Implementaciones.RecursosCompartidos.CajaRegistradora;
import cantina.ucu.Interfaces.ICantina;
import cantina.ucu.Interfaces.IPedido;

public class Cantina implements ICantina {

    private Cafetera cafetera;
    private PriorityQueue<IPedido> pedidosPendientes;
    private CajaRegistradora caja;
    private Queue<Runnable> baristas;   // chau?

    public Cantina(Cafetera cafetera, CajaRegistradora cajaRegistradora, int cantidadBaristas){

        this.cafetera = cafetera;
        this.caja = cajaRegistradora;
        this.baristas = new LinkedList<>();
        for (int i = 0; i < cantidadBaristas; i++) {
            baristas.add(new Barista(this));
        }
        this.pedidosPendientes = new PriorityQueue<>();
    }

    @Override
    public void procesarPedido() {
        /*  
            procesa el pedido con más prioridad con el run de barista
        */
        while (pedidosPendientes.isEmpty()) {
            try {
                pedidosPendientes.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pedidosPendientes.notifyAll();
    }

    @Override
    public void agregarPedido(IPedido pedido) {
        pedidosPendientes.add(pedido); // agregar con PRIORIDAD
    }

    public void recalcularPrioridad() {
        for (IPedido pedido : pedidosPendientes) {
            pedido.calcularPrioridad();
        }
    }
    
    public Cafetera getCafetera() {
        return cafetera;
    }

    public PriorityQueue<IPedido> getPedidosPendientes() {
        return pedidosPendientes;
    }

    public CajaRegistradora getCaja() {
        return caja;
    }

    public Queue<Runnable> getBaristas() {
        return baristas;
    }

}
