package cantina.ucu.Implementaciones;

import java.util.concurrent.locks.Lock;

import cantina.ucu.Implementaciones.Productos.Cafe;
import cantina.ucu.Implementaciones.RecursosCompartidos.CajaRegistradora;
import cantina.ucu.Interfaces.ICantina;
import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IProducto;
import cantina.ucu.Interfaces.IRecursoCompartido;

public class Barista implements Runnable {

    private ICantina cantina;

    public Barista(ICantina cantina) {
        this.cantina = cantina;
    }

    private void prepararPedido(IPedido pedido) throws InterruptedException {
        if (pedido.tieneCafe()) {
                try {
                    IRecursoCompartido cafetera = cantina.getCafetera();

                    int tiempoCafe = cafetera.atender(pedido);
                    Thread.sleep(tiempoCafe * 1000);

                    cantina.getCafetera().setTiempoOcupada(cantina.getCafetera().getTiempoOcupada() + tiempoCafe);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int tiempoDePreparacion = 0;
            for (IProducto producto : pedido.getProductos()) {
                if (!(producto instanceof Cafe)) {
                    tiempoDePreparacion += producto.getTiempoDePreparacion();
                }
            }

            try {
                Thread.sleep(tiempoDePreparacion * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!pedido.getEstaPago()) {
                CajaRegistradora caja = cantina.getCaja();
                
                Lock mutex = caja.getMutex();

                while (!mutex.tryLock()) {
                    try {
                        mutex.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                int cantidadProductos = caja.atender(pedido);

                try {
                    Thread.sleep(cantidadProductos * 2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mutex.unlock(); 
            }
        cantina.recalcularPrioridad();
    }   


    @Override
    public void run() {
        /*

        */
        while (!Thread.currentThread().isInterrupted()) {
            try {
                IPedido pedido = cantina.procesarPedido();

                if (pedido == null) {
                    break;
                }

                prepararPedido(pedido);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
