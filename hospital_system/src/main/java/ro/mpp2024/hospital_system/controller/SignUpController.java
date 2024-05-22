package ro.mpp2024.hospital_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.mpp2024.hospital_system.model.UserType;
import ro.mpp2024.hospital_system.service.Service;

import java.io.IOException;

public class SignUpController {
    public TextField firstNameTextField;
    public TextField lastNameTextField;
    public TextField cnpTextField;
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public PasswordField confirmPasswordTextField;
    public ComboBox<String> accountTypeComboBox;
    private Service service;

    public void setService(Service service) {
        this.service = service;
    }

    public void initialize() {
        accountTypeComboBox.getItems().addAll("ADMINISTRATOR", "DOCTOR", "PHARMACY");
    }

    public void handleSignUp(ActionEvent actionEvent) {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String cnp = cnpTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();
        UserType userType = UserType.valueOf(accountTypeComboBox.getValue());
        try {
            if (password.equals(confirmPassword)) {
                service.addUser(firstName, lastName, cnp, username, password, userType);
                System.out.println("Sign up successful");
            } else {
                System.out.println("Passwords do not match");
            }
        } catch (Exception e) {
            showError("Sign up failed");
        }
    }

    public void handleGoToLoginView(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ro/mpp2024/hospital_system/login-view.fxml"));
            fxmlLoader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent root = fxmlLoader.getRoot();
            stage.getScene().setRoot(root);
            LoginController loginController = fxmlLoader.getController();
            loginController.setService(service);

            stage.show();
        } catch (IOException e) {
            showError("Error loading login view");

        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(message);
    }
}
