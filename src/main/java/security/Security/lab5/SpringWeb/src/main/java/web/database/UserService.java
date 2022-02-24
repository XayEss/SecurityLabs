package web.database;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import org.bouncycastle.util.encoders.Hex;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;

import web.database.mapper.UserRowMapper;
import web.model.User;

@Service
public class UserService implements UserServiceInterface {
	private static final String SELECT_USER = "SELECT name FROM users WHERE login=? AND password=?";
	private static final String SELECT_ALL = "SELECT name, gender, location, comment, iv FROM users WHERE login=?";
	private static final String SALT_COLUMN = "salt";
	private static final String SELECT_SALT = String.format("SELECT %s FROM users WHERE login = ?", SALT_COLUMN);
	private static final String SELECT_SALT_IV = String.format("SELECT %s, %s FROM users WHERE login = ?", SALT_COLUMN, "iv");
	private static final String INSERT_USER = "INSERT INTO users (name, login, password, salt, gender, location, comment, iv) VALUES(?,?,?,?,?,?,?,?)";
	private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

	private final JdbcTemplate jdbcTemplate;
	private final EncryptionHandler aesWorker;

	public UserService(JdbcTemplate jdbcTemplate, EncryptionHandler aesWorker) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.aesWorker = aesWorker;
	}

	public String checkAuthorization(String login, String password) {
		String user;
		LOGGER.info("Starting query");
		byte[] initializationVector = null;
		try {
			String saltAndVector = jdbcTemplate.query(SELECT_SALT_IV, new PreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, login);
				}
			}, new ResultSetExtractor<String>() {

				@Override
				public String extractData(ResultSet rs) throws SQLException, DataAccessException {
					rs.next();
					return rs.getString(SALT_COLUMN) + " " + rs.getString("iv");

				}

			});
			String[] splitSaltIV = saltAndVector.split(" ");
			String salt = splitSaltIV[0];
			initializationVector = Base64.getDecoder().decode(splitSaltIV[1]);
			user = jdbcTemplate.queryForObject(SELECT_USER, new Object[] { login, encrypt(password, Hex.decode(salt)) },
					String.class);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		LOGGER.info("Query success");
		String name = aesWorker.decrypt(user, initializationVector);
		System.out.println(name);
		return name;
	}

	public void addUser(String name, String login, String password, String gender, String location, String comment) {
		LOGGER.info("Starting query");
		byte[] salt = generateSalt();
		String saltHash = Hex.toHexString(salt); 
		byte[] initVector = aesWorker.generateVector();
		jdbcTemplate.update(INSERT_USER, aesWorker.encrypt(name, initVector), login,
				encrypt(password, salt), saltHash, aesWorker.encrypt(gender, initVector),
				aesWorker.encrypt(location, initVector), aesWorker.encrypt(comment, initVector), Base64.getEncoder().encodeToString(initVector));
		LOGGER.info("Query success");
	}
	
	public User getCurrentUser(String userName) {
		User user = jdbcTemplate.query(SELECT_ALL, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, userName);
			}
		}, new ResultSetExtractor<User>() {

			@Override
			public User extractData(ResultSet rs) throws SQLException, DataAccessException {
				User user = new User();
				rs.next();
				byte[] iv = Base64.getDecoder().decode(rs.getString("iv"));
				user.setName(aesWorker.decrypt(rs.getString("name"), iv));
				user.setRegion(aesWorker.decrypt(rs.getString("location"), iv));
				user.setGender(aesWorker.decrypt(rs.getString("gender"), iv));
				user.setComment(aesWorker.decrypt(rs.getString("comment"), iv));
				return user;
			}});
		user.setLogin(userName);
		return user;
	}

	@Override
	public String encrypt(String text, byte[] salt) {
		MessageDigest md = null;
		String result = null;
		if (text != null) {
			try {
				md = MessageDigest.getInstance("SHA3-256");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			if (md != null) {
				md.update(salt);
				md.update(text.getBytes(StandardCharsets.UTF_8));
				result = String.format("%064x", new BigInteger(1, md.digest()));
			}
		}
		return result;
	}

	private byte[] generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[32];
		random.nextBytes(salt);
		return salt;
	}

}
