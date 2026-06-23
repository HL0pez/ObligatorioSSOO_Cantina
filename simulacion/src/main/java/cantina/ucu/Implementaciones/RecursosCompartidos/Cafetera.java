package cantina.ucu.Implementaciones.RecursosCompartidos;

import java.util.concurrent.Semaphore;

import cantina.ucu.Implementaciones.Metricas;
import cantina.ucu.Implementaciones.Productos.Cafe;
import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IProducto;
import cantina.ucu.Interfaces.IRecursoCompartido;

public class Cafetera implements IRecursoCompartido {

    private final int cantidad;
    private int tiempoOcupada;
    private Semaphore semaforoCafetera;
    private final Metricas metricas;


    public Cafetera(int slots) {
        this.cantidad = slots;
        this.semaforoCafetera = new Semaphore(slots);
        this.metricas = Metricas.getInstancia();
    }


    public int atender(IPedido pedido){
        int tiempoCafe = 0;
        try {
            semaforoCafetera.acquire();  

            for (IProducto producto : pedido.getProductos()) {
                if (producto instanceof Cafe) {
                    tiempoCafe += producto.getTiempoDePreparacion();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        semaforoCafetera.release();


        return tiempoCafe;
    }

    public int getCantidad() {
        return cantidad;
    }

    public synchronized int getTiempoOcupada() {
        return tiempoOcupada;
    }

    public synchronized void setTiempoOcupada(int tiempoOcupada) {
        this.tiempoOcupada = tiempoOcupada;
    }
    
    public Semaphore getSemaforo() {
        return semaforoCafetera;
    }
}