package cantina.ucu.Implementaciones.RecursosCompartidos;


import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

import cantina.ucu.Implementaciones.Metricas;
import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IRecursoCompartido;

public class CajaRegistradora implements IRecursoCompartido {

    private int cantidad = 1;
    private final Lock mutex = new ReentrantLock(); 
    private final Metricas metricas = Metricas.getInstancia();

    public CajaRegistradora(int cantidad){
        this.cantidad = cantidad;
    }
    public int getCantidad() {
        return cantidad;
    }

    public Lock getMutex() {
        return mutex;
    }

    @Override
    public int atender(IPedido pedido) {
        metricas.getPedidosCompletados().add(pedido);
        return pedido.getProductos().size();
    }
        
}
