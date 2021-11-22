package de.luke.naruto.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

	private Connection _con;

	private String _host;
	private int _port;
	private String _database;
	private String _user;
	private String _password;

	public MySQL(String host, int port, String database, String user, String password) {

		this._host = host;
		this._port = port;
		this._database = database;
		this._user = user;
		this._password = password;

		connect();

	}

	public void connect() {

		try {
			_con = DriverManager.getConnection("jdbc:mysql://" + _host + ":" + _port + "/" + _database + "?autoReconnect=true", _user, _password);

			System.out.println("MySQL connected");

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("MySQL disconnected");
		}

	}

	

	public void disconnect() {

		try {
			if (this.hasConnection()) {
				this._con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean hasConnection() {
		if (this._con != null) {
			return true;
		}
		return false;
	}

	public void UpdateConnection() throws SQLException {

		if (_con.isValid(0) == false) {

			disconnect();
			connect();
			System.out.println("NewConnection");

		}
	}

	public Connection getConnection() throws SQLException {
		if (_con.isValid(0) == false) {

			disconnect();
			connect();

		}
		return this._con;
	}

	public Connection getSafeConnection() {
		return this._con;
	}
}
