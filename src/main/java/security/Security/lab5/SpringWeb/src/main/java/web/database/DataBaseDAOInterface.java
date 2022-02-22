package web.database;

public interface DataBaseDAOInterface {

	 public String checkLogin(String login, String password);
	 public void addUser(String name, String login, String password, String gender, String location, String comment);
	 public String encrypt(String text);
}
