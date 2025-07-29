package demos;

class MiException extends Exception{
	MiException(String msg){
		super(msg);
	}
}

class Jaula implements AutoCloseable{
	@Override
	public void close() throws MiException {
		throw new MiException("Jaula no se cerro");
	}
}

public class JammedTurkeyCageV5 implements AutoCloseable {

	public void close() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Cage door does not close"); //<==
	}

	public static void main(String[] args) {
		
		try (JammedTurkeyCageV5 t = new JammedTurkeyCageV5(); //UnsupportedOperationException
			 Jaula	j = new Jaula()) { //MiException
			
			throw new IllegalStateException("Turkeys ran off"); //IllegalStateException //1
		} catch (Exception e) {
			System.out.println("Caught: "+e); //IllegalStateException
			
			for (Throwable t: e.getSuppressed()) {
				System.out.println("Suppressed: "+t);
			}
				//MiException
			    //UnsupportedOperationException
		}
		System.out.println("Fin Programa");
	}
}