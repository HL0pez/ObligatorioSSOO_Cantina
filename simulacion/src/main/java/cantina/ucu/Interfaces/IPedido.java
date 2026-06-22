package cantina.ucu.Interfaces;

import java.util.List;

import cantina.ucu.Implementaciones.Cliente;


public interface IPedido {

    public boolean tieneCafe();
    public void calcularPrioridad();
    public List<IProducto> getProductos();
    public boolean getEstaPago();
    public Cliente getCliente();
}
