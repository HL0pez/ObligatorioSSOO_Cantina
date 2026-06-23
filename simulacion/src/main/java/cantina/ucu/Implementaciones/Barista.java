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
                    System.out.println(this + " usa cafetera");
                    Thread.sleep(tiempoCafe * 1000);

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
                CajaRegistradora caja = (CajaRegistradora) cantina.getCaja();
                Lock mutex = caja.getMutex();

                mutex.lock();
                try {
                    int cantidadProductos = caja.atender(pedido);
                    Thread.sleep(cantidadProductos * 2000);
                    System.out.println(this + " usa caja");
                } finally {
                    mutex.unlock();
                }
            }

        cantina.recalcularPrioridad();
    }   


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (cantina.estaAbierta()) {
                    IPedido pedido = cantina.procesarPedido();

                if (pedido == null) {
                    break;
                }

                prepararPedido(pedido);
                System.out.println(this + " preparó pedido " + pedido.getId());

                }
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
