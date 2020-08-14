package br.coffea.safekeeper.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.coffea.safekeeper.dao.DAOProperties;

public class ConnectionFactory {
	
	public static Connection openConnection() throws SQLException {
		String url = String.format("jdbc:mysql://%s:%d/%s?useSSL=false&&serverTimezone=UTC", DAOProperties.getDBDAOServerName(), DAOProperties.getDBDAOPort(), DAOProperties.getDBDAODBName());
		return DriverManager.getConnection(url, DAOProperties.getDBDAOUserName(), DAOProperties.getDBDAOPassword());
	}
}
