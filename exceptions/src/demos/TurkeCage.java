package demos;

public class TurkeCage implements AutoCloseable {
	@Override
	public void close() {
		System.out.println("Close gate");//2
	}

	public static void main(String[] args) {
		try (TurkeCage t = new TurkeCage()) {
			System.out.println("Put turkeys in"); //1
		}
	}

}