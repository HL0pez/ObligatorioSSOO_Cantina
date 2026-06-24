package cantina.ucu.Interfaces;

import java.time.LocalDateTime;
import java.util.List;

import cantina.ucu.Implementaciones.Cliente;


public interface IPedido extends Comparable<IPedido> {

    public boolean tieneCafe();
    public void calcularPrioridad();
    public List<IProducto> getProductos();
    public boolean getEstaPago();
    public Cliente getCliente();
    public int getPrioridad();
    public int getTiempoDePreparacion();
    public int getId();
    public LocalDateTime getMomentoDeCreacion();
    public void setMomentoDeCreacion();
    public LocalDateTime getMomentoDeAtencion();
    public void setMomentoDeAtencion();
    public LocalDateTime getMomentoDeEntrega();
    public void setMomentoDeEntrega();
}
