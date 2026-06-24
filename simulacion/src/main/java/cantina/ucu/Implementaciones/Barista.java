package cantina.ucu.Implementaciones;

import java.util.concurrent.locks.Lock;

import cantina.ucu.Implementaciones.Productos.Cafe;
import cantina.ucu.Implementaciones.RecursosCompartidos.Cafetera;
import cantina.ucu.Implementaciones.RecursosCompartidos.CajaRegistradora;
import cantina.ucu.Interfaces.ICantina;
import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IProducto;
import cantina.ucu.Interfaces.IRecursoCompartido;

public class Barista extends Thread {

    private ICantina cantina;

    public Barista(ICantina cantina) {
        this.cantina = cantina;
    }

    private void prepararPedido(IPedido pedido) throws InterruptedException {
        if (pedido.tieneCafe()) {
                IRecursoCompartido cafetera = cantina.getCafetera();
                try {
                    System.out.println(Thread.currentThread().getName() + " ocupa un slot de la cafetera");
                    int tiempoCafe = cafetera.atender(pedido);
                    System.out.println(Thread.currentThread().getName() + " usa el slot por " + tiempoCafe + " segundos");
                    Thread.sleep(tiempoCafe * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    ((Cafetera) cafetera).getSemaforo().release();
                    System.out.println(Thread.currentThread().getName() + " libera el slot de la cafetera");
                }
            }

            int tiempoDePreparacion = 0;
            for (IProducto producto : pedido.getProductos()) {
                if (!(producto instanceof Cafe)) {
                    tiempoDePreparacion += producto.getTiempoDePreparacion();
                }
            }

            try {
                System.out.println(Thread.currentThread().getName() + " prepara el resto del pedido en " + tiempoDePreparacion + " segundos");
                Thread.sleep(tiempoDePreparacion * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!pedido.getEstaPago()) {
                CajaRegistradora caja = (CajaRegistradora) cantina.getCaja();
                Lock mutex = caja.getMutex();

                mutex.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " ocupa caja");
                    int cantidadProductos = caja.atender(pedido);
                    System.out.println(Thread.currentThread().getName() + " usa caja por " + cantidadProductos + " segundos");
                    Thread.sleep(cantidadProductos * 2000);
                } finally {
                    System.out.println(Thread.currentThread().getName() + " libera la caja");
                    mutex.unlock();
                    pedido.setMomentoDeEntrega();
                }
            }

        cantina.recalcularPrioridad();
    }   


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && cantina.estaAbierta()) {
            try {
                
                IPedido pedido = cantina.procesarPedido();
                if (pedido == null) {
                    break;
                }

                prepararPedido(pedido);
                System.out.println(Thread.currentThread().getName() + " preparó pedido " + pedido.getId() + " del cliente " + pedido.getCliente().getName());
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
