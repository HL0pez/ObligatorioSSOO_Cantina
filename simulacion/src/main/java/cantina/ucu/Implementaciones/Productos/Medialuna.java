package cantina.ucu.Implementaciones;

import cantina.ucu.Interfaces.IProducto;

public class Medialuna implements IProducto {
    
    private Double precio;
    private Double tiempoDePreparacion;

    public Medialuna(Double precio, Double tiempoDePreparacion){
        this.tiempoDePreparacion = tiempoDePreparacion;
        this.precio = precio;
    }

    public Double getPrecio() {
        return precio;
    }

    public Double getTiempoDePreparacion() {
        return tiempoDePreparacion;
    }
}
