package br.coffea.safekeeper.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ServicePassword {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty service = new SimpleStringProperty();
	private StringProperty login = new SimpleStringProperty();
	private StringProperty password = new SimpleStringProperty();
	private StringProperty notes = new SimpleStringProperty();

	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public String getService() {
		return service.get();
	}

	public void setService(String service) {
		this.service.set(service);
	}

	public String getLogin() {
		return login.get();
	}

	public void setLogin(String login) {
		this.login.set(login);
	}

	public String getPassword() {
		return password.get();
	}

	public void setPassword(String password) {
		this.password.set(password);
	}

	public String getNotes() {
		return notes.get();
	}

	public void setNotes(String notes) {
		this.notes.set(notes);
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public StringProperty serviceProperty() {
		return service;
	}

	public StringProperty loginProperty() {
		return login;
	}

	public StringProperty passwordProperty() {
		return password;
	}

	public StringProperty notesProperty() {
		return notes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServicePassword other = (ServicePassword) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PasswordService [ID=" + id + ", SERVICE=" + service + ", LOGIN=" + login + ", PASSWORD=" + password
				+ ", NOTES=" + notes + "]";
	}
}
