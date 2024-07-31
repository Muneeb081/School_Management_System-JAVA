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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class LoginController implements Initializable {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;

    @FXML
    private Button signinButton;

    private final AdminService adminService;


    private final StageManager stageManager;

    LoginController(AdminService adminService, @Lazy StageManager stageManager )
    {
        this.adminService = adminService;
        this.stageManager=stageManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setupListeners();
    }

    public void setupListeners()
    {
        usernameTextField.textProperty().addListener(
                (observable,oldValue,newValue) ->enableOrDisableLogicButton()
        );
        passwordField.textProperty().addListener(
                (observable,oldValue,newValue) ->enableOrDisableLogicButton()
        );
        loginButton.setOnAction(
                (event ) -> {
                    if (isFormValid()) {
                        onloginButtonPressed();
                    } else {

                        FXUtils.showMessage(Alert.AlertType.WARNING, "Form is not valid");
                    }
                });

        signinButton.setOnAction(
                (event ) -> {
                    onSigninButtonPressed();
                });
    }

    @FXML
    public void onUsernameTextFieldChanged()
    {
        enableOrDisableLogicButton();
    }

    @FXML
    public void onPasswordFieldChanged()
    {
        enableOrDisableLogicButton();
    }


    public void onloginButtonPressed()
    {
        try{
            List<AdminEntity> adminEntities=adminService.findAll();
            for(AdminEntity entity:adminEntities)
            {
                if(usernameTextField.getText().equalsIgnoreCase(entity.getName()))
                {
                    if(passwordField.getText().equals(entity.getPassword()))
                    {
                        stageManager.switchScene(FxmlView.MENU);
                        return;
                    }

                }
                    FXUtils.showMessage(Alert.AlertType.ERROR,"Your credentials are Invalid");
                    usernameTextField.requestFocus();

            }
        }catch(Exception e){
            FXUtils.showMessage(Alert.AlertType.ERROR,"Your credentials are Invalid");
        }
    }

    private boolean isFormValid() {
        return usernameTextField.getText().length()>=3 && passwordField.getText().length()>=3;
    }

    private void enableOrDisableLogicButton()
    {
        loginButton.setDisable(!isFormValid());
    }

    public void onSigninButtonPressed()
    {
        stageManager.switchScene(FxmlView.ADMIN);
    }

}
