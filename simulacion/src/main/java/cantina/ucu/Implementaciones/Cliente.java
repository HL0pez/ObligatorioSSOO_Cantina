package cantina.ucu.Implementaciones;

import java.util.List;

import cantina.ucu.Implementaciones.Enums.FuenteDePedido;
import cantina.ucu.Implementaciones.Enums.Rol;
import cantina.ucu.Interfaces.ICantina;
import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IProducto;

public class Cliente implements Runnable {

    private int prioridad;
    private int puntosFidelidad;
    private Rol rol;
    private IPedido pedido;
    private ICantina cantina;

    public Cliente(Rol rol){
        this.rol = rol;
        if (rol == Rol.PROFESOR) {
            this.prioridad = 5;
        }
    }
    
    public void hacerPedido(IPedido pedido) {
        for (IPedido p : cantina.getPedidosPendientes()) {
            if (p.getCliente() == this) {
                break;
            }
        }
        cantina.agregarPedido(pedido);
        ++puntosFidelidad;
    }
    
    public void hacerPedido(ICantina cantina, List<IProducto> productos, FuenteDePedido fuenteDePedido, boolean estaPago) {
        this.pedido = new Pedido(productos, this , fuenteDePedido, estaPago);
        this.cantina = cantina;
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

    @Override
    public void run() {
        if (pedido != null) {
            hacerPedido(pedido);// Hacer concurrente y repetitivo
        }
    }
    
}
