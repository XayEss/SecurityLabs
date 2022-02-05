package security.Security.lab3;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountCreator {
	private static final String CREATE_ACCOUNT_TEMPLATE = "http://95.217.177.249/casino/createacc?id=%d";
	private int startId = 7777;
	
	public Account createAccount() {
		Account account = null;
		try {
			HttpURLConnection connection = null;
			URL creatorUrl = null;
			int response = 0;
			do {
				creatorUrl = new URL(String.format(CREATE_ACCOUNT_TEMPLATE, ++startId));
				connection = (HttpURLConnection)creatorUrl.openConnection();
				connection.setDoInput(true);
				connection.setRequestMethod("GET");
				//connection.setRequestProperty("id", String.valueOf(startId++));
				//connection.connect();
				response = connection.getResponseCode();
				System.out.println("id: " + startId + "response: " + response);
				System.out.println(connection.getURL());
			}while(response != HttpURLConnection.HTTP_CREATED);
			try(BufferedInputStream reader = new BufferedInputStream(connection.getInputStream())){
				String answer = new String(reader.readAllBytes());
				ObjectMapper mapper = new ObjectMapper();
				account = mapper.readerFor(Account.class).readValue(answer);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return account;
	}

}
