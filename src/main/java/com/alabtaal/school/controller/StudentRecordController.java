package com.alabtaal.school.controller;

import com.alabtaal.school.config.StageManager;
import com.alabtaal.school.entity.FeeCounterEntity;
import com.alabtaal.school.entity.ResultEntity;
import com.alabtaal.school.entity.StudentRecordEntity;
import com.alabtaal.school.enums.FxmlView;
import com.alabtaal.school.service.FeeCounterService;
import com.alabtaal.school.service.ResultService;
import com.alabtaal.school.service.StudentRecordService;
import com.alabtaal.school.util.FXUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;
@Controller


public class StudentRecordController implements Initializable {

    private final StageManager stageManager;
    private final StudentRecordService studentRecordService;
    private final FeeCounterService feeCounterService;
    private final ResultService resultService;

    @FXML
    private Label idLabel;

    @FXML
    TextField idTextField;

    @FXML
    TextField nameTextField;

    @FXML
    TextField fatherNameTextField;
    @FXML
    TextField classTextField;

    @FXML
    TextField sectionTextField;

    @FXML
    TextField rollNoTextField;


    @FXML
    TableView<StudentRecordEntity> studentRecordTableView;

    @FXML
    TableColumn<StudentRecordEntity,String> idColumn;

    @FXML
    TableColumn<StudentRecordEntity, String> nameColumn;

    @FXML
    TableColumn<StudentRecordEntity,String> fatherNameColumn;

    @FXML
    TableColumn<StudentRecordEntity,String> classColumn;
    @FXML
    TableColumn<StudentRecordEntity,String> sectionColumn;

    @FXML
    TableColumn<StudentRecordEntity,String> rollNoColumn;



    @FXML
    Button backButton;

    @FXML
    Button addButton;

    @FXML
    Button searchButton;

    @FXML
    Button deleteButton;

    @FXML
    Button updateButton;

    public StudentRecordController(StudentRecordService studentRecordService, @Lazy StageManager stageManager, ResultService resultService, FeeCounterService feeCounterService, ResultService resultService1) {
        this.stageManager = stageManager;
        this.studentRecordService = studentRecordService;
        this.feeCounterService = feeCounterService;
        this.resultService = resultService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idLabel.setVisible(false);
        idTextField.setVisible(false);
        setupListeners();
        setupStudentRecordTableView();
    }

    private void setupListeners()
    {
        idTextField.textProperty().addListener(
                (observable, oldValue, newValue) -> onIdTextFieldChanged(oldValue, newValue)
        );
        nameTextField.textProperty().addListener(
                (observable, oldValue, newValue) ->{
                    if (!newValue.matches("^[a-zA-Z]*$")) {
                        nameTextField.setText(oldValue);
                    }
                }
        );
        fatherNameTextField.textProperty().addListener(
                (observable, oldValue, newValue) ->{
                    if (!newValue.matches("^[a-zA-Z]*$")) {
                        fatherNameTextField.setText(oldValue);
                    }
                }
        );

        rollNoTextField.textProperty().addListener(
                (observable, oldValue, newValue) -> onRollNoTextFieldChanged(oldValue, newValue)
        );

        studentRecordTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldSelection,newSelection)->{
                    if(newSelection!=null)
                    {
                        idTextField.setText(String.valueOf(newSelection.getStudentId()));
                        nameTextField.setText(newSelection.getName());
                        fatherNameTextField.setText(newSelection.getFatherName());
                        classTextField.setText(newSelection.getClassName());
                        sectionTextField.setText(newSelection.getSection());
                        rollNoTextField.setText(String.valueOf(newSelection.getRollNumber()));
                        addButton.setVisible(false);
                    }
                }
        );
        backButton.setOnAction(
                (event)->onBackButtonPressed()
        );
        addButton.setOnAction(
                (event)->onAddButtonPressed()
        );
        searchButton.setOnAction(
                (event)->onSearchButtonPressed()
        );
        deleteButton.setOnAction(
                (event)->  onDeleteButtonPressed()
        );
        updateButton.setOnAction(
                (event)->  onUpdateButtonPressed()
        );
    }

    public void onIdTextFieldChanged(String oldValue,String newValue){
        try {
            if(newValue.isBlank())
            {
                idTextField.setText(newValue);
                return;
            }
            Long.parseLong((newValue));


        }catch (Exception e){

            idTextField.setText(oldValue);
        }
    }



    public void onRollNoTextFieldChanged(String oldValue,String newValue){
        try {
            if(newValue.isBlank())
            {
                rollNoTextField.setText(newValue);
                return;
            }
            Long.parseLong((newValue));


        }catch (Exception e){

            rollNoTextField.setText(oldValue);
        }
    }



    private void setupStudentRecordTableView() {
        idColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getStudentId()))
        );
        nameColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(cellData.getValue().getName())
        );
        fatherNameColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(cellData.getValue().getFatherName())
        );
        classColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getClassName()))
        );
        sectionColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(cellData.getValue().getSection())
        );
        rollNoColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getRollNumber()))
        );

        studentRecordTableView.setItems(FXCollections.observableList(studentRecordService.findAll()));
    }

    private void clear()
    {
        idTextField.setText("");
        nameTextField.setText("");
        fatherNameTextField.setText("");
        classTextField.setText("");
        sectionTextField.setText("");
        rollNoTextField.setText("");
    }

    private void onBackButtonPressed()
    {
        try{
            stageManager.switchScene(FxmlView.MENU);
        }catch(Exception e)
        {
            FXUtils.showMessage(Alert.AlertType.ERROR,"Issue in switch scene");
        }
    }

    private void onAddButtonPressed() {
        try {
            StudentRecordEntity studentRecordEntity = new StudentRecordEntity();

            String name = nameTextField.getText().trim();
            String fatherName = fatherNameTextField.getText().trim();
            String className = classTextField.getText().trim();
            String section = sectionTextField.getText().trim();
            String rollNumberText = rollNoTextField.getText().trim();

            if (!name.isEmpty()) {
                studentRecordEntity.setName(name);
            } else {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Name cannot be empty");
                return;
            }

            if (!fatherName.isEmpty()) {
                studentRecordEntity.setFatherName(fatherName);
            } else {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Father name cannot be empty");
                return;
            }

            if (!className.isEmpty()) {
                studentRecordEntity.setClassName(className);
            } else {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Class cannot be empty");
                return;
            }

            if (!section.isEmpty()) {
                studentRecordEntity.setSection(section);
            } else {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Section cannot be empty");
                return;
            }

            if (!rollNumberText.isEmpty()) {
                try {
                    Long rollNumber = Long.parseLong(rollNumberText);
                    studentRecordEntity.setRollNumber(rollNumber);
                } catch (NumberFormatException e) {
                    FXUtils.showMessage(Alert.AlertType.ERROR, "Invalid roll number format");
                    return;
                }
            } else {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Roll number cannot be empty");
                return;
            }

            studentRecordService.save(studentRecordEntity);

            FXUtils.showMessage(Alert.AlertType.INFORMATION, "Student Record Successfully Saved");
            studentRecordTableView.setItems(FXCollections.observableList(studentRecordService.findAll()));
            idTextField.setText(String.valueOf(studentRecordEntity.getStudentId()));
            idLabel.setVisible(true);
            idTextField.setVisible(true);

        } catch (Exception e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Student Record did not save successfully: " + e.getMessage());
        }
        clear();

    }


    private void onSearchButtonPressed() {
        try {
            String searchIdText = idTextField.getText().trim();

            if (searchIdText.isEmpty()) {
                FXUtils.showMessage(Alert.AlertType.ERROR, "ID field is empty");
                return;
            }

            Long searchId = Long.parseLong(searchIdText);
            final StudentRecordEntity studentRecordEntity = studentRecordService.findById(searchId);

            if (studentRecordEntity != null) {
                FXUtils.showMessage(Alert.AlertType.INFORMATION, "Student Record found for ID: " + studentRecordEntity.getStudentId());
            } else {
                FXUtils.showMessage(Alert.AlertType.INFORMATION, "Required record not found for ID: " + searchId);
            }
        } catch (NumberFormatException e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Invalid ID format");
        } catch (Exception e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, e.getMessage());
        }
        clear();
        addButton.setVisible(true);
    }



    private void onDeleteButtonPressed() {
        try {
            String idText = idTextField.getText().trim();

            if (idText.isEmpty()) {
                FXUtils.showMessage(Alert.AlertType.INFORMATION, "ID must be entered");
                return;
            }

            Long studentId = Long.parseLong(idText);
            StudentRecordEntity studentRecord = studentRecordService.findById(studentId);


            for (FeeCounterEntity feeRecord : studentRecord.getFee()) {
                feeCounterService.delete(feeRecord);
                }

            for (ResultEntity resultEntity : studentRecord.getResults()) {
                resultService.delete(resultEntity);
            }
            studentRecordService.delete(studentId);

            FXUtils.showMessage(Alert.AlertType.INFORMATION, "Student Record successfully deleted");
            studentRecordTableView.setItems(FXCollections.observableList(studentRecordService.findAll()));
            clear();
        } catch (NumberFormatException e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Invalid ID format");
        } catch (Exception e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Error deleting student record: " + e.getMessage());
        }
        addButton.setVisible(true);
    }


    private void onUpdateButtonPressed() {
        try {
            Long studentId = Long.parseLong(idTextField.getText());
            StudentRecordEntity existingEntity = studentRecordService.findById(studentId);

            if (existingEntity != null) {
                existingEntity.setName(nameTextField.getText());
                existingEntity.setFatherName(fatherNameTextField.getText());
                existingEntity.setClassName(classTextField.getText());
                existingEntity.setSection(sectionTextField.getText());

                String rollNumberText = rollNoTextField.getText().trim();
                if (!rollNumberText.isEmpty()) {
                    try {
                        Long rollNumber = Long.parseLong(rollNumberText);
                        existingEntity.setRollNumber(rollNumber);
                    } catch (NumberFormatException e) {
                        FXUtils.showMessage(Alert.AlertType.ERROR, "Invalid roll number format");
                        return;
                    }
                } else {
                    FXUtils.showMessage(Alert.AlertType.ERROR, "Roll number cannot be empty");
                    return;
                }

                studentRecordService.save(existingEntity);

                FXUtils.showMessage(Alert.AlertType.INFORMATION, "Record updated successfully");
                studentRecordTableView.setItems(FXCollections.observableList(studentRecordService.findAll()));
            } else {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Record not found for ID: " + studentId);
            }
        } catch (NumberFormatException e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Invalid ID format");
        } catch (Exception e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Error occurred while updating record: " + e.getMessage());
        }
        clear();
        addButton.setVisible(true);
    }
}