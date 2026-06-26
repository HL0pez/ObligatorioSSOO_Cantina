package cantina.ucu.Implementaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cantina.ucu.Implementaciones.Enums.FuenteDePedido;
import cantina.ucu.Implementaciones.Enums.Rol;
import cantina.ucu.Implementaciones.Productos.Agua;
import cantina.ucu.Implementaciones.RecursosCompartidos.CajaRegistradora;
import cantina.ucu.Implementaciones.RecursosCompartidos.Cafetera;

class MetricasTest {

    @BeforeEach
    void reset() {
        Metricas metricas = Metricas.getInstancia();
        metricas.setCantina(null);
    }

    @Test
    void agregaTiempoDeCafeteraYLoDevuelve() {
        Metricas metricas = Metricas.getInstancia();

        metricas.agregarTiempoCafeteraOcupada(5);
        metricas.agregarTiempoCafeteraOcupada(7);

        assertEquals(12, metricas.getTiempoCafeteraOcupada());
    }

    @Test
    void registraPedidosCompletados() {
        Metricas metricas = Metricas.getInstancia();
        Pedido pedido = new Pedido(List.of(new Agua(1)), new Cliente(Rol.ESTUDIANTE), FuenteDePedido.APP, true);
        metricas.getPedidosCompletados().add(pedido);

        assertNotNull(metricas.getPedidosCompletados());
        assertEquals(1, metricas.getPedidosCompletados().size());
    }

    @Test
    void setCantinaPermiteAsignarLaCantina() throws Exception {
        Metricas metricas = Metricas.getInstancia();
        Cantina cantina = new Cantina(new Cafetera(1), new CajaRegistradora(), 1);

        metricas.setCantina(cantina);

        Field cantinaField = Metricas.class.getDeclaredField("cantina");
        cantinaField.setAccessible(true);
        assertEquals(cantina, cantinaField.get(metricas));
    }
}
