package com.alabtaal.school.controller;

import com.alabtaal.school.config.StageManager;
import com.alabtaal.school.entity.AdminEntity;
import com.alabtaal.school.enums.FxmlView;
import com.alabtaal.school.service.AdminService;
import com.alabtaal.school.util.FXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class AdminController implements Initializable {

    private static final String REGISTRATION_NUMBER_FILE_PATH = "src/main/resources/registrationNumbers.txt";

    private final StageManager stageManager;
    private final AdminService adminService;
    @FXML
    TextField registrationNoTextField;

    @FXML
    TextField nameTextField;

    @FXML
    TextField passwordField;

    @FXML
    Button signUpButton;

    public AdminController(AdminService adminService, @Lazy StageManager stageManager) {
        this.stageManager = stageManager;
        this.adminService = adminService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupListeners();

    }

    public void setupListeners() {

        signUpButton.setOnAction(
                (event) -> {
                    onSignUpButtonPressed() ;
                }
        );
    }

    private void clear()
    {
        registrationNoTextField.setText("");
        nameTextField.setText("");
        passwordField.setText("");

    }

    private void onSignUpButtonPressed() {
        try {
            if (!isRegistrationNumberValid()) {
                FXUtils.showMessage(Alert.AlertType.WARNING, "Error,Invalid registration number.");
                return;
            }

            AdminEntity adminEntity = new AdminEntity();

            if (nameTextField.getText().isEmpty()) {
                FXUtils.showMessage(Alert.AlertType.WARNING, "Error name");
                return;
            }

            if (passwordField.getText().isEmpty()) {
                FXUtils.showMessage(Alert.AlertType.WARNING, "Error  password.");
                return;
            }

            adminEntity.setName(nameTextField.getText());
            adminEntity.setPassword(passwordField.getText());
            adminService.save(adminEntity);
            FXUtils.showMessage(Alert.AlertType.INFORMATION, "Success");
            stageManager.switchScene(FxmlView.LOGIN);
            clear();
        } catch (Exception e) {

            FXUtils.showMessage(Alert.AlertType.ERROR, "Error occurred while Sign UP " + e.getMessage());
            clear();
        }
    }


    private boolean isRegistrationNumberValid() {
        try {

            BufferedReader reader = new BufferedReader(new FileReader(REGISTRATION_NUMBER_FILE_PATH));
            String line;
            String enteredId = registrationNoTextField.getText().trim();
            while ((line = reader.readLine()) != null) {
                if (enteredId.equals(line.trim())) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}


