package demos;

public class JammedTurkeyCageV4 implements AutoCloseable {

	public void close() throws IllegalStateException {
		throw new IllegalStateException("Cage door does not close"); //<==
	}

	public static void main(String[] args) {
		try (JammedTurkeyCageV4 t = new JammedTurkeyCageV4()) {
			throw new RuntimeException("Turkeys ran off"); 
		} catch (IllegalStateException e) {
			System.out.println("Caught: " + e.getMessage());
			
			for (Throwable t: e.getSuppressed())
				System.out.println("Suppressed: "+t.getMessage());
			
			
		}
		System.out.println("Fin Programa");
	}
}