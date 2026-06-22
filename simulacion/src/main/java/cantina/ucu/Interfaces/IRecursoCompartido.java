package cantina.ucu.Interfaces;


public interface IRecursoCompartido {

    int getCantidad();

    int getTiempoOcupada();

    void setTiempoOcupada(int i);

    int atender(IPedido pedido);

}
