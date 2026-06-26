package cantina.ucu.Implementaciones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.Test;

import cantina.ucu.Implementaciones.Enums.FuenteDePedido;
import cantina.ucu.Implementaciones.Enums.Rol;
import cantina.ucu.Implementaciones.Productos.Agua;
import cantina.ucu.Implementaciones.RecursosCompartidos.CajaRegistradora;
import cantina.ucu.Implementaciones.RecursosCompartidos.Cafetera;
import cantina.ucu.Interfaces.IPedido;

class CantinaTest {

    private Cantina crearCantina() {
        return new Cantina(new Cafetera(1), new CajaRegistradora(), 1);
    }

    private Pedido crearPedido(Cliente cliente) {
        return new Pedido(List.of(new Agua(1)), cliente, FuenteDePedido.APP, true);
    }

    private void abrirCantina(Cantina cantina) throws Exception {
        Field abiertaField = Cantina.class.getDeclaredField("abierta");
        abiertaField.setAccessible(true);
        abiertaField.setBoolean(cantina, true);
    }

    @Test
    void agregarPedidoSinAbrirNoLoAgrega() throws Exception {
        Cantina cantina = crearCantina();
        Pedido pedido = crearPedido(new Cliente(Rol.ESTUDIANTE));

        cantina.agregarPedido(pedido);

        assertTrue(cantina.getPedidosPendientes().isEmpty());
    }

    @Test
    void procesarPedidoDevuelveNullCuandoNoHayPedidosYLaCantinaEstaCerrada() throws Exception {
        Cantina cantina = crearCantina();
        abrirCantina(cantina);
        cantina.cerrar();

        assertNull(cantina.procesarPedido());
    }

    @Test
    void procesarPedidoAtiendeElPedidoDeMayorPrioridad() throws Exception {
        Cantina cantina = crearCantina();
        abrirCantina(cantina);

        Cliente clienteMenor = new Cliente(Rol.ESTUDIANTE);
        clienteMenor.setPuntosFidelidad(1);
        Cliente clienteMayor = new Cliente(Rol.PROFESOR);
        clienteMayor.setPuntosFidelidad(0);

        Pedido pedidoMenor = crearPedido(clienteMenor);
        Pedido pedidoMayor = crearPedido(clienteMayor);

        cantina.agregarPedido(pedidoMenor);
        cantina.agregarPedido(pedidoMayor);

        IPedido pedidoAtendido = cantina.procesarPedido();

        assertNotNull(pedidoAtendido);
        assertEquals(pedidoMayor.getId(), pedidoAtendido.getId());
        assertNotNull(pedidoAtendido.getMomentoDeAtencion());
        assertTrue(cantina.getPedidosPendientes().contains(pedidoMenor));
    }
}
