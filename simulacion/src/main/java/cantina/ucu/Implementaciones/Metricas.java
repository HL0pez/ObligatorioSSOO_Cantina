package cantina.ucu.Implementaciones;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cantina.ucu.Implementaciones.Enums.Rol;
import cantina.ucu.Interfaces.IPedido;

public final class Metricas {
    private Queue<IPedido> pedidosCompletados;
    private int tiempoCafeteraOcupada;
    private Queue<IPedido> pedidosSinAtender; // no es un valor real solo se usa para contar
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
        System.out.println("\n");
    }

    private void imprimirTiempoCafeteraOcupada(){
        System.out.println("Los slots de la cafetera estuvieron ocupados " + tiempoCafeteraOcupada + " segundos entre todos");
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
                Rol rol = pedido.getCliente().getRol();
                System.out.println("El " + rol + " con pedido numero " + pedido.getId() + " demoro " + segundosPedido + " segundos en ser entregado");
            }
        }

        Double promedioTiempoTotalPedidos = (double) totalSegundos / pedidosCompletados.size();
        System.out.println("\nPromedio tiempo total de pedidos: " + promedioTiempoTotalPedidos + " segundos");
    }

    private void imprimirPromedioTiempoEnEsperaPedidos() {
        if (pedidosCompletados.isEmpty()) {
            System.out.println("Promedio tiempo total en espera de pedidos: 0.0 segundos");
            return;
        }
        
        long totalSegundos = 0;
        long segundosPedidoMax = 0;
        long segundosPedidoMaxProfesor = 0;
        IPedido pedidoMasDemorado = null;
        IPedido profesorMasDemorado = null;


        for (IPedido pedido : pedidosCompletados) {
            LocalDateTime creacion = pedido.getMomentoDeCreacion();
            LocalDateTime atencion = pedido.getMomentoDeAtencion();

            if (creacion != null && atencion != null) {
                long segundosPedido = Duration.between(creacion, atencion).getSeconds();
                if (segundosPedido > segundosPedidoMax) {
                    segundosPedidoMax = segundosPedido;
                    pedidoMasDemorado = pedido;
                }
                if (pedido.getCliente().getRol() == Rol.PROFESOR && segundosPedido > segundosPedidoMaxProfesor) {
                    segundosPedidoMaxProfesor = segundosPedido;
                    profesorMasDemorado = pedido;
                }
                totalSegundos += segundosPedido;
                System.out.println("El pedido numero " + pedido.getId() + " demoro " + segundosPedido + " segundos en ser atendido");
            }
        }
        
        Double promedioTiempoEnEsperaPedidos = (double) totalSegundos / pedidosCompletados.size();
        System.out.println("\nPromedio tiempo en espera de pedidos: " + promedioTiempoEnEsperaPedidos + " segundos");
        
        if (pedidoMasDemorado != null) {
            System.out.println("El pedido que espero en general mas fue " + pedidoMasDemorado.getId() + " con " + segundosPedidoMax + " segundos");
        }
        if (profesorMasDemorado != null) {
            System.out.println("El pedido del profesor que espero mas fue " + profesorMasDemorado.getId() + " con " + segundosPedidoMaxProfesor + " segundos");
        }
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
