package cantina.ucu.Implementaciones;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cantina.ucu.Interfaces.IPedido;

public final class Metricas {
    private Queue<IPedido> pedidosCompletados;
    private int tiempoCafeteraOcupada;
    private Queue<IPedido> pedidosSinAtender;
    private double promedioTiempoTotalPedidos;
    private double promedioTiempoEnEsperaPedidos;
    private static Metricas instancia = null;
    private final Lock mutexTiempo = new ReentrantLock();
    private final Lock mutexPedidos = new ReentrantLock();
    private final Lock mutexPromedios = new ReentrantLock(); 
    
    
    private Metricas() {
        this.pedidosCompletados = new LinkedList<IPedido>();
        this.tiempoCafeteraOcupada = 0;
        this.pedidosSinAtender = new LinkedList<IPedido>();
        this.promedioTiempoTotalPedidos = 0.0;
        this.promedioTiempoEnEsperaPedidos  = 0.0;
    }

    public static Metricas getInstancia() {
        if (instancia == null) {
            instancia = new Metricas();
        }
        return instancia;
    }


    // Hacer metodos de metricas

    public void imprimirMetricas(){
        imprimirPendientes();
        imprimirPromedioTiempoEnEsperaPedidos();
        imprimirPromedioTiempoTotalPedidos();
        imprimirTiempoCafeteraOcupada();
    }

    private void imprimirTiempoCafeteraOcupada(){
        System.out.println("La cafetera estuco ocupada " + tiempoCafeteraOcupada + " segundos");
    }

    private void imprimirPromedioTiempoTotalPedidos() {
      //TODO: 
    }

    private void imprimirPromedioTiempoEnEsperaPedidos() {
      //TODO: 
    }

    private void imprimirPendientes() {
        System.out.println("Quedaron " + pedidosSinAtender.size() + " pedidos sin atender");
    }

    public void agregarTiempoCafeteraOcupada(int tiempo){
        mutexTiempo.lock();
        try {
            this.tiempoCafeteraOcupada += tiempo;
        } finally {
            mutexTiempo.unlock();
        }
    }
    
    public Queue<IPedido> getPedidosCompletados() {
        mutexPedidos.lock();
        try {
            return pedidosCompletados;
        } finally {
            mutexPedidos.unlock();
        }
    }
    public int getTiempoCafeteraOcupada() {
        mutexTiempo.lock();
        try {
            return tiempoCafeteraOcupada;
        } finally {
            mutexTiempo.unlock();
        }
    }
    public Queue<IPedido> getPedidosSinAtender() {
        mutexPedidos.lock();
        try {
            return pedidosSinAtender;
        } finally {
            mutexPedidos.unlock();
        }
    }
    public double getPromedioTiempoTotalPedidos() {
        mutexPromedios.lock();
        try {
            return promedioTiempoTotalPedidos;
        } finally {
            mutexPromedios.unlock();
        }
    }
    public double getPromedioTiempoEnEsperaPedidos() {
        mutexPromedios.lock();
        try {
            return promedioTiempoEnEsperaPedidos;
        } finally {
            mutexPromedios.unlock();
        }
    }



}
