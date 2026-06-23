package cantina.ucu.Implementaciones.Productos;

import cantina.ucu.Interfaces.IProducto;

public class Agua implements IProducto {
    private int tiempoDePreparacion;

    public Agua(int tiempoDePreparacion){
        this.tiempoDePreparacion = tiempoDePreparacion;
    }

    public int getTiempoDePreparacion() {
        return tiempoDePreparacion;
    }
}
