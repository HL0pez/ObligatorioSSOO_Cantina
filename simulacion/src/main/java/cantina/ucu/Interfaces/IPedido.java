package cantina.ucu.Interfaces;

import java.util.List;


public interface IPedido {

    public boolean tieneCafe();
    public void calcularPrioridad();
    public List<IProducto> getProductos();
    public boolean getEstaPago();
}
