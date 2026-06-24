package cantina.ucu.Implementaciones;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import cantina.ucu.Interfaces.ICantina;
import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IRecursoCompartido;

public class Cantina implements ICantina {

    private IRecursoCompartido cafetera;
    private PriorityQueue<IPedido> pedidosPendientes;
    private IRecursoCompartido caja;
    private List<Thread> baristas = new LinkedList<>();
    private boolean abierta = false;
    private Thread reloj;
    private Metricas metricas = Metricas.getInstancia();

    public Cantina(IRecursoCompartido cafetera, IRecursoCompartido cajaRegistradora, int cantidadBaristas){
        this.cafetera = cafetera;
        this.caja = cajaRegistradora;
        for (int i = 0; i < cantidadBaristas; i++) {
            Thread hiloBarista = new Thread(new Barista(this),"Barista-" + (i + 1));
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
            metricas.getPedidosCompletados().add(metricas.getPedidosSinAtender().poll());
            IPedido pedido = pedidosPendientes.poll();
            pedido.setMomentoDeAtencion();
            return pedido;
        }
    }

    @Override
    public void agregarPedido(IPedido pedido) {
        if (abierta) {
            synchronized (pedidosPendientes) {
                pedido.calcularPrioridad();
                pedidosPendientes.add(pedido);
                metricas.getPedidosSinAtender().add(pedido);
                pedidosPendientes.notifyAll();
            }    
        }
    }

    public void recalcularPrioridad() {
        synchronized (pedidosPendientes) {
            for (IPedido pedido : pedidosPendientes) {
                pedido.calcularPrioridad();
            }
        }
    }

    public void simulacion(int segundos){
        this.reloj = new Thread(new Reloj(segundos, this));
        reloj.start();
    }

    public boolean estaAbierta() {
        return this.abierta;
    }
    
    public void cerrar() {
        this.abierta = false;
    }

    public void abrir() {
        this.abierta = true;
        for (Thread barista : baristas) {
            if (!barista.isAlive()) {
                barista.start();
            }
        }
    }

    public IRecursoCompartido getCafetera() {
        return cafetera;
    }

    public PriorityQueue<IPedido> getPedidosPendientes() {
        return pedidosPendientes;
    }

    public IRecursoCompartido getCaja() {
        return caja;
    }

    public List<Thread> getBaristas() {
        return baristas;
    }

    @Override
    public Thread getReloj() {
        return this.reloj;
    }

}
