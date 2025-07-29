package demos;

public class JammedTurkeyCageV2 implements AutoCloseable {

	public void close() throws IllegalStateException {
		throw new IllegalStateException("Cage door does not close"); //2 
	}

	public static void main(String[] args) {
		try (JammedTurkeyCageV2 t = new JammedTurkeyCageV2()) {
			throw new IllegalStateException("Turkeys ran off"); //1 //<===
		} catch (IllegalStateException e) {
			System.out.println("Caught: " + e.getMessage());
		}
		System.out.println("Fin Programa");
	}
}