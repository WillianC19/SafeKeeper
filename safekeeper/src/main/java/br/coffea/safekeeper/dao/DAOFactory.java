package br.coffea.safekeeper.dao;

public class DAOFactory {

	public static ServicePasswordDAO getServicePasswordDAO() {
		try {
			String daoClass = DAOProperties.getDAOClassName();
			return (ServicePasswordDAO) Class.forName(daoClass).getDeclaredConstructor().newInstance();
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
