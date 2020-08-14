package br.coffea.safekeeper.controller;

import java.util.List;

import br.coffea.safekeeper.dao.DAOFactory;
import br.coffea.safekeeper.dao.ServicePasswordDAO;
import br.coffea.safekeeper.model.ServicePassword;
import br.coffea.safekeeper.util.LimitedTextField;
import br.coffea.safekeeper.util.StringUtils;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class Controller {

	@FXML
	private TextField txtSearch;
	@FXML
	private Button btnSearch;
	@FXML
	private Button btnClearSearch;
	@FXML
	private TableView<ServicePassword> table;
	@FXML
	private Button btnNew;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnDelete;
	@FXML
	private LimitedTextField txtService;
	@FXML
	private LimitedTextField txtLogin;
	@FXML
	private LimitedTextField txtPassword;
	@FXML
	private TextArea txtNotes;
	@FXML
	private Button btnConfirm;
	@FXML
	private Button btnCancel;
	
	private BooleanProperty editMode = new SimpleBooleanProperty();
	private BooleanProperty resultsFiltered = new SimpleBooleanProperty();
	private ServicePassword currentServicePassword;
	private ServicePasswordDAO dao;
	
	@FXML
	private void initialize() {
		dao = DAOFactory.getServicePasswordDAO();
		
		loadData(false);
		
		table.getSelectionModel().selectedItemProperty().addListener((event, oldValue, newValue) -> {
			unbindData(oldValue);
			bindData(newValue);
		});
		
		btnNew.disableProperty().bind(editMode);
		btnEdit.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull().or(editMode));
		btnDelete.disableProperty().bind(table.getSelectionModel().selectedItemProperty().isNull().or(editMode));
		btnCancel.disableProperty().bind(editMode.not());
		btnConfirm.disableProperty().bind(editMode.not());
		btnSearch.disableProperty().bind(txtSearch.textProperty().isEmpty());
		btnClearSearch.disableProperty().bind(resultsFiltered.not());
		
		txtService.editableProperty().bind(editMode);
		txtLogin.editableProperty().bind(editMode);
		txtPassword.editableProperty().bind(editMode);
		txtNotes.editableProperty().bind(editMode);
		
		table.disableProperty().bind(editMode);
	}
	
	private void bindData(ServicePassword servicePassword) {
		if (servicePassword != null) {
			txtService.textProperty().bindBidirectional(servicePassword.serviceProperty());
			txtLogin.textProperty().bindBidirectional(servicePassword.loginProperty());
			txtPassword.textProperty().bindBidirectional(servicePassword.passwordProperty());
			txtNotes.textProperty().bindBidirectional(servicePassword.notesProperty());
		}
	}
	
	private void unbindData(ServicePassword servicePassword) {
		if (servicePassword != null) {
			txtService.textProperty().unbindBidirectional(servicePassword.serviceProperty());
			txtLogin.textProperty().unbindBidirectional(servicePassword.loginProperty());
			txtPassword.textProperty().unbindBidirectional(servicePassword.passwordProperty());
			txtNotes.textProperty().unbindBidirectional(servicePassword.notesProperty());
			clearForm();
		}
	}
	
	@FXML
	public void exit() {
		Platform.exit();
	}

	@FXML
	public void search() {
		loadData(true);
		resultsFiltered.set(true);
	}

	@FXML
	public void clearSearch() {
		loadData(false);
		txtSearch.clear();
		resultsFiltered.set(false);
	}

	@FXML
	public void onNew() {
		table.getSelectionModel().clearSelection();
		editMode.set(true);
		currentServicePassword = new ServicePassword();
		bindData(currentServicePassword);
		txtService.requestFocus();
	}

	@FXML
	public void onEdit() {
		editMode.set(true);
		currentServicePassword = table.getSelectionModel().getSelectedItem();
	}

	@FXML
	public void onDelete() {
		table.getItems().remove(table.getSelectionModel().getSelectedItem());
		storeData();
	}

	@FXML
	public void onConfirm() {
		String errorMessage = validateForm();
		if (!errorMessage.isEmpty()) {
			showValidateError(errorMessage);
			return;
		}
		editMode.set(false);
		if (currentServicePassword.getId() == 0) {
			int newId = dao.generateId();
			currentServicePassword.setId(newId);
			table.getItems().add(currentServicePassword);
			unbindData(currentServicePassword);
			clearForm();
			table.getSelectionModel().select(currentServicePassword);
		}
		
		storeData();
	}
	
	@FXML
	public void onCancel() {
		editMode.set(false);
		if(currentServicePassword.getId() == 0) {
			unbindData(currentServicePassword);
			clearForm();
		}
	}

	private String validateForm() {
		StringBuilder errorMessage = new StringBuilder();
		if (StringUtils.isEmpty(currentServicePassword.getService())) {
			errorMessage.append("Fill the Site/Service field").append(StringUtils.newLine());
		}
		if (StringUtils.isEmpty(currentServicePassword.getLogin())) {
			errorMessage.append("Fill the Login field").append(StringUtils.newLine());
		}
		if (StringUtils.isEmpty(currentServicePassword.getPassword())) {
			errorMessage.append("Fill the Password field").append(StringUtils.newLine());
		}
		
		return errorMessage.toString();
	}
	
	private void showValidateError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Validation Error");
		alert.setHeaderText("Incorrect information");
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	private void clearForm() {
		txtService.clear();
		txtLogin.clear();
		txtPassword.clear();
		txtNotes.clear();
	}
	
	private void loadData(boolean filter) {
		List<ServicePassword> items;
		if (!filter) {
			items = dao.load();
		} else {
			items = dao.filter(txtSearch.getText());
		}
		ObservableList<ServicePassword> list = FXCollections.observableArrayList(items);
		table.setItems(list);
	}
	
	private void storeData() {
		dao.store(table.getItems());
	}
	
}
