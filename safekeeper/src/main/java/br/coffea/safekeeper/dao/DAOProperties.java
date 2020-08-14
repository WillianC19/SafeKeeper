package br.coffea.safekeeper.dao;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class DAOProperties {

	private static Properties props = new Properties();
	
	static {
		try {
			Path path = Paths.get(DAOProperties.class.getResource("/dao.properties").toURI());
			try (InputStream in = Files.newInputStream(path)) {
				props.load(in);
			}
		} catch (URISyntaxException | IOException e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static String getDAOClassName() {
		return props.getProperty("dao.class");
	}
	
	public static String getDBDAOServerName() {
		return props.getProperty("dao.db.serverName");
	}
	
	public static int getDBDAOPort() {
		return Integer.parseInt(props.getProperty("dao.db.port"));
	}
	
	public static String getDBDAODBName() {
		return props.getProperty("dao.db.dbName");
	}
	
	public static String getDBDAOUserName() {
		return props.getProperty("dao.db.userName");
	}
	
	public static String getDBDAOPassword() {
		return props.getProperty("dao.db.password");
	}
}
