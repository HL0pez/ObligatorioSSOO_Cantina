package cantina.ucu.Implementaciones;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.concurrent.Semaphore;

import cantina.ucu.Implementaciones.RecursosCompartidos.Cafetera;
import cantina.ucu.Implementaciones.RecursosCompartidos.CajaRegistradora;
import cantina.ucu.Interfaces.ICantina;
import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IRecursoCompartido;

public class Cantina implements ICantina {

    private IRecursoCompartido cafetera;
    private PriorityQueue<IPedido> pedidosPendientes;
    private IRecursoCompartido caja;
    private ArrayList<Runnable> baristas;
    private Stack<IPedido> pedidosCompletados;
    private Semaphore semaforoCaja;
    private Semaphore semaforoCafetera;
    private Semaphore semaforoBarista;

    public Cantina(Cafetera cafetera, CajaRegistradora cajaRegistradora, int cantidadBaristas){

        this.cafetera = cafetera;
        this.caja = cajaRegistradora;
        this.baristas = new ArrayList<>();
        for (int i = 0; i < cantidadBaristas; i++) {
            baristas.add(new Barista());
        }
        this.pedidosCompletados = new Stack<>();
        this.pedidosPendientes = new PriorityQueue<>();
        this.semaforoCafetera = new Semaphore(cafetera.getCantidad());
        this.semaforoCaja = new Semaphore(caja.getCantidad());
        this.semaforoBarista = new Semaphore(cantidadBaristas);
        
    }

    @Override
    public void procesarPedido() {
        /*  adquiere semaforoBarista
            procesa el pedido con más prioridad con el run de barista
            libera semaforoBarista
        */
    }

    @Override
    public void agregarPedido(IPedido pedido) {
        pedidosPendientes.add(pedido);
    }
    
}
