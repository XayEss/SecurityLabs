package security.Security.lab3;

import java.time.ZoneOffset;

import org.apache.commons.math3.random.MersenneTwister;

public class Lab3Main {

	public static void main(String[] args) {
		NumberPredictor predictor = new NumberPredictor();
		//predictor.predictLcg();
		predictor.predictMarsenneTwister();
		//findRealSeed(1644157926, 2125782690);
	}
	
	public static void findRealSeed(int seed, long result) {
		MersenneTwister twister = new MersenneTwister();
		security.Security.lab3.MersenneTwister newTwister = new security.Security.lab3.MersenneTwister();
		int seekingSeed = seed;
		long firstReal = result;
		twister.setSeed(seed);
		newTwister.setSeed(seed);
		long b = 0;
		long c = 0;
		do {
			b = convertToUint(twister.nextInt());
			c = convertToUint(newTwister.nextInt());
			System.out.println("Mersenne: " + b);
			System.out.println("Mersenne2: " + c);
			System.out.println("seed: " + seed);
			seed++;
			twister.setSeed(seed);
			newTwister.setSeed(seed);
		}	while(b != firstReal && c != firstReal);
		System.out.println("seed difference: " + (seed - seekingSeed));
	}
	
	public static long convertToUint(int number) {
		long l = number;
		if(l < 0) 
			l += 4294967296l;
		return l;
	}
}
