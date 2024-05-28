package ro.mpp2024.hospital_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.mpp2024.hospital_system.model.User;
import ro.mpp2024.hospital_system.service.Service;

public class LoginController {
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    private Service service;
    public void setService(Service service) {
        this.service = service;
    }

    public void handleLogin(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        try {
            if (service.login(username, password)) {
                User user = service.getUserByUsername(username);
                System.out.println("Login successful");
                goToViewBasedOnRole(user);
            } else {
                System.out.println("Login failed");
            }
        } catch (Exception e) {
            showError("Login failed");
        }
    }

    private void goToViewBasedOnRole(User user) {
        switch (user.getUserType()) {
            case ADMINISTRATOR:
                handleGoToAdministratorView();
                break;
            case DOCTOR:
                handleGoToDoctorView();
                break;
            case PHARMACY:
                handleGoToPharmacyView();
                break;
        }

    }

    private void handleGoToPharmacyView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ro/mpp2024/hospital_system/pharmacy-view.fxml"));
            fxmlLoader.load();
            Stage stage = new Stage();
            Parent root = fxmlLoader.getRoot();
            stage.setScene(new javafx.scene.Scene(root));
            PharmacyController pharmacyController = fxmlLoader.getController();
            pharmacyController.setService(service, service.getUserByUsername(usernameTextField.getText()).getId());
            stage.show();
        } catch (Exception e) {
            showError("Could not open pharmacy view");
        }
    }

    private void handleGoToDoctorView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ro/mpp2024/hospital_system/doctor-view.fxml"));
            fxmlLoader.load();
            Stage stage = new Stage();
            Parent root = fxmlLoader.getRoot();
            stage.setScene(new javafx.scene.Scene(root));
            DoctorController doctorController = fxmlLoader.getController();
            doctorController.setService(service, service.getUserByUsername(usernameTextField.getText()).getId());
            stage.show();
        } catch (Exception e) {
            showError("Could not open administrator view");
        }
    }

    private void handleGoToAdministratorView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ro/mpp2024/hospital_system/administrator-view.fxml"));
            fxmlLoader.load();
            Stage stage = new Stage();
            Parent root = fxmlLoader.getRoot();
            stage.setScene(new javafx.scene.Scene(root));
            AdministratorController administratorController = fxmlLoader.getController();
            administratorController.setService(service, service.getUserByUsername(usernameTextField.getText()).getId());
            stage.show();
        } catch (Exception e) {
            showError("Could not open administrator view");
        }
    }

    public void handleGoToSignUp(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ro/mpp2024/hospital_system/sing-up-view.fxml"));
            fxmlLoader.load();
            Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            Parent root = fxmlLoader.getRoot();
            stage.getScene().setRoot(root);
            SignUpController signUpController = fxmlLoader.getController();
            signUpController.setService(service);

            stage.show();
        } catch (Exception e) {
            showError("Could not open sign up view");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.show();
    }
}
