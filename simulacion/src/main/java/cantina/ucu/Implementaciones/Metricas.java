package cantina.ucu.Implementaciones;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cantina.ucu.Interfaces.IPedido;

public final class Metricas {
    private Queue<IPedido> pedidosCompletados;
    private int tiempoCafeteraOcupada;
    private Queue<IPedido> pedidosSinAtender;
    private static Metricas instancia = null;
    private final Lock mutexTiempo = new ReentrantLock();
    private final Lock mutexPedidos = new ReentrantLock();
    
    private Metricas() {
        this.pedidosCompletados = new LinkedList<IPedido>();
        this.tiempoCafeteraOcupada = 0;
        this.pedidosSinAtender = new LinkedList<IPedido>();
    }

    public static Metricas getInstancia() {
        if (instancia == null) {
            instancia = new Metricas();
        }
        return instancia;
    }

    public void imprimirMetricas(){
        System.out.println("\n");
        imprimirPendientes();
        System.out.println("\n");
        imprimirPromedioTiempoEnEsperaPedidos();
        System.out.println("\n");
        imprimirPromedioTiempoTotalPedidos();
        System.out.println("\n");
        imprimirTiempoCafeteraOcupada();
    }

    private void imprimirTiempoCafeteraOcupada(){
        System.out.println("La cafetera estuvo ocupada " + tiempoCafeteraOcupada + " segundos");
    }

    private void imprimirPromedioTiempoTotalPedidos() {
        if (pedidosCompletados.isEmpty()) {
            System.out.println("Promedio tiempo total de pedidos: 0.0 segundos");
            return;
        }

        long totalSegundos = 0;

        for (IPedido pedido : pedidosCompletados) {
            LocalDateTime creacion = pedido.getMomentoDeCreacion();
            LocalDateTime entrega = pedido.getMomentoDeEntrega();

            if (creacion != null && entrega != null) {
                long segundosPedido = Duration.between(creacion, entrega).getSeconds();
                totalSegundos += segundosPedido;
                System.out.println("El pedido numero " + pedido.getId() + " demoro " + segundosPedido + " segundos en ser entregado");
            }
        }

        Double promedioTiempoTotalPedidos = (double) totalSegundos / pedidosCompletados.size();
        System.out.println("Promedio tiempo total de pedidos: " + promedioTiempoTotalPedidos + " segundos");
    }

    private void imprimirPromedioTiempoEnEsperaPedidos() {
        if (pedidosCompletados.isEmpty()) {
            System.out.println("Promedio tiempo total en espera de pedidos: 0.0 segundos");
            return;
        }
        
        long totalSegundos = 0;

        for (IPedido pedido : pedidosCompletados) {
            LocalDateTime creacion = pedido.getMomentoDeCreacion();
            LocalDateTime atencion = pedido.getMomentoDeAtencion();

            if (creacion != null && atencion != null) {
                long segundosPedido = Duration.between(creacion, atencion).getSeconds();
                totalSegundos += segundosPedido;
                System.out.println("El pedido numero " + pedido.getId() + " demoro " + segundosPedido + " segundos en ser atendido");
            }
        }
        Double promedioTiempoEnEsperaPedidos = (double) totalSegundos / pedidosCompletados.size();
        System.out.println("Promedio tiempo en espera de pedidos: " + promedioTiempoEnEsperaPedidos + " segundos");
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



}
