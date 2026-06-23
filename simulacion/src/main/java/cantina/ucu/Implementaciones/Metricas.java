package cantina.ucu.Implementaciones;

import java.util.LinkedList;
import java.util.Queue;

import cantina.ucu.Interfaces.IPedido;

public final class Metricas {
    private Queue<IPedido> pedidosCompletados;
    private int tiempoCafeteraOcupada;
    private Queue<IPedido> pedidosSinAtender;
    private double promedioTiempoTotalPedidos;
    private double promedioTiempoEnEsperaPedidos;
<<<<<<< HEAD
    private static Metricas instancia = null;
    
=======
    private static Metricas metricas;
    
    //Hacer singleton
>>>>>>> 7cf90c79438e0c2fb9e451d10971e893175ef1b8
    private Metricas() {
        this.pedidosCompletados = new LinkedList<IPedido>();
        this.tiempoCafeteraOcupada = 0;
        this.pedidosSinAtender = new LinkedList<IPedido>();
        this.promedioTiempoTotalPedidos = 0.0;
<<<<<<< HEAD
        this.promedioTiempoEnEsperaPedidos  = 0.0;
    }

    public static Metricas getInstancia() {
        if (instancia == null) {
            instancia = new Metricas();
        }
        return instancia;
    }


=======
        this.promedioTiempoEnEsperaPedidos = 0.0;
    }

    public static Metricas getMetricas() {
        if (metricas == null) {
            metricas = new Metricas();
        }
        return metricas;
    }

>>>>>>> 7cf90c79438e0c2fb9e451d10971e893175ef1b8
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
