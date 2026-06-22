package cantina.ucu.Implementaciones.Productos;

import cantina.ucu.Interfaces.IProducto;

public class Cafe implements IProducto {
    
    private Double precio;
    private int tiempoDePreparacion;

    public Cafe(Double precio, int tiempoDePreparacion){
        this.tiempoDePreparacion = tiempoDePreparacion;
        this.precio = precio;
    }

    public Double getPrecio() {
        return precio;
    }

    public int getTiempoDePreparacion() {
        return tiempoDePreparacion;
    }
}
