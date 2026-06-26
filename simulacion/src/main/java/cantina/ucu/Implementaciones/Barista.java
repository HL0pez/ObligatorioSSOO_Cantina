package cantina.ucu.Implementaciones;

import java.util.concurrent.locks.Lock;

import cantina.ucu.Implementaciones.Productos.Cafe;
import cantina.ucu.Implementaciones.RecursosCompartidos.CajaRegistradora;
import cantina.ucu.Interfaces.ICantina;
import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IProducto;
import cantina.ucu.Interfaces.IRecursoCompartido;

public class Barista extends Thread {

    private ICantina cantina;
    private Metricas metricas = Metricas.getInstancia();

    public Barista(ICantina cantina) {
        this.cantina = cantina;
    }

    /*
    la logica de como se procesan los pedidos
    */
    private void prepararPedido(IPedido pedido) throws InterruptedException {
        if (pedido.tieneCafe()) {
                IRecursoCompartido cafetera = cantina.getCafetera();
                System.out.println(Thread.currentThread().getName() + " ocupa un slot de la cafetera");
                int tiempoCafe = cafetera.atender(pedido);
                System.out.println(Thread.currentThread().getName() + " usó el slot por " + tiempoCafe + " segundos");
                System.out.println(Thread.currentThread().getName() + " libera el slot de la cafetera");
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
                    caja.atender(pedido);
                } finally {
                    System.out.println(Thread.currentThread().getName() + " libera la caja");
                    mutex.unlock();
                }
            }
    }   

    /*
    mientras la cantina este abierta procesa pedidos y asigna sus metricas
    */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted() && cantina.estaAbierta()) {
            try {
                
                IPedido pedido = cantina.procesarPedido();                
                if (pedido == null) {
                    break;
                }
                
                System.out.println(Thread.currentThread().getName() + " tomo el pedido " + pedido.getId() );

                prepararPedido(pedido);
                System.out.println(Thread.currentThread().getName() + " preparó pedido " + pedido.getId());
                pedido.setMomentoDeEntrega();

                metricas.getPedidosCompletados().add(pedido);
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
