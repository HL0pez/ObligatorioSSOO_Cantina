package cantina.ucu.Implementaciones;

import java.util.List;

import cantina.ucu.Implementaciones.Enums.FuenteDePedido;
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
        this.estaPago = estaPago;
    }

    @Override
    public boolean tieneCafe() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tieneCafe'");
    }

    @Override
    public void calcularPrioridad() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calcularPrioridad'");
    }
    
}
