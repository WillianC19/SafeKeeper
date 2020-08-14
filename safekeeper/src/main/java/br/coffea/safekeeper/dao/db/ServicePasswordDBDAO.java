package br.coffea.safekeeper.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.coffea.safekeeper.dao.DAOException;
import br.coffea.safekeeper.dao.ServicePasswordDAO;
import br.coffea.safekeeper.model.ServicePassword;

public class ServicePasswordDBDAO implements ServicePasswordDAO {
	
	@Override
	public List<ServicePassword> load() {
		return filter(null);
	}

	@Override
	public void store(List<ServicePassword> servicePasswords) {
		try (Connection conn = ConnectionFactory.openConnection()) {
			conn.setAutoCommit(false);
			
			String deleteSql = "DELETE FROM SERVICE_PASSWORD";
			try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
				stmt.executeUpdate();
			}
			
			String insertSql = "INSERT INTO SERVICE_PASSWORD (ID, SERVICE, LOGIN, PASSWORD, NOTES) VALUES (?, ?, ?, ?, ?)";
			try(PreparedStatement stmt = conn.prepareStatement(insertSql)) {
				for (ServicePassword servicePassword : servicePasswords) {
					stmt.setInt(1, servicePassword.getId());
					stmt.setString(2, servicePassword.getService());
					stmt.setString(3, servicePassword.getLogin());
					stmt.setString(4, encrypt(servicePassword.getPassword()));
					stmt.setString(5, servicePassword.getNotes());
					stmt.executeUpdate();
				}
			}
			conn.commit();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public List<ServicePassword> filter(String text) {
		try (Connection conn = ConnectionFactory.openConnection()) {
			String sql = "SELECT ID, LOGIN, PASSWORD, SERVICE, NOTES FROM SERVICE_PASSWORD";
			
			if (text != null) {
				sql += " WHERE UPPER(LOGIN) LIKE ? OR UPPER(SERVICE) LIKE ?";
			}
			
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				if (text != null) {
					String param = "%" + text.toUpperCase() + "%";
					stmt.setString(1, param);
					stmt.setString(2, param);
				}
				
				try (ResultSet rs = stmt.executeQuery()) {
					List<ServicePassword> servicePasswords = new ArrayList<>();
					
					while (rs.next()) {
						ServicePassword servicePassword = new ServicePassword();
						servicePassword.setId(rs.getInt("ID"));
						servicePassword.setLogin(rs.getString("LOGIN"));
						servicePassword.setPassword(decrypt(rs.getString("PASSWORD")));
						servicePassword.setService(rs.getString("SERVICE"));
						servicePassword.setNotes(rs.getString("NOTES"));
						servicePasswords.add(servicePassword);
					}
					
					return servicePasswords;
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public int generateId() {
		try (Connection conn = ConnectionFactory.openConnection()) {
			String sql = "SELECT MAX(ID) FROM SERVICE_PASSWORD";
			
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						return rs.getInt(1) + 1;
					}
					return 1;
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
}
