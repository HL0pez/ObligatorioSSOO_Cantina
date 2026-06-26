package cantina.ucu.Implementaciones.RecursosCompartidos;

import java.util.concurrent.Semaphore;

import cantina.ucu.Implementaciones.Metricas;
import cantina.ucu.Implementaciones.Productos.Cafe;
import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IProducto;
import cantina.ucu.Interfaces.IRecursoCompartido;

public class Cafetera implements IRecursoCompartido {

    private final int cantidad;
    private Semaphore semaforoCafetera;
    private final Metricas metricas;


    public Cafetera(int slots) {
        this.cantidad = slots;
        this.semaforoCafetera = new Semaphore(slots);
        this.metricas = Metricas.getInstancia();
    }

    /*
    toma su slot (semaforo)
    calcula el tiempo que va a estar ocupado el slot de la cafetera
    espera el tiempo que toma hacer los cafes
    libera el slot (semaforo)
    guarda el tiempo que estuvo ocupado en metricas
    */
    public int atender(IPedido pedido){
        int tiempoCafe = 0;
        boolean permisoAdquirido = false;

        try {
            semaforoCafetera.acquire();
            permisoAdquirido = true;

            for (IProducto producto : pedido.getProductos()) {
                if (producto instanceof Cafe) {
                    tiempoCafe += producto.getTiempoDePreparacion();
                }
            }

            Thread.sleep(tiempoCafe * 1000);
            metricas.agregarTiempoCafeteraOcupada(tiempoCafe);

            return tiempoCafe;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return 0;
        } finally {
            if (permisoAdquirido) {
                semaforoCafetera.release();
                return tiempoCafe;
            }
        }
    }



// ========================================= getters =========================================
    
    public int getCantidad() {
        return cantidad;
    }
    
    public Semaphore getSemaforo() {
        return semaforoCafetera;
    }
}