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
	private static final String LcgMode = "Lcg";
	private static final String MARSENNE_TWISTER = "Mt";


	public GamePlayer(Account account) {
		super();
		this.account = account;
	}

	public int playLcg() {
		return playLcg(100, 100);
	}
	
	public int playLcg(int amount, int number) {
		Game game = new Game();
		game.setAccount(account);
		try {
			HttpURLConnection connection = null;
			URL creatorUrl = null;
			int response = 0;
			creatorUrl = new URL(String.format(BASE_URL_TEMPLATE + PLAY_URL_TEMPLATE, LcgMode, account.getId(), amount,
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
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("RealNum: " + game.getRealNumber());
		if(number == game.getRealNumber()) {
			System.out.println("You won");
		}
		account.setMoney(game.getAccount().getMoney());
		//account = game.getAccount();
		System.out.println("current money: " + game.getAccount().getMoney());
		return game != null ? (int)game.getRealNumber() : 0;
	}
	
	public Game receiveData(InputStream input) {
			try (BufferedInputStream reader = new BufferedInputStream(input)) {
				String answer = new String(reader.readAllBytes());
				ObjectMapper mapper = new ObjectMapper();
				System.out.println(answer);
				return mapper.readerFor(Game.class).readValue(answer);
			}catch (IOException e) {				
			}
			return null;
	}
	
	public long playMarsenneTwister(int amount, long number) {
		Game game = new Game();
		URL creatorUrl = null;
		HttpURLConnection connection = null;
		int response = 0;
		try {
			creatorUrl = new URL(String.format(BASE_URL_TEMPLATE + PLAY_URL_TEMPLATE, MARSENNE_TWISTER, account.getId(), amount,
					number));
			connection = (HttpURLConnection) creatorUrl.openConnection();
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			System.out.println(connection.getURL());
			System.out.println("response: " + response);
			if(responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedInputStream reader = new BufferedInputStream(connection.getInputStream())) {
					String answer = new String(reader.readAllBytes());
					ObjectMapper mapper = new ObjectMapper();
					game = mapper.readerFor(Game.class).readValue(answer);
					System.out.println(answer);
				}
				account.setMoney(game.getAccount().getMoney());
			}else {
				System.out.println("Response Bad");
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
