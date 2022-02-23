package web.model;

public class User {
	private String login;
	private String password;
	private String repeatPassword;
	private String name;
	private String gender;
	private String region;
	private String comment;
	
	public User() {
		
	}
	
	public User(String login, String password, String repeatPassword, String name, String gender, String region,
			String comment) {
		super();
		this.login = login;
		this.password = password;
		this.repeatPassword = repeatPassword;
		this.name = name;
		this.gender = gender;
		this.region = region;
		this.comment = comment;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepeatPassword() {
		return repeatPassword;
	}
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", name=" + name + ", gender=" + gender + ", region=" + region + ", comment="
				+ comment + "]";
	}
	
	
	
	
}
