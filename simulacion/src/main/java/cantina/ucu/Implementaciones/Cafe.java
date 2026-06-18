package cantina.ucu.Implementaciones;

import cantina.ucu.Interfaces.IProducto;

public class Cafe implements IProducto {
    
    private Double precio;
    private Double tiempoDePreparacion;

    public Cafe(Double precio, Double tiempoDePreparacion){
        this.tiempoDePreparacion = tiempoDePreparacion;
        this.precio = precio;
    }
}
