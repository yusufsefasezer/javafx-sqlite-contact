package com.yusufsezer;

import com.yusufsezer.contracts.IRepository;
import com.yusufsezer.controller.JFXCController;
import com.yusufsezer.repository.SQLiteRepository;
import java.net.URL;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JFXCMain extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		String dbURL = "jdbc:sqlite:contacts.db";

		URL fxmlPath = getClass().getResource("/fxml/JFXCFrame.fxml");

		// loader
		FXMLLoader loader = new FXMLLoader(fxmlPath);

		// repository
		final IRepository repository = new SQLiteRepository(dbURL);

		// controller
		JFXCController controller = new JFXCController(repository);

		loader.setController(controller);

		Parent root = loader.load();
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
