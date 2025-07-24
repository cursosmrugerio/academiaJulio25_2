package logica.v1;

public class EjercicioIntegradorPokemon {

	public static void main(String[] args) {
        
        Pokemon[] arregloPokemon = {new Squirtle(),
        		new Charmander(),
        		new Bulbasaur(),
        		new Pikachu()
        };
        
        for (Pokemon pok: arregloPokemon) {
        	System.out.println("*****"+pok.getClass().getSimpleName());
        	pok.atacarAraniazo();
        	pok.atacarMordisco();
        	pok.atacarPlacaje();
        	if (pok instanceof IAgua) {
        		System.out.println("*IAgua");
        		((IAgua)pok).atacarHidrobomba();
        	}
        	if (pok instanceof IFuego) {
        		System.out.println("*IFuego");
        		((IFuego)pok).atacarLanzaLlamas();
        	}
        	if (pok instanceof IPlanta) {
        		System.out.println("*IPlanta");
        		((IPlanta)pok).atacarDrenaje();
        	}
        	if (pok instanceof IElectrico) {
        		System.out.println("*IElectrico");
        		((IElectrico)pok).atacarImpactrueno();
        	}
        }
      
    }
    
}
