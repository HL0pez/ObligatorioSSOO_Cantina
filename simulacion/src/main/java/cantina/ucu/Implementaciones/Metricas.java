package cantina.ucu.Implementaciones;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cantina.ucu.Implementaciones.Enums.Rol;
import cantina.ucu.Interfaces.ICantina;
import cantina.ucu.Interfaces.IPedido;

public final class Metricas {
    private Queue<IPedido> pedidosCompletados;
    private int tiempoCafeteraOcupada;
    private static Metricas instancia = null;
    private final Lock mutexTiempo = new ReentrantLock();
    private final Lock mutexPedidos = new ReentrantLock();
    private ICantina cantina;
    
    private Metricas() {
        this.pedidosCompletados = new LinkedList<IPedido>();
        this.tiempoCafeteraOcupada = 0;
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
        int pedidosValidos = 0;

        for (IPedido pedido : pedidosCompletados) {
            LocalDateTime creacion = pedido.getMomentoDeCreacion();
            LocalDateTime entrega = pedido.getMomentoDeEntrega();
            if (creacion != null && entrega != null) {
                long segundosPedido = Duration.between(creacion, entrega).getSeconds();
                totalSegundos += segundosPedido;
                pedidosValidos++;
                Rol rol = pedido.getCliente().getRol();
                System.out.println("El " + rol + " con pedido numero " + pedido.getId() + " demoro " + segundosPedido + " segundos en ser entregado");
            }
        }

        double promedioTiempoTotalPedidos = pedidosValidos == 0 ? 0.0 : (double) totalSegundos / pedidosValidos;
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
        long totalSegundosProfesor = 0;
        long totalSegundosAlumno = 0;
        IPedido pedidoMasDemorado = null;
        IPedido profesorMasDemorado = null;
        int cantAlumnos = 0;
        int cantProfes = 0;
        int pedidosValidos = 0;

        for (IPedido pedido : pedidosCompletados) {
            LocalDateTime creacion = pedido.getMomentoDeCreacion();
            LocalDateTime atencion = pedido.getMomentoDeAtencion();

            if (creacion != null && atencion != null) {
                pedidosValidos++;
                long segundosPedido = Duration.between(creacion, atencion).getSeconds();
                if (segundosPedido > segundosPedidoMax) {
                    segundosPedidoMax = segundosPedido;
                    pedidoMasDemorado = pedido;
                }
                if (pedido.getCliente().getRol() == Rol.PROFESOR) {
                    totalSegundosProfesor += segundosPedido;
                    cantProfes++;
                    if (segundosPedido > segundosPedidoMaxProfesor) {
                        segundosPedidoMaxProfesor = segundosPedido;
                        profesorMasDemorado = pedido;
                    }
                }

                if (pedido.getCliente().getRol() == Rol.ESTUDIANTE) {
                    totalSegundosAlumno += segundosPedido;
                    cantAlumnos++;
                }
                totalSegundos += segundosPedido;
                System.out.println("El " + pedido.getCliente().getRol() + " con pedido numero " + pedido.getId() + " demoro " + segundosPedido + " segundos en ser atendido");
            }
        }

        if (pedidosValidos != 0) {
            double promedioTiempoEnEsperaPedidos = (double) totalSegundos / pedidosValidos;
            System.out.println("\nPromedio tiempo en espera de pedidos: " + promedioTiempoEnEsperaPedidos + " segundos");
        }

        if (cantProfes != 0) {
            double promedioTiempoEnEsperaPedidosProfe = (double) totalSegundosProfesor / cantProfes;
            System.out.println("\nPromedio tiempo en espera de pedidos de profesores: " + promedioTiempoEnEsperaPedidosProfe + " segundos");
        }

        if (cantAlumnos != 0) {
            double promedioTiempoEnEsperaPedidosEstudiante = (double) totalSegundosAlumno / cantAlumnos;
            System.out.println("\nPromedio tiempo en espera de pedidos de alumnos: " + promedioTiempoEnEsperaPedidosEstudiante + " segundos");
        }
        
        if (pedidoMasDemorado != null) {
            System.out.println("El pedido que espero en general mas fue " + pedidoMasDemorado.getId() + " con " + segundosPedidoMax + " segundos en ser atendido");
        }
        
        if (profesorMasDemorado != null) {
            System.out.println("El pedido del profesor que espero mas fue " + profesorMasDemorado.getId() + " con " + segundosPedidoMaxProfesor + " segundos en ser atendido");
        }
    }

    private void imprimirPendientes() {
        if (this.cantina != null) {
            System.out.println("Quedaron " + cantina.getPedidosPendientes().size() + " pedidos sin atender");        
        }
        for (IPedido pedido : cantina.getPedidosPendientes()) {
            long espera = Duration.between(pedido.getMomentoDeCreacion(), LocalDateTime.now()).getSeconds();
            if (espera > 300) {
                System.out.println("El pedido" + pedido.getId() + " supero el umbral de espera de 5 minutos");
            }
        }
    }

    public void agregarTiempoCafeteraOcupada(int tiempo){
        mutexTiempo.lock();
        try {
            this.tiempoCafeteraOcupada += tiempo;
        } finally {
            mutexTiempo.unlock();
        }
    }
    
// ========================================= getters =========================================
    
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

    // ========================================= getters =========================================

    public void setCantina(ICantina cantina){
        this.cantina = cantina;
    }
}
