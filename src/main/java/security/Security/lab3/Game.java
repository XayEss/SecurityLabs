package security.Security.lab3;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Game {
	private String message;
	private Account account;
	private long realNumber;
	
	@JsonGetter("message")
	public String getMessage() {
		return message;
	}
	@JsonSetter("message")
	public void setMessage(String message) {
		this.message = message;
	}
	@JsonGetter("account")
	public Account getAccount() {
		return account;
	}
	@JsonSetter("account")
	public void setAccount(Account account) {
		this.account = account;
	}
	@JsonGetter("realNumber")
	public long getRealNumber() {
		return realNumber;
	}
	@JsonSetter("realNumber")
	public void setRealNumber(long realNumber) {
		this.realNumber = realNumber;
	}
	
	
}
