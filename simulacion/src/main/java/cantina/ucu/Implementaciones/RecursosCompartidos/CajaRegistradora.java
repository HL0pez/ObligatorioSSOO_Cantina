package cantina.ucu.Implementaciones.RecursosCompartidos;


import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

import cantina.ucu.Implementaciones.Metricas;
import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IRecursoCompartido;

public class CajaRegistradora implements IRecursoCompartido {

    private int cantidad = 0;
    private int tiempoOcupada = 0;
    private final Lock mutex = new ReentrantLock(); 
    private final Metricas metricas = Metricas.getInstancia();

    public int getCantidad() {
        return cantidad;
    }

    public int getTiempoOcupada() {
        return tiempoOcupada;
    }

    public Lock getMutex() {
        return mutex;
    }

    @Override
    public void setTiempoOcupada(int tiempoOcupada) {
        this.tiempoOcupada = tiempoOcupada;
    }

    @Override
    public int atender(IPedido pedido) {
        metricas.getPedidosCompletados().add(pedido);
        return pedido.getProductos().size();
    }
        
}
