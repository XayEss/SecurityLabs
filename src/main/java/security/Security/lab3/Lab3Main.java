package security.Security.lab3;

import java.time.ZoneOffset;

import org.apache.commons.math3.random.MersenneTwister;

public class Lab3Main {

	public static void main(String[] args) {
		NumberPredictor predictor = new NumberPredictor();
		//predictor.predictLcg();
		predictor.predictMarsenneTwister();
		MersenneTwister twister = new MersenneTwister();
		security.Security.lab3.MersenneTwister newTwister = new security.Security.lab3.MersenneTwister();
		MTRandom twisterGenerator = new MTRandom();
		int seed = 1644084567;
		MersenneTwister19937 mt19937 = new MersenneTwister19937(1644084567);
		//seed = player.getSeedTime().toEpochSecond();
		twisterGenerator.setSeed(seed);
		twister.setSeed(seed);
		newTwister.setSeed(seed);
		for(int i = 0; i < 10; i++) {
			System.out.println("MTRand: " + twisterGenerator.nextLong());
		}
		for(int i = 0; i < 10; i++) {
			System.out.println("Mersenne: " + twister.nextLong(4294967296l));
		} 
		for(int i = 0; i < 10; i++) {
			System.out.println("Mersenne2: " + newTwister.nextLong(4294967296l));
		} 
		for(int i = 0; i < 10; i++) {
			System.out.println("Mersenne3: " + mt19937.nextIntBetween(0, Integer.MAX_VALUE));
		} 
	}
}
