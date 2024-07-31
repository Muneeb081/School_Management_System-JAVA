package com.alabtaal.school.controller;


import com.alabtaal.school.config.StageManager;
import com.alabtaal.school.enums.FxmlView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;


import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class MenuController implements Initializable {

    private final StageManager stageManager;
    @FXML
    private Button studentsModuleButton;

    @FXML
    private Button feeSubmissionModuleButton;

    @FXML
    private Button teachersModuleButton;

    @FXML
    private Button studentsReportCardButton;

    @FXML
    private Button masterModuleButton;

  //  @FXML
    //private Button closeButton;

    public MenuController(@Lazy StageManager stageManager) {
        this.stageManager = stageManager;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupListeners();
    }

    public void setupListeners() {
        studentsModuleButton.setOnAction(event ->  {
            stageManager.switchScene(FxmlView.STUDENTS_MODULE);
        });
        feeSubmissionModuleButton.setOnAction(event ->{
            stageManager.switchScene(FxmlView.FEE_SUBMISSION_MODULE);
        });
        teachersModuleButton.setOnAction(event -> {
            stageManager.switchScene(FxmlView.TEACHERS_MODULE);
        });
        studentsReportCardButton.setOnAction(event ->  {
            stageManager.switchScene(FxmlView.STUDENTS_REPORT_CARD);
        });
        masterModuleButton.setOnAction(event ->  {
            stageManager.switchScene(FxmlView.Master_Table);
        });

       // closeButton.setOnAction(
                //(event) -> {
                   // try {
                       // onCloseButtonPressed();
                   // } catch (IOException e) {
                        //throw new RuntimeException(e);
                    //}
                //}
       // );
    }


    public void onCloseButtonPressed() throws IOException {
        Platform.exit();
    }
}
