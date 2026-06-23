package cantina.ucu.Implementaciones;

import java.util.List;

import cantina.ucu.Implementaciones.Enums.FuenteDePedido;
import cantina.ucu.Implementaciones.Productos.Cafe;
import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IProducto;

public class Pedido implements IPedido {

    private List<IProducto> productos;
    private Cliente cliente;
    private int tiempoDePreparacion = 0;
    private FuenteDePedido fuente;
    private boolean estaPago;
    private int envejecimiento;
    private int prioridad;
    private static int contadorId;
    private int id;

    public Pedido(List<IProducto> productos, Cliente cliente, FuenteDePedido fuenteDePedido, boolean estaPago){
        this.productos = productos;
        this.cliente = cliente;
        this.fuente = fuenteDePedido;
        for (IProducto producto : productos) {
            this.tiempoDePreparacion += producto.getTiempoDePreparacion();
        }
        if (fuenteDePedido == FuenteDePedido.MOSTRADOR) {
            this.estaPago = false;
        }else{
            this.estaPago = estaPago;
        }
        this.id = ++contadorId;
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

    private int cantidadCafe(){
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
        // Prioridad = rol + fidelidad + (cantCafe * tieneCafe) + (tiempoDeEspera * factor envejecimiento)
        prioridad = (cliente.getPrioridad() + cliente.getPuntosFidelidad() + (this.cantidadCafe() * 2 ) + (envejecimiento * 2) );
        ++envejecimiento;
    }

    @Override
    public int compareTo(IPedido otroPedido) {
        
        int comparacionPrioridad = Integer.compare(otroPedido.getPrioridad(),this.getPrioridad());

        if (comparacionPrioridad != 0) {
            return comparacionPrioridad;
        }else{
            int comparacionId = Integer.compare(this.getId(), otroPedido.getId());
            return comparacionId;
        }
    }

    public List<IProducto> getProductos() {
        return productos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getTiempoDePreparacion() {
        return tiempoDePreparacion;
    }

    public FuenteDePedido getFuente() {
        return fuente;
    }

    public boolean getEstaPago() {
        return estaPago;
    }

    public int getEnvejecimiento() {
        return envejecimiento;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getId(){
        return this.id;
    }
    
}
