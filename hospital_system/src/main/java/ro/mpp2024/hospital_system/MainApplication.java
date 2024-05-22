package ro.mpp2024.hospital_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ro.mpp2024.hospital_system.controller.LoginController;
import ro.mpp2024.hospital_system.repository.DrugRepository;
import ro.mpp2024.hospital_system.repository.UserRepository;
import ro.mpp2024.hospital_system.repository.db.DrugDBRepository;
import ro.mpp2024.hospital_system.repository.db.UserDBRepository;
import ro.mpp2024.hospital_system.service.Service;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        UserRepository userRepository = new UserDBRepository();
        DrugRepository drugRepository = new DrugDBRepository();
        Service service = new Service(userRepository, drugRepository);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login-view.fxml"));
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        LoginController loginController = fxmlLoader.getController();
        loginController.setService(service);
        stage.show();
    }
}
