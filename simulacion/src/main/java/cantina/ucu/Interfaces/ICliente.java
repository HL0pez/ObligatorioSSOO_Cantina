package cantina.ucu.Interfaces;

import java.util.List;

import cantina.ucu.Implementaciones.Enums.FuenteDePedido;
import cantina.ucu.Implementaciones.Enums.Rol;

public interface ICliente {
    Rol rol = null;
    int puntosFidelidad = (Integer) null;

    public void hacerPedido(ICantina cantina,List<IProducto> productos, ICliente cliente, FuenteDePedido fuenteDePedido, boolean estaPago);
}
