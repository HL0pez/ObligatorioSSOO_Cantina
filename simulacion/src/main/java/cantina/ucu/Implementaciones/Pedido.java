package cantina.ucu.Implementaciones;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import cantina.ucu.Implementaciones.Enums.FuenteDePedido;
import cantina.ucu.Implementaciones.Enums.Rol;
import cantina.ucu.Implementaciones.Productos.Cafe;
import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IProducto;

public class Pedido implements IPedido {

    private List<IProducto> productos;
    private Cliente cliente;
    private int tiempoDePreparacion = 0;
    private FuenteDePedido fuente;
    private boolean estaPago;
    private int prioridad;
    private static int contadorId;
    private int id;
    private LocalDateTime momentoDeCreacion;
    private LocalDateTime momentoDeAtencion;
    private LocalDateTime momentoDeEntrega;
    private int bonusCafe = 2;

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
        synchronized (Pedido.class) {
            this.id = ++contadorId;
        }
        this.momentoDeCreacion = LocalDateTime.now();
        this.prioridad = calcularPrioridad();
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
    public int calcularPrioridad() {
        // Prioridad = rol + fidelidad + (cantCafe * bonusCafe) + (tiempoDeEspera)
        System.out.println("Prioridad anterior del pedido " + this.id + " " + this.prioridad);
        int nuevaPrioridad = (cliente.getPrioridad() + cliente.getPuntosFidelidad() + (this.cantidadCafe() * bonusCafe ) + (int)calcularEnvejecimiento() * 2);
        
        if (calcularEnvejecimiento() > 300) {
            this.prioridad = 99999999;
            return this.prioridad;
        }
        
        if (cliente.getRol() == Rol.PROFESOR && calcularEnvejecimiento() > 200) {
        this.prioridad = 99999999;
        return this.prioridad;
        }
        
        this.prioridad = nuevaPrioridad;
        System.out.println("Lleva " + calcularEnvejecimiento() + "s esperando");
        System.out.println("Prioridad nueva del pedido " + this.id + " " + this.prioridad);
        
        return nuevaPrioridad;
    }

    @Override
    public long calcularEnvejecimiento(){
        return Duration.between(momentoDeCreacion, LocalDateTime.now()).getSeconds();
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
