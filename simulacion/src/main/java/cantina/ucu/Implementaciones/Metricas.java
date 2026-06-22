package cantina.ucu.Implementaciones;

import java.util.Queue;

import cantina.ucu.Interfaces.IPedido;

public class Metricas {
    private Queue<IPedido> pedidosCompletados;
    private int tiempoCafeteraOcupada;
    private Queue<IPedido> pedidosSinAtender;
    private double promedioTiempoTotalPedidos;
    private double promedioTiempoEnEsperaPedidos;
    
    //Hacer singleton
    // Hacer metodos de metricas
    
    
    public Queue<IPedido> getPedidosCompletados() {
        return pedidosCompletados;
    }
    public int getTiempoCafeteraOcupada() {
        return tiempoCafeteraOcupada;
    }
    public Queue<IPedido> getPedidosSinAtender() {
        return pedidosSinAtender;
    }
    public double getPromedioTiempoTotalPedidos() {
        return promedioTiempoTotalPedidos;
    }
    public double getPromedioTiempoEnEsperaPedidos() {
        return promedioTiempoEnEsperaPedidos;
    }



}
