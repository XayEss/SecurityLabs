package security.Security.lab3;

import java.math.BigInteger;
import java.time.ZoneOffset;
import org.apache.commons.math3.random.MersenneTwister;

public class NumberPredictor {

	// private static final double M = (long) (Math.pow(2, 32));
	private static final BigInteger M  = BigInteger.valueOf((long) Math.pow(2, 32));
	private static final int betAmount = 100;
	private Account player;

	public NumberPredictor() {
		AccountCreator creator = new AccountCreator();
		player = creator.createAccount();
	}

	public void predictLcg() {
		System.out.println(player.toString());
		GamePlayer gamer = new GamePlayer(player);
		BigInteger number1 = BigInteger.valueOf(gamer.playLcg());
		BigInteger number2 = BigInteger.valueOf(gamer.playLcg());
		BigInteger number3 = BigInteger.valueOf(gamer.playLcg());
		System.out.println(number1 + " " + number2 + " " + number3);
		System.out.println("numb1: " + number2.subtract(number1));
		System.out.println("modI: " + number2.subtract(number1).modInverse(M));
		BigInteger a = (number3.subtract(number2).multiply(number2.subtract(number1).modInverse(M))).mod(M);
		BigInteger c = number2.subtract(number1.multiply(a)).mod(M);
		System.out.println("a: " + a + " c: " + c);
		int previous = gamer.playLcg();
		int money;
		do {
			int gambleNumber = lcgGenerator(a.longValue(), c.longValue(), previous);
			System.out.println("Gamble: " + gambleNumber);
			money = player.getMoney();
			previous = gamer.playLcg(money / 2, gambleNumber);
			System.out.println("Money: " + money);
		} while (money < 1000000 && money > 1);
	}

	public int lcgGenerator(double a, double c, int previous) {
		int result = (int) ((a * previous + c) % M.longValue());
		return result;
	}

	public void predictMarsenneTwister() {
		MersenneTwister twister = new MersenneTwister();
		GamePlayer gamer = new GamePlayer(player);
		int seed = (int) player.getCreationMoment().toEpochSecond(ZoneOffset.UTC);
		twister.setSeed(seed);
		System.out.println("created: " + player.getCreationMoment().toString());
		System.out.println("Will be deleted: " + player.getDeletionMoment().toString());
		System.out.println("seed: " + seed);
		int money;
		do {
			long gamble = convertToUint(twister.nextInt());
			System.out.println("Gamble: " + gamble);
			long realNumber = gamer.playMarsenneTwister(100, gamble);
			money = player.getMoney();
			System.out.println("money: " + money);
			if (gamble == realNumber) {
				System.out.println(
						"----------------------------------------WIN----------------------------------------");
			}
		} while (money < 1000000 && money != 0);

	}

	public long convertToUint(int number) {
		long l = number;
		if (l < 0)
			l += 4294967296l;
		return l;
	}

}
