package ro.mpp2024.hospital_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mpp2024.hospital_system.model.User;
import ro.mpp2024.hospital_system.model.UserType;
import ro.mpp2024.hospital_system.repository.UserRepository;
import ro.mpp2024.hospital_system.repository.db.UserDBRepository;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        UserRepository userRepository = new UserDBRepository();
        System.out.println(userRepository.findByUsername("admin"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}