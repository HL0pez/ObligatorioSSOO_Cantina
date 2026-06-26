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
            this.prioridad = 50;
        }
    }
    /*es privado porque  es el que corre después de hscer .start() */
    private void registrarPedido(IPedido pedido) {
        cantina.agregarPedido(pedido);
        if(puntosFidelidad < 100){
            ++puntosFidelidad;
        }
    }

    /*este es el que se usa en el main,  */
    public void crearPedido(ICantina cantina, List<IProducto> productos, FuenteDePedido fuenteDePedido, boolean estaPago) {
        this.pedido = new Pedido(productos, this , fuenteDePedido, estaPago);
        this.cantina = cantina;
    }
    /* crea nuevas instancias de pedido con los mismos atributos pero distinto id */
    private void crearPedido(IPedido pedido){
        this.pedido = new Pedido(pedido.getProductos(), this, pedido.getFuente(), pedido.getEstaPago());
    }


    @Override
    public void run() {
        while (cantina.estaAbierta()) {
        if (pedido != null && cantina.estaAbierta()) {
            crearPedido(pedido);
            pedido.setMomentoDeCreacion();
            System.out.println(Thread.currentThread().getName() + " hizo el pedido " + pedido.getId() + " desde " + pedido.getFuente() + " con prioridad " + pedido.getPrioridad());
            registrarPedido(pedido);
        }
        try {
            if (pedido.getFuente() == FuenteDePedido.APP) {
                Thread.sleep(10000);
            }else if (pedido.getFuente() == FuenteDePedido.TOTEM) {
                Thread.sleep(20000);
            }else if (pedido.getFuente() == FuenteDePedido.MOSTRADOR) {
                Thread.sleep(30000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
    }

// ========================================= getters =========================================

    public int getPrioridad() {
        return prioridad;
    }

    public int getPuntosFidelidad() {
        return puntosFidelidad;
    }

    public Rol getRol() {
        return rol;
    }

    
// ========================================= setters =========================================

    public void setPuntosFidelidad(int nuevosPuntos){
        this.puntosFidelidad = nuevosPuntos;
    }
}
