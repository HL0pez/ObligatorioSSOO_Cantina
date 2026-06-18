package cantina.ucu.Interfaces;

import java.util.List;

import cantina.ucu.Implementaciones.Enums.FuenteDePedido;
import cantina.ucu.Implementaciones.Enums.Rol;

public interface ICliente {

    public void hacerPedido(ICantina cantina,List<IProducto> productos, ICliente cliente, FuenteDePedido fuenteDePedido, boolean estaPago);

    public int getPrioridad();

    public int getPuntosFidelidad();
}
