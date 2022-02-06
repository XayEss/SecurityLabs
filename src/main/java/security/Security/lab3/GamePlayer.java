package security.Security.lab3;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GamePlayer {

	private Account account;
	private static final String BASE_URL_TEMPLATE = "http://95.217.177.249/casino";
	private static final String PLAY_URL_TEMPLATE = "/play%s?id=%d&bet=%d&number=%d";
	private static final String LCG = "Lcg";
	private static final String MERSENNE_TWISTER = "Mt";


	public GamePlayer(Account account) {
		super();
		this.account = account;
	}

	public int playLcg() {
		return playLcg(1, 1);
	}
	
	public int playLcg(int amount, int number) {
		return (int) play(LCG, amount, number);
	}
	
	public long playMarsenneTwister(int amount, long number) {
		return play(MERSENNE_TWISTER, amount, number);
	}
	
	public long play(String mode, int amount, long number) {
		Game game = new Game();
		//game.setAccount(account);
		try {
			HttpURLConnection connection = null;
			URL creatorUrl = null;
			int response = 0;
			creatorUrl = new URL(String.format(BASE_URL_TEMPLATE + PLAY_URL_TEMPLATE, mode, account.getId(), amount,
					number));
			connection = (HttpURLConnection) creatorUrl.openConnection();
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			// connection.connect();
			response = connection.getResponseCode();
			System.out.println("response" + response);
			System.out.println(connection.getURL());
			if (response == HttpURLConnection.HTTP_OK) {
				try (BufferedInputStream reader = new BufferedInputStream(connection.getInputStream())) {
					String answer = new String(reader.readAllBytes());
					ObjectMapper mapper = new ObjectMapper();
					game = mapper.readerFor(Game.class).readValue(answer);
				}
				account.setMoney(game.getAccount().getMoney());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("RealNum: " + game.getRealNumber());
		return game.getRealNumber();
	}
	
	public Account getAccount() {
		return account;
	}
}
