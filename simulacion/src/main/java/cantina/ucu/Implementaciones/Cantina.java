package cantina.ucu.Implementaciones;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import cantina.ucu.Interfaces.ICantina;
import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IRecursoCompartido;

public class Cantina implements ICantina {

    private IRecursoCompartido cafetera;
    private Queue<IPedido> pedidosPendientes;
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
        this.pedidosPendientes = new LinkedList<>();
        this.semaforoCafetera = new Semaphore(cafetera.getCantidad());
        this.semaforoCaja = new Semaphore(caja.getCantidad());
        this.semaforoBarista = new Semaphore(cantidadBaristas);
        
    }

    @Override
    public void procesarPedido() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'procesarPedido'");
    }

    @Override
    public void agregarPedido(IPedido pedido) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'agregarPedido'");
    }
    
}
