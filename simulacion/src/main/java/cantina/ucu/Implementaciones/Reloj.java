package cantina.ucu.Implementaciones;

import cantina.ucu.Interfaces.ICantina;

public class Reloj implements Runnable{

    private int tiempo;
    private ICantina cantina;

    public Reloj(int segundos, ICantina cantina){
        this.tiempo = segundos;
        this.cantina = cantina;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(this.tiempo * 1000);
            cantina.cerrar();
            System.out.println("=====================================================");
            System.out.println("Ya cerramos! No admitimos mas pedidos. volvé pronto!!");
            System.out.println("=====================================================");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
