package cantina.ucu.Interfaces;

import java.util.List;

import cantina.ucu.Implementaciones.Enums.FuenteDePedido;

public interface IPedido {

    List<IProducto> productos = null;
    ICliente cliente = null;
    Double tiempoDePreparacion = null;
    FuenteDePedido fuente = null;
    boolean estaPago = (Boolean) null;
    Double precio = null;
    int envejecimiento = (Integer) null;
    int prioridad = (Integer) null;


    public boolean tieneCafe();
    public void calcularPrioridad();
}
