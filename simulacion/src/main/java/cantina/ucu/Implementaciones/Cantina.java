package cantina.ucu.Implementaciones;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import cantina.ucu.Implementaciones.RecursosCompartidos.Cafetera;
import cantina.ucu.Implementaciones.RecursosCompartidos.CajaRegistradora;
import cantina.ucu.Interfaces.ICantina;
import cantina.ucu.Interfaces.IPedido;

public class Cantina implements ICantina {

    private Cafetera cafetera;
    private PriorityQueue<IPedido> pedidosPendientes;
    private CajaRegistradora caja;
    private List<Thread> baristas = new LinkedList<>();

    public Cantina(Cafetera cafetera, CajaRegistradora cajaRegistradora, int cantidadBaristas){

        this.cafetera = cafetera;
        this.caja = cajaRegistradora;
        for (int i = 0; i < cantidadBaristas; i++) {
            Thread hiloBarista = new Thread(new Barista(this),"Barista: " + (i + 1));
            baristas.add(hiloBarista);
        }
        this.pedidosPendientes = new PriorityQueue<>();
    }

    @Override
    public IPedido procesarPedido() {
        /*  
            procesa el pedido con más prioridad con el run de barista
        */
        synchronized(pedidosPendientes){
            while (pedidosPendientes.isEmpty()) {
                try {
                    pedidosPendientes.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
      return pedidosPendientes.poll();
    }

    @Override
    public void agregarPedido(IPedido pedido) {
        synchronized (pedidosPendientes) {
            pedido.calcularPrioridad();
            pedidosPendientes.add(pedido);
            pedidosPendientes.notifyAll();
        }
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

    public List<Thread> getBaristas() {
        return baristas;
    }

}
