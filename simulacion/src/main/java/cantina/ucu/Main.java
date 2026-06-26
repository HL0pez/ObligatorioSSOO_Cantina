package cantina.ucu;

import java.util.LinkedList;

import cantina.ucu.Implementaciones.Cantina;
import cantina.ucu.Implementaciones.Cliente;
import cantina.ucu.Implementaciones.Metricas;
import cantina.ucu.Implementaciones.Enums.FuenteDePedido;
import cantina.ucu.Implementaciones.Enums.Rol;
import cantina.ucu.Implementaciones.Productos.Agua;
import cantina.ucu.Implementaciones.Productos.Cafe;
import cantina.ucu.Implementaciones.Productos.Medialuna;
import cantina.ucu.Implementaciones.RecursosCompartidos.Cafetera;
import cantina.ucu.Implementaciones.RecursosCompartidos.CajaRegistradora;
import cantina.ucu.Interfaces.ICantina;
import cantina.ucu.Interfaces.IProducto;
import cantina.ucu.Interfaces.IRecursoCompartido;

public class Main {
    public static void main(String[] args) {

        Metricas metricas = Metricas.getInstancia();

        IProducto awita = new Agua(1);
        IProducto cortadito = new Cafe(3);
        IProducto medialunita = new Medialuna(2);
        IProducto capuchin = new Cafe(4);

        IRecursoCompartido cafetera = new Cafetera(2);
        IRecursoCompartido caja = new CajaRegistradora();

        ICantina cantinaCUCU = new Cantina(cafetera, caja, 2);

        metricas.setCantina(cantinaCUCU);
        
        LinkedList<IProducto> productos1 = new LinkedList<>();
        LinkedList<IProducto> productos2 = new LinkedList<>();
        LinkedList<IProducto> productos3 = new LinkedList<>();
        LinkedList<IProducto> productos4 = new LinkedList<>();

        productos1.add(capuchin);
        productos1.add(awita);
        productos1.add(medialunita);
        productos1.add(medialunita);

        productos2.add(capuchin);
        productos2.add(cortadito);
        productos2.add(medialunita);

        productos3.add(medialunita);
        productos3.add(awita);

        productos4.add(medialunita);
        productos4.add(medialunita);

        Cliente seba = new Cliente(Rol.PROFESOR);
        Cliente enzo = new Cliente(Rol.PROFESOR);
        Cliente pablito = new Cliente(Rol.PROFESOR);
        Cliente milgar = new Cliente(Rol.ESTUDIANTE);
        Cliente her = new Cliente(Rol.ESTUDIANTE);
        Cliente santi = new Cliente(Rol.ESTUDIANTE);
        Cliente alexis = new Cliente(Rol.ESTUDIANTE);
        Cliente axel = new Cliente(Rol.ESTUDIANTE);

        milgar.setPuntosFidelidad(25);
        santi.setPuntosFidelidad(10);
        seba.setPuntosFidelidad(20);

        seba.crearPedido(cantinaCUCU, productos1, FuenteDePedido.APP, true);
        enzo.crearPedido(cantinaCUCU, productos2, FuenteDePedido.TOTEM, false);
        pablito.crearPedido(cantinaCUCU, productos3, FuenteDePedido.MOSTRADOR, false);
        milgar.crearPedido(cantinaCUCU, productos4, FuenteDePedido.MOSTRADOR, false);
        her.crearPedido(cantinaCUCU, productos4, FuenteDePedido.APP, true);
        santi.crearPedido(cantinaCUCU, productos3, FuenteDePedido.TOTEM, false);
        alexis.crearPedido(cantinaCUCU, productos2, FuenteDePedido.MOSTRADOR, false);
        axel.crearPedido(cantinaCUCU, productos1, FuenteDePedido.MOSTRADOR, false);
        
        cantinaCUCU.abrir();

        Thread hiloSeba = new Thread(seba, "Seba");
        Thread hiloEnzo = new Thread(enzo, "Enzo");
        Thread hiloPablito = new Thread(pablito, "Pablito");
        Thread hiloMilgar = new Thread(milgar, "Mili");
        Thread hiloHer = new Thread(her, "Her");
        Thread hiloSanti = new Thread(santi, "Santi");
        Thread hiloAlexis = new Thread(alexis, "Alexis");
        Thread hiloAxel = new Thread(axel, "Axel");

        System.out.println("Iniciando simulación...");
        cantinaCUCU.simulacion(60);

        hiloSeba.start();
        hiloEnzo.start();
        hiloPablito.start();
        hiloMilgar.start();
        hiloHer.start();
        hiloSanti.start();
        hiloAlexis.start();
        hiloAxel.start();

        try {
            hiloSeba.join();
            hiloEnzo.join();
            hiloPablito.join();
            hiloMilgar.join();
            hiloHer.join();
            hiloSanti.join();
            hiloAlexis.join();
            hiloAxel.join();

            for (Thread barista : cantinaCUCU.getBaristas()) {
                barista.join();
            }

            metricas.imprimirMetricas();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}