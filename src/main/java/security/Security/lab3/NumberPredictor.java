package security.Security.lab3;

import java.math.BigInteger;
import java.time.ZoneOffset;
import org.apache.commons.math3.random.MersenneTwister;

public class NumberPredictor {

	//private static final double M = (long) (Math.pow(2, 32));
	private static final BigInteger M = BigInteger.valueOf((long) Math.pow(2, 32));
	private Account player;

	public void predictLcg() {
		AccountCreator creator = new AccountCreator();
		player = creator.createAccount();
		System.out.println(player.toString());
		GamePlayer gamer = new GamePlayer(player);
//		int number1 = gamer.playLcg();
//		int number2 = gamer.playLcg();
//		int number3 = gamer.playLcg();
		BigInteger number1 = BigInteger.valueOf(gamer.playLcg());
		BigInteger number2 = BigInteger.valueOf(gamer.playLcg());
		BigInteger number3 = BigInteger.valueOf(gamer.playLcg());
		System.out.println(number1 + " " + number2 + " " + number3);
//		double a = ((number3-number2) * ModuloInverser.modInverse((number2 - number1), (int) M) % M);
//		double c = ((number2 - number1 * a) % M);
		System.out.println("numb1: " + number2.subtract(number1));
		System.out.println("modI: " + number2.subtract(number1).modInverse(M));
		BigInteger a = (number3.subtract(number2).multiply(number2.subtract(number1).modInverse(M))).mod(M);
		BigInteger c = number2.subtract(number1.multiply(a)).mod(M);
		System.out.println("a: " + a + " c: " + c);
		int previous = gamer.playLcg();
		do {
		int gambleNumber = lcgGenerator(a.longValue(), c.longValue(), previous);
		System.out.println("Gamble: " + gambleNumber);
		previous = gamer.playLcg(player.getMoney()/2, gambleNumber);
		//player = gamer.getAccount();
		System.out.println("player money: " + player.getMoney());
		if(player.getMoney() == 1) {
			break;
		}
		}while(player.getMoney() < 1000000);
	}

	public int lcgGenerator(double a, double c, int previous) {
		int result = (int) ((a * previous + c) % M.longValue());
		return result;
	}
	
	public void predictMarsenneTwister() {
		if(player == null) {
			AccountCreator creator = new AccountCreator();
			player = creator.createAccount();
		}
		MersenneTwister twister = new MersenneTwister();
		GamePlayer gamer = new GamePlayer(player);
		MTRandom twisterGenerator = new MTRandom();
		long seed = (int) player.getCreationMoment().toEpochSecond(ZoneOffset.UTC);
		//seed = player.getSeedTime().toEpochSecond();
		twisterGenerator.setSeed(seed);
		twister.setSeed(seed);
		//twisterGenerator.setSeed(player.getCreationMoment().get);
		System.out.println("created: " + player.getCreationMoment().toString());
		System.out.println("Will be deleted: " + player.getDeletionMoment().toString());
		System.out.println("seed: " + seed);
		System.out.println("lol:" + 0xfffffff);	
		System.out.println(-5 & 10);
		MersenneTwister19937 mt19937 = new MersenneTwister19937(seed);
		do {
		//long gamble = (long) Math.floor(twisterGenerator.nextDouble() * 0xfffffff); 
		//long gamble = twister.nextLong(4294967296l);
		long gamble = twister.nextInt(Integer.MAX_VALUE);
		//long gamble = mt19937.nextInt();
		System.out.println("Gamble: " + gamble);
		System.out.println("gamblexxxxx:" + (gamble & 0xffffffff));	
		long result = gamer.playMarsenneTwister(10, gamble);
		System.out.println("money: " + player.getMoney());
		if(gamble != result) {
			player.setCreationMoment(player.getCreationMoment().plusSeconds(1));
			seed = player.getCreationMoment().toEpochSecond(ZoneOffset.UTC);
			twister.setSeed(seed);
		}
		}while(player.getMoney() < 1000000 && player.getMoney() != 0);
		
		
	}

}
