package primitivo;

public class Principal3 {
	
	public static void main(String[] args) throws Exception{
		   int i = 1, j = 5;
		   do {
		      if (i++ > --j) {
		    	  System.out.println("");
		    	  //continue;
		      }
		   } while (i < 5);
		   System.out.println("i=" + i + " j=" + j);
		}
// i  j     if         while
// 1  5     (1 > 4)    (2 < 5)
// 2  4     (2 > 3)    (3 < 5)
// 3  3     (3 > 2)    (4 < 5)
// 4  2     (4 > 1)    (5 < 5)
// 5  1	

}
