package cantina.ucu.Implementaciones;

import java.util.List;

import cantina.ucu.Implementaciones.Enums.FuenteDePedido;
import cantina.ucu.Implementaciones.Enums.Rol;
import cantina.ucu.Interfaces.ICantina;
import cantina.ucu.Interfaces.ICliente;
import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IProducto;

public class Cliente implements ICliente {

    private int prioridad;
    private int puntosFidelidad;
    private Rol rol;

    public Cliente(Rol rol){
        this.rol = rol;
        if (rol == Rol.PROFESOR) {
            this.prioridad = 5;
        }
    }
    
    @Override
    public void hacerPedido(ICantina cantina, List<IProducto> productos, ICliente cliente, FuenteDePedido fuenteDePedido, boolean estaPago) {
        IPedido pedido = new Pedido(productos, cliente, fuenteDePedido, estaPago);
        cantina.agregarPedido(pedido);
        ++puntosFidelidad;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getPuntosFidelidad() {
        return puntosFidelidad;
    }

    public Rol getRol() {
        return rol;
    }
    
}
