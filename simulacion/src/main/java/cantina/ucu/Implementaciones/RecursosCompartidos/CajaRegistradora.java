package cantina.ucu.Implementaciones.RecursosCompartidos;


import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IRecursoCompartido;

public class CajaRegistradora implements IRecursoCompartido {
    private final Lock mutex = new ReentrantLock(); 

    public Lock getMutex() {
        return mutex;
    }

    /*
    Usa la caja 2 segundos por producto
    */
    @Override
    public int atender(IPedido pedido) {
        int cantidadProductos = pedido.getProductos().size();
        try {
            Thread.sleep(cantidadProductos * 2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " usa caja por " + cantidadProductos + " segundos");
        return pedido.getProductos().size();

    }
        
}
