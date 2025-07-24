package logica.v2;

public class Pikachu implements IElectrico,Pokemon{

    @Override
    public void atacarPlacaje() {
        System.out.println("Hola soy Pikachu y este es mi ataque placaje");
    }

    @Override
    public void atacarAraniazo() {
        System.out.println("Hola soy Pikachu y este es mi ataque arañazo");
    }

    @Override
    public void atacarMordisco() {
        System.out.println("Hola soy Pikachu y este es mi ataque mordisco");
    }

    @Override
    public void atacarImpactrueno() {
        System.out.println("Hola soy Pikachu y este es mi ataque Impactrueno");
    }

    @Override
    public void atacarPunioTrueno() {
        System.out.println("Hola soy Pikachu y este es mi ataque Puño Trueno");
    }
    
}
