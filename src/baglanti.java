import java.sql.*;

public class baglanti {

	Statement st;
	Connection conn;

	public baglanti() {
		connect();
	}

	public void connect() {
		String myDriver = "com.mysql.jdbc.Driver";
		String db = "jdbc:mysql://localhost/fis_bilgileri";
		try {
			Class.forName(myDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			this.conn = DriverManager.getConnection(db, "root", "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			this.st = this.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void add(String sql) {
		try {
			this.st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet show(String sql) {
		Statement st = null;
		try {
			st = this.conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}