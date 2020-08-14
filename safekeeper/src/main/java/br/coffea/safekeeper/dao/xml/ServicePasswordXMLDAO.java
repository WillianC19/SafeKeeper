package br.coffea.safekeeper.dao.xml;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import br.coffea.safekeeper.dao.DAOException;
import br.coffea.safekeeper.dao.ServicePasswordDAO;
import br.coffea.safekeeper.model.ServicePassword;

public class ServicePasswordXMLDAO implements ServicePasswordDAO {

	private static final Path STORAGE_FILE = Paths.get(System.getProperty("user.home"), "password.xml");

	@Override
	public List<ServicePassword> load() {
		List<ServicePassword> servicePasswords = new ArrayList<>();
		if (Files.exists(STORAGE_FILE)) {
			return servicePasswords;
		}
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbFactory.newDocumentBuilder();
			Document doc = db.parse(Files.newInputStream(STORAGE_FILE));
			NodeList servicePasswordTags = doc.getElementsByTagName("ServicePassword");

			for (int i = 0; i < servicePasswordTags.getLength(); i++) {
				Element servicePasswordTag = (Element) servicePasswordTags.item(i);
				ServicePassword servicePassword = new ServicePassword();

				servicePassword.setId(Integer.parseInt(servicePasswordTag.getAttribute("id")));
				servicePassword.setService(servicePasswordTag.getElementsByTagName("Service").item(0).getTextContent());
				servicePassword.setLogin(servicePasswordTag.getElementsByTagName("Login").item(0).getTextContent());
				servicePassword
						.setPassword(servicePasswordTag.getElementsByTagName("Password").item(0).getTextContent());
				servicePassword.setNotes(servicePasswordTag.getElementsByTagName("Notes").item(0).getTextContent());

				servicePasswords.add(servicePassword);
			}
			return servicePasswords;
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void store(List<ServicePassword> servicePasswords) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbFactory.newDocumentBuilder();
			Document doc = db.newDocument();

			Element rootTag = doc.createElement("SafeKeeper");
			doc.appendChild(rootTag);
			servicePasswords.forEach(servicePassword -> {
				Element servicePasswordTag = doc.createElement("ServicePassword");
				servicePasswordTag.setAttribute("id", String.valueOf(servicePassword.getId()));

				Element serviceTag = doc.createElement("Service");
				serviceTag.appendChild(doc.createTextNode(servicePassword.getService()));
				servicePasswordTag.appendChild(serviceTag);

				Element loginTag = doc.createElement("Login");
				loginTag.appendChild(doc.createTextNode(servicePassword.getLogin()));
				servicePasswordTag.appendChild(loginTag);

				Element passwordTag = doc.createElement("Password");
				passwordTag.appendChild(doc.createTextNode(servicePassword.getPassword()));
				servicePasswordTag.appendChild(passwordTag);

				Element notesTag = doc.createElement("Notes");
				notesTag.appendChild(
						doc.createTextNode(servicePassword.getNotes() == null ? "" : servicePassword.getNotes()));
				servicePasswordTag.appendChild(notesTag);

				rootTag.appendChild(servicePasswordTag);
			});
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}

	@Override
	public List<ServicePassword> filter(String text) {
		List<ServicePassword> items = load();
		String txtUpper = text.toUpperCase();
		return items.stream().filter(
				s -> s.getLogin().toUpperCase().contains(txtUpper) || s.getService().toUpperCase().contains(txtUpper))
				.collect(Collectors.toList());
	}

	@Override
	public int generateId() {
		return load().stream()
				.mapToInt(s -> s.getId() + 1)
				.max()
				.orElse(1);
	}

}
