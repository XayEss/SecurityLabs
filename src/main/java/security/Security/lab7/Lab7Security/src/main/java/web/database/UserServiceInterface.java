package web.database;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.dao.EmptyResultDataAccessException;

import web.model.User;

public interface UserServiceInterface {
	public String checkAuthorization(String login, String password);
	public void addUser(String name, String login, String password, String gender, String location, String comment);
	public User getCurrentUser(String currentUser);
	public String encrypt(String text, byte[] salt);
}
