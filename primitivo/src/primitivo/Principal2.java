package primitivo;

public class Principal2 {
	
	public static void main(String[] args) throws Exception{
		   int i = 1, j = 10;
		   do {
		      if (i++ > --j) { 
		    	  continue;
		      }
		   } while (i < 5);
		   System.out.println("i=" + i + " j=" + j);
		}
// i  j     if         while
// 1  10	(1 > 9)    (2 < 5)
// 2   9    (2 > 8)    (3 < 5)
// 3   8    (3 > 7)    (4 < 5)
// 4   7    (4 > 6)    (5 < 5)	
// 5   6

}
