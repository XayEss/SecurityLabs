package web.database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import web.model.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setLogin(rs.getString(2));
		user.setPassword(rs.getString(3));
		user.setName(rs.getString(1));
		return user;
	}

}
