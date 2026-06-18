package cantina.ucu.Implementaciones;

import java.util.LinkedList;
import java.util.Queue;

import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IRecursoCompartido;

public class CajaRegistradora implements IRecursoCompartido {

    private Queue<IPedido> pendientes = null;
    private int cantidad;

    public CajaRegistradora(int cantidad){
        this.pendientes = new LinkedList<>();
        this.cantidad = cantidad;
    }

    @Override
    public void atenderPedido(IPedido pedido) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atenderPedido'");
    }

    public Queue<IPedido> getPendientes() {
        return pendientes;
    }

    public int getCantidad() {
        return cantidad;
    }
    
}
