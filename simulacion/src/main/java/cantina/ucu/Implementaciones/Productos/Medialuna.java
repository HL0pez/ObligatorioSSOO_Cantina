package cantina.ucu.Implementaciones.Productos;

import cantina.ucu.Interfaces.IProducto;

public class Medialuna implements IProducto {
    
    private Double precio;
    private int tiempoDePreparacion;

    public Medialuna(Double precio, int tiempoDePreparacion){
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
