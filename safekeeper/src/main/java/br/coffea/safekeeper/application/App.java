package br.coffea.safekeeper.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Coffea SafeKeeper");
		
		FXMLLoader loader = new FXMLLoader(App.class.getResource("/Layout.fxml"));
		Pane root = loader.load();
		Scene scene = new Scene(root, 850, 400);
		primaryStage.setMinWidth(850);
		primaryStage.setMinHeight(400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
