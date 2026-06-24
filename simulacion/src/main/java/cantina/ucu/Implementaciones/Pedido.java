package cantina.ucu.Implementaciones;

import java.time.LocalDateTime;
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
    private LocalDateTime momentoDeCreacion;
    private LocalDateTime momentoDeAtencion;
    private LocalDateTime momentoDeEntrega;

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
        this.momentoDeCreacion = LocalDateTime.now();
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

    public void setProductos(List<IProducto> productos) {
        this.productos = productos;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setTiempoDePreparacion(int tiempoDePreparacion) {
        this.tiempoDePreparacion = tiempoDePreparacion;
    }

    public void setFuente(FuenteDePedido fuente) {
        this.fuente = fuente;
    }

    public void setEstaPago(boolean estaPago) {
        this.estaPago = estaPago;
    }

    public void setEnvejecimiento(int envejecimiento) {
        this.envejecimiento = envejecimiento;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public static int getContadorId() {
        return contadorId;
    }

    public static void setContadorId(int contadorId) {
        Pedido.contadorId = contadorId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getMomentoDeCreacion() {
        return momentoDeCreacion;
    }

    public void setMomentoDeCreacion() {
        this.momentoDeCreacion = LocalDateTime.now();
    }

    public LocalDateTime getMomentoDeAtencion() {
        return momentoDeAtencion;
    }

    public void setMomentoDeAtencion() {
        this.momentoDeAtencion = LocalDateTime.now();
    }

    public LocalDateTime getMomentoDeEntrega() {
        return momentoDeEntrega;
    }

    public void setMomentoDeEntrega() {
        this.momentoDeEntrega = LocalDateTime.now();
    }
    
}
