package cantina.ucu.Interfaces;

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
}
