package com.yusufsezer;

import com.yusufsezer.contract.IRepository;
import com.yusufsezer.controller.ContactController;
import com.yusufsezer.repository.ContactRepository;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App {

    public static void main(String[] args) {
        JavaFXContactApp.main(args);
    }

    public static class JavaFXContactApp extends Application {

        @Override
        public void start(Stage stage) throws Exception {
            // loader
            URL fxmlPath = getClass().getResource("/fxml/JFXCFrame.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlPath);

            // repository
            String dbURL = "jdbc:sqlite:contacts.db";
            Connection connection = DriverManager.getConnection(dbURL);
            IRepository repository = new ContactRepository(connection);

            // controller
            ContactController controller = new ContactController(repository);
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

}
