package br.coffea.safekeeper.dao;

import java.util.Base64;
import java.util.List;

import br.coffea.safekeeper.model.ServicePassword;
import br.coffea.safekeeper.util.CryptoException;
import br.coffea.safekeeper.util.CryptoUtils;

public interface ServicePasswordDAO {

	final byte[] SECRET_KEY = "LDJGOGDLKJFSDYFK".getBytes();
	
	List<ServicePassword> load();
	
	void store(List<ServicePassword> servicePasswords);
	
	List<ServicePassword> filter(String text);
	
	public int generateId();
	
	default String encrypt(String password) {
		try {
			byte[] encBytes = CryptoUtils.encryptAES(SECRET_KEY, password.getBytes());
			byte[] base64Bytes = Base64.getEncoder().encode(encBytes);
			
			return new String(base64Bytes);
			
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}
	
	default String decrypt(String password) {
		try {
			byte[] base64Bytes = password.getBytes();
			byte[] encBytes = Base64.getDecoder().decode(base64Bytes);
			byte[] decBytes = CryptoUtils.decryptAES(SECRET_KEY, encBytes);
			return new String(decBytes);
		} catch (Exception e) {
			throw new CryptoException(e);
		}
	}
	
}
