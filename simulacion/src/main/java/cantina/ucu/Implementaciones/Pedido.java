package cantina.ucu.Implementaciones;

import java.util.List;

import cantina.ucu.Implementaciones.Enums.FuenteDePedido;
import cantina.ucu.Implementaciones.Productos.Cafe;
import cantina.ucu.Interfaces.ICliente;
import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IProducto;

public class Pedido implements IPedido {

    private List<IProducto> productos;
    private ICliente cliente;
    private Double tiempoDePreparacion;
    private FuenteDePedido fuente;
    private boolean estaPago;
    private Double precio;
    private int envejecimiento;
    private int prioridad;

    public Pedido(List<IProducto> productos, ICliente cliente, FuenteDePedido fuenteDePedido, boolean estaPago){
        this.productos = productos;
        this.cliente = cliente;
        this.fuente = fuenteDePedido;
        for (IProducto producto : productos) {
            this.precio += producto.getPrecio();
            this.tiempoDePreparacion += producto.getTiempoDePreparacion();
        }
        if (fuenteDePedido == fuenteDePedido.MOSTRADOR) {
            estaPago = false;
        }else{
            this.estaPago = estaPago;
        }
    }

    @Override
    public boolean tieneCafe() {
        if (productos == null) {
            return false;
        }
        for (IProducto producto : productos) {
            if (producto instanceof Cafe) {
                return true;
            }
        }
        return false;
    }

    public int cantidadCafe(){
        int i = 0;
        for (IProducto producto : productos) {
            if (producto instanceof Cafe) {
                ++i;
            }
        }
        return i;
    }

    @Override
    public void calcularPrioridad() {
        ++envejecimiento;
        // Prioridad = rol + fidelidad + (cantCafe * tieneCafe) + (tiempoDeEspera * factor envejecimiento)
        prioridad = (cliente.getPrioridad() + cliente.getPuntosFidelidad() + (this.cantidadCafe() * 2 ) + (envejecimiento * 2) );
    }
    
}
