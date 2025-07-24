package logica.v2;

public class Squirtle implements IAgua,Pokemon{

    @Override
    public void atacarPlacaje() {
        System.out.println("Hola soy Squirtle y este es mi ataque placaje");
    }

    @Override
    public void atacarAraniazo() {
        System.out.println("Hola soy Squirtle y este es mi ataque arañazo");
    }

    @Override
    public  void atacarMordisco() {
        System.out.println("Hola soy Squirtle y este es mi ataque mordisco");
    }

    @Override
    public void atacarHidrobomba() {
        System.out.println("Hola soy Squirtle y este es mi ataque HidroBomba");
    }

    @Override
    public void atacarBurbuja() {
        System.out.println("Hola soy Squirtle y este es mi ataque Burbuja");
    }

    @Override
    public void atacarPistolaAgua() {
        System.out.println("Hola soy Squirtle y este es mi ataque Pistola de Agua");
    }

}
