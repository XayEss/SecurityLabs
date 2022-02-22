package web.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementSetter;

public class PreparedStatementSetterImpl implements PreparedStatementSetter {

	private int id;
	
	public PreparedStatementSetterImpl(int id) {
		super();
		this.id = id;
	}

	@Override
	public void setValues(PreparedStatement ps) throws SQLException {
		ps.setInt(1, id);
	}

}
