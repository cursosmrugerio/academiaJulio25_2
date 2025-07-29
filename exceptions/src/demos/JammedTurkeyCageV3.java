package demos;

public class JammedTurkeyCageV3 implements AutoCloseable {

	public void close() throws IllegalStateException {
		throw new IllegalStateException("Cage door does not close"); //2 
	}

	public static void main(String[] args) {
		try (JammedTurkeyCageV3 t = new JammedTurkeyCageV3()) {
			throw new IllegalStateException("Turkeys ran off"); //1 //<===
		} catch (IllegalStateException e) {
			System.out.println("Caught: " + e.getMessage());
			
			for (Throwable t: e.getSuppressed())
				System.out.println("Suppressed: "+t.getMessage());
			
			
		}
		System.out.println("Fin Programa");
	}
}