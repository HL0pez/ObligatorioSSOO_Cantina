package cantina.ucu.Implementaciones;


public class Reloj implements Runnable{

    private int tiempo;

    public Reloj(int segundos){
        this.tiempo = segundos;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(this.tiempo * 1000);
            System.out.println("=====================================================");
            System.out.println("Ya cerramos! No admitimos mas pedidos. volvé pronto!!");
            System.out.println("=====================================================");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
