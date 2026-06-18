package cantina.ucu.Implementaciones;

import java.util.List;

import cantina.ucu.Implementaciones.Enums.FuenteDePedido;
import cantina.ucu.Implementaciones.Enums.Rol;
import cantina.ucu.Interfaces.ICliente;
import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IProducto;

public class Cliente implements ICliente {

    private int prioridad;
    private int puntosFidelidad;
    private Rol rol;

    public Cliente(Rol rol){
        this.rol = rol;
        if (rol == Rol.PROFESOR) {
            this.prioridad = 5;
        }
    }
    
    @Override
    public IPedido hacerPedido(List<IProducto> productos, ICliente cliente, FuenteDePedido fuenteDePedido, boolean estaPago) {
        return new Pedido(productos, cliente, fuenteDePedido, estaPago);
    }
    
}
