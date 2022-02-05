package security.Security.lab3;

public class ModuloInverser {
	
	
	static int modInverse(int a, int m)
    {
      System.out.println("mod: " + a + " m: " + m);
        for (int x = 1; x < m; x++)
            if (((a%m) * (x%m)) % m == 1) {
            	System.out.println("x: " + x);
                return x;
            }
        return 1;
    }
	

}
