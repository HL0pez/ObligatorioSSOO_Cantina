package cantina.ucu.Implementaciones.Productos;

import cantina.ucu.Interfaces.IProducto;

public class Cafe implements IProducto {
    
    private int tiempoDePreparacion;

    public Cafe(int tiempoDePreparacion){
        this.tiempoDePreparacion = tiempoDePreparacion;
    }

    public int getTiempoDePreparacion() {
        return tiempoDePreparacion;
    }
}
