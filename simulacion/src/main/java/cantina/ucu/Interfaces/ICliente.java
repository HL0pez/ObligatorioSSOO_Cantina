package cantina.ucu.Interfaces;

import cantina.ucu.Implementaciones.Enums.Rol;

public interface ICliente {
    Rol rol = null;
    int puntosFidelidad = (Integer) null;

    public void hacerPedido(IPedido pedido);
}
