package cantina.ucu.Implementaciones.Productos;

import cantina.ucu.Interfaces.IProducto;

public class Medialuna implements IProducto {
    
    private int tiempoDePreparacion;

    public Medialuna(int tiempoDePreparacion){
        this.tiempoDePreparacion = tiempoDePreparacion;
    }
    
    public int getTiempoDePreparacion() {
        return tiempoDePreparacion;
    }
}
