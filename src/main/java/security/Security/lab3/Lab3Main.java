package security.Security.lab3;

import java.time.ZoneOffset;

import org.apache.commons.math3.random.MersenneTwister;

public class Lab3Main {

	public static void main(String[] args) {
		NumberPredictor predictor = new NumberPredictor();
		predictor.predictLcg();
		//predictor.predictMarsenneTwister();
		findRealSeed(1644157926, 2125782690);
	}
	
	public static void findRealSeed(int seed, long result) {
		MersenneTwister twister = new MersenneTwister();
		security.Security.lab3.MersenneTwister newTwister = new security.Security.lab3.MersenneTwister();
		MTRandom twisterGenerator = new MTRandom();
		int seekingSeed = seed;
		long firstReal = result;
		//seed = player.getSeedTime().toEpochSecond();
		twisterGenerator.setSeed(seed);
		twister.setSeed(seed);
		newTwister.setSeed(seed);
		long a = 0;
		long b = 0;
		long c = 0;
		do {
			a = convertToUint(twisterGenerator.nextInt());
			b = convertToUint(twister.nextInt());
			c = convertToUint(newTwister.nextInt());
			System.out.println("MTRand: " + a);
			System.out.println("Mersenne: " + b);
			System.out.println("Mersenne2: " + c);
			System.out.println("seed: " + seed);
			seed++;
			twisterGenerator.setSeed(seed);
			twister.setSeed(seed);
			newTwister.setSeed(seed);
		}	while(a != firstReal && b != firstReal && c != firstReal);
		//seekingSeed = seed;
		System.out.println("seed difference: " + (seed - seekingSeed));
//		for(int i = 0; i < 10; i++) {
//			System.out.println("MTRand: " + convertToUint(twisterGenerator.nextInt()));
//		}
//		for(int i = 0; i < 10; i++) {
//			System.out.println("Mersenne: " + convertToUint(twister.nextInt()));
//		} 
//		for(int i = 0; i < 10; i++) {
//			System.out.println("Mersenne2: " + convertToUint(newTwister.nextInt()));
//		} 
//		for(int i = 0; i < 10; i++) {
//			System.out.println("Mersenne3: " + mt19937.nextIntBetween(0, Integer.MAX_VALUE));
//		} 
	}
	
	public static long convertToUint(int number) {
		long l = number;
		if(l < 0) 
			l += 4294967296l;
		return l;
	}
}
