package cantina.ucu.Implementaciones;

import java.util.LinkedList;
import java.util.Queue;

import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IRecursoCompartido;

public class CajaRegistradora implements IRecursoCompartido {

    private int cantidad;
    private int tiempoOcupada;

    public CajaRegistradora(int cantidad){
        this.cantidad = cantidad;
    }

    @Override
    public void atenderPedido(IPedido pedido) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atenderPedido'");
    }

    public int getCantidad() {
        return cantidad;
    }
    
}
