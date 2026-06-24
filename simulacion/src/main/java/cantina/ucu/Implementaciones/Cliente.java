package cantina.ucu.Implementaciones;

import java.util.List;

import cantina.ucu.Implementaciones.Enums.FuenteDePedido;
import cantina.ucu.Implementaciones.Enums.Rol;
import cantina.ucu.Interfaces.ICantina;
import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IProducto;

public class Cliente extends Thread {

    private int prioridad;
    private int puntosFidelidad;
    private Rol rol;
    private IPedido pedido;
    private ICantina cantina;

    public Cliente(Rol rol){
        this.rol = rol;
        if (rol == Rol.PROFESOR) {
            this.prioridad = 5;
        }
    }
    /*es privado porque  es el que corre después de hscer .start() */
    private void registrarPedido(IPedido pedido) {
        cantina.agregarPedido(pedido);
        ++puntosFidelidad;
    }

    /*este es el que se usa en el main,  */
    public void crearPedido(ICantina cantina, List<IProducto> productos, FuenteDePedido fuenteDePedido, boolean estaPago) {
        this.pedido = new Pedido(productos, this , fuenteDePedido, estaPago);
        this.cantina = cantina;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getPuntosFidelidad() {
        return puntosFidelidad;
    }

    public Rol getRol() {
        return rol;
    }

    public void setPuntosFidelidad(int nuevosPuntos){
        this.puntosFidelidad = nuevosPuntos;
    }

    @Override
    public void run() {
        if (pedido != null && cantina.estaAbierta()) {
            registrarPedido(pedido);
        }else{
            System.out.println("No se pudo hacer el pedido");
        }
    }
    
}
