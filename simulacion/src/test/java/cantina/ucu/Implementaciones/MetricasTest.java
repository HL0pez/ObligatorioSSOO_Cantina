package cantina.ucu.Implementaciones;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cantina.ucu.Implementaciones.Enums.FuenteDePedido;
import cantina.ucu.Implementaciones.Enums.Rol;
import cantina.ucu.Interfaces.IPedido;
import cantina.ucu.Interfaces.IProducto;

class MetricasTest {

    private Metricas metricas;

    @BeforeEach
    void setUp() {
        metricas = Metricas.getInstancia();
        metricas.getPedidosCompletados().clear();
        metricas.getPedidosSinAtender().clear();
    }

    @Test
    void deberiaPromediarTiempoDeEsperaPorProfesoresConTodosLosPedidosValidos() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            IPedido pedidoProfesor1 = new PedidoStub(crearCliente(Rol.PROFESOR), 300, 300);
            IPedido pedidoProfesor2 = new PedidoStub(crearCliente(Rol.PROFESOR), 100, 100);
            IPedido pedidoEstudiante = new PedidoStub(crearCliente(Rol.ESTUDIANTE), 60, 60);

            metricas.getPedidosCompletados().add(pedidoProfesor1);
            metricas.getPedidosCompletados().add(pedidoProfesor2);
            metricas.getPedidosCompletados().add(pedidoEstudiante);

            metricas.imprimirMetricas();
        } finally {
            System.setOut(originalOut);
        }

        String salida = output.toString();
        assertTrue(salida.contains("Promedio tiempo en espera de pedidos de profesores: 200.0 segundos"));
    }

    private Cliente crearCliente(Rol rol) {
        Cliente cliente = new Cliente(rol);
        cliente.setPuntosFidelidad(0);
        return cliente;
    }

    private static class PedidoStub implements IPedido {
        private final Cliente cliente;
        private final LocalDateTime creacion;
        private final LocalDateTime atencion;
        private final LocalDateTime entrega;
        private final int id;
        private int prioridad;

        PedidoStub(Cliente cliente, long esperaEnSegundos, int id) {
            this.cliente = cliente;
            this.creacion = LocalDateTime.now().minusSeconds(esperaEnSegundos + 10);
            this.atencion = this.creacion.plusSeconds(esperaEnSegundos);
            this.entrega = this.atencion.plusSeconds(5);
            this.id = id;
            this.prioridad = 1;
        }

        @Override
        public boolean tieneCafe() {
            return false;
        }

        @Override
        public int calcularPrioridad() {
            return prioridad;
        }

        @Override
        public List<IProducto> getProductos() {
            return new ArrayList<>();
        }

        @Override
        public boolean getEstaPago() {
            return true;
        }

        @Override
        public Cliente getCliente() {
            return cliente;
        }

        @Override
        public int getPrioridad() {
            return prioridad;
        }

        @Override
        public int getTiempoDePreparacion() {
            return 0;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public LocalDateTime getMomentoDeCreacion() {
            return creacion;
        }

        @Override
        public void setMomentoDeCreacion() {
        }

        @Override
        public LocalDateTime getMomentoDeAtencion() {
            return atencion;
        }

        @Override
        public void setMomentoDeAtencion() {
        }

        @Override
        public LocalDateTime getMomentoDeEntrega() {
            return entrega;
        }

        @Override
        public void setMomentoDeEntrega() {
        }

        @Override
        public FuenteDePedido getFuente() {
            return FuenteDePedido.APP;
        }

        @Override
        public long calcularEnvejecimiento() {
            return 0;
        }

        @Override
        public int compareTo(IPedido otroPedido) {
            return Integer.compare(this.getId(), otroPedido.getId());
        }
    }
}
