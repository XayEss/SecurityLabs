package security.Security.lab3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Account {
	private int id;
	private int money;
	private String creationTime;
	private LocalDateTime creationMoment;
	private String deletionTime;
	private LocalDateTime deletionMoment;
	
	
	@JsonGetter("id")
	public int getId() {
		return id;
	}
	@JsonSetter("id")
	public void setId(int id) {
		this.id = id;
	}
	@JsonGetter("money")
	public int getMoney() {
		return money;
	}
	@JsonSetter("money")
	public void setMoney(int money) {
		this.money = money;
	}
	public String getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}
	@JsonGetter("deletionTime")
	public String getDeletionTime() {
		return deletionTime;
	}
	@JsonSetter("deletionTime")
	public void setDeletionTime(String deletionTime) {
		deletionTime = deletionTime.replaceFirst("Z", "");
		deletionMoment = LocalDateTime.parse(deletionTime);
		creationMoment = deletionMoment.minusHours(1);
		this.deletionTime = deletionTime;
	}
	
	
	public LocalDateTime getDeletionMoment() {
		return deletionMoment;
	}
	public LocalDateTime getCreationMoment() {
		return creationMoment;
	}
	@Override
	public String toString() {
		return "Account [id=" + id + ", money=" + money + ", creationMoment=" + creationMoment + ", deletionMoment="
				+ deletionMoment + "]";
	}
	
	public void setCreationMoment(LocalDateTime creationMoment) {
		this.creationMoment = creationMoment;
	}
	
	
	
	
	
	
}
