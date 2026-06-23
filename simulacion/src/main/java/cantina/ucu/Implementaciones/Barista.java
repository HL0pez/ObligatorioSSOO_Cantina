package cantina.ucu.Implementaciones;

import java.util.concurrent.locks.Lock;

import cantina.ucu.Implementaciones.Productos.Cafe;
import cantina.ucu.Implementaciones.RecursosCompartidos.Cafetera;
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
                IRecursoCompartido cafetera = cantina.getCafetera();
                try {
                    System.out.println(this + " ocupa cafetera");
                    int tiempoCafe = cafetera.atender(pedido);
                    Thread.sleep(tiempoCafe * 1000);
                    System.out.println(this + " usa la cafetera por " + tiempoCafe + " segundos");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    ((Cafetera) cafetera).getSemaforo().release();
                    System.out.println(this + " libera cafetera");
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
                System.out.println(this + " prepara el resto del pedido en " + tiempoDePreparacion + " segundos");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!pedido.getEstaPago()) {
                CajaRegistradora caja = (CajaRegistradora) cantina.getCaja();
                Lock mutex = caja.getMutex();

                mutex.lock();
                System.out.println(this + " ocupa caja");
                try {
                    int cantidadProductos = caja.atender(pedido);
                    Thread.sleep(cantidadProductos * 2000);
                    System.out.println(this + " usa caja por " + cantidadProductos + " segundos");
                } finally {
                    System.out.println(this + " libera la caja");
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
