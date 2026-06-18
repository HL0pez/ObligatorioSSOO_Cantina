package cantina.ucu.Implementaciones;

import cantina.ucu.Implementaciones.Productos.Cafe;
import cantina.ucu.Interfaces.ICantina;
import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IProducto;

public class Barista implements Runnable {

    private ICantina cantina;
    private IPedido pedido;

    public Barista(ICantina cantina) {
        this.cantina = cantina;
    }

    public void setPedido(IPedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public void run() {
        /*
        chequea si el pedido tiene cafe
        adquiere semaforoCafetera
        espera lo que demore los cafes
        libera semaforocafetera
        chequea si estaPago
        adquiere semaforoCaja
        espera 2 seg por producto
        libera semaforoCaja
        pasa pedido a completados
         */
        
        if (pedido.tieneCafe()) {
            try {
                cantina.getSemaforoCafetera().acquire();
                int tiempoCafe = 0;
                for (IProducto producto : pedido.getProductos()) {
                    if (producto instanceof Cafe) {
                        tiempoCafe += producto.getTiempoDePreparacion();
                    }
                }
                Thread.sleep(tiempoCafe * 1000);
                cantina.getCafetera().setTiempoOcupada(cantina.getCafetera().getTiempoOcupada() + tiempoCafe);
                cantina.getSemaforoCafetera().release();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (!pedido.getEstaPago()) {
            try {
                cantina.getSemaforoCafetera().acquire();
                Thread.sleep(pedido.getProductos().size() * 2000);
                cantina.getSemaforoCaja().release();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        cantina.getPedidosCompletados().add(pedido);
    }   
}
