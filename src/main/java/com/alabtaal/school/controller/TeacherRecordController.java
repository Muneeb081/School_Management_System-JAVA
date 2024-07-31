package com.alabtaal.school.controller;

import com.alabtaal.school.config.StageManager;
import com.alabtaal.school.entity.FeeCounterEntity;
import com.alabtaal.school.entity.TeacherRecordEntity;
import com.alabtaal.school.enums.FxmlView;
import com.alabtaal.school.service.TeacherRecordService;
import com.alabtaal.school.util.FXUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Controller
public class TeacherRecordController implements Initializable {


    private final TeacherRecordService teacherRecordService;
    private final StageManager stageManager;

    @FXML
    private Label idLabel;
    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField designationTextField;


    @FXML
    private ChoiceBox<String> subjectAssignedChoiceBox;
    @FXML
    private ChoiceBox<String> departmentChoiceBox;
    @FXML
    private ChoiceBox<String> specializationChoiceBox;


    @FXML
    private TableView<TeacherRecordEntity> teacherTable;
    @FXML
    private TableColumn<TeacherRecordEntity, String> idColumn;

    @FXML
    private TableColumn<TeacherRecordEntity, String> nameColumn;

    @FXML
    private TableColumn<TeacherRecordEntity, String> subjectAssignedColumn;

    @FXML
    private TableColumn<TeacherRecordEntity, String> departmentColumn;

    @FXML
    private TableColumn<TeacherRecordEntity, String> designationColumn;

    @FXML
    private TableColumn<TeacherRecordEntity, String> specializationColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button deleteButton;
    @FXML
    private Button backButton;
    @FXML
    private Button updateButton;

    public TeacherRecordController(TeacherRecordService teacherRecordService, @Lazy StageManager stageManager) {
        this.teacherRecordService = teacherRecordService;
        this.stageManager = stageManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idLabel.setVisible(false);
        idTextField.setVisible(false);
        subjectAssignedChoiceBox();
        departmentChoiceBox();
        specializationChoiceBox();
        setupListeners();
        setupTeacherTable();


    }

    private void subjectAssignedChoiceBox(){
        subjectAssignedChoiceBox.getItems().clear();
        subjectAssignedChoiceBox.setItems(FXCollections.observableList(getSubjectAssigned()));
    }

    private void departmentChoiceBox(){
        departmentChoiceBox.getItems().clear();
        departmentChoiceBox.setItems(FXCollections.observableList(getDepartment()));
    }

    private void specializationChoiceBox(){
        specializationChoiceBox.getItems().clear();
        specializationChoiceBox.setItems(FXCollections.observableList(getSpecialization()));
    }

    private void setupListeners() {
        idTextField.textProperty().addListener(
                (observable, oldValue, newValue)-> onIdTextFieldChanged(oldValue,newValue)
        );
        nameTextField.textProperty().addListener(
                (observable, oldValue, newValue) ->{
                    if (!newValue.matches("^[a-zA-Z]*$")) {
                        nameTextField.setText(oldValue);
                    }
                }
        );
        designationTextField.textProperty().addListener(
                (observable, oldValue, newValue) ->{
                    if (!newValue.matches("^[a-zA-Z]*$")) {
                        designationTextField.setText(oldValue);
                    }
                }
        );

        addButton.setOnAction(
                event -> onAddButtonPressed()
        );
        searchButton.setOnAction(
                event -> onSearchButtonPressed()
        );
        deleteButton.setOnAction(
                event -> onDeleteButtonPressed()
        );
        backButton.setOnAction(
                event -> onBackButtonPressed()
        );
        updateButton.setOnAction(
                event -> onUpdateButtonPressed()
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

    private void setupTeacherTable() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTeacherId())));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        subjectAssignedColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubject()));
        departmentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartment()));
        designationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDesignation()));
        specializationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQualification()));

        refreshTeacherTable();
        teacherTable.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldSelection,newSelection) -> {
                    if (newSelection != null){
                        idTextField.setText(String.valueOf(newSelection.getTeacherId()));
                        nameTextField.setText(newSelection.getName());
                        designationTextField.setText(newSelection.getDesignation());

                        if (newSelection.getSubject() !=null){
                            subjectAssignedChoiceBox.setValue(newSelection.getSubject());
                        }else{
                            subjectAssignedChoiceBox.setValue(" ");
                        }

                        if (newSelection.getDepartment() !=null){
                            departmentChoiceBox.setValue(newSelection.getDepartment());
                        }else{
                            departmentChoiceBox.setValue(" ");
                        }

                        if (newSelection.getQualification() !=null){
                            specializationChoiceBox.setValue(newSelection.getQualification());
                        }else{
                            specializationChoiceBox.setValue(" ");
                        }
                        addButton.setVisible(false);
                    }
                }
        );
    }

    private void refreshTeacherTable() {
        List<TeacherRecordEntity> teacherRecords = teacherRecordService.findAll();
        teacherTable.setItems(FXCollections.observableList(teacherRecords));
    }

    private void clear()
    {
        idTextField.setText("");
        nameTextField.setText("");
        subjectAssignedChoiceBox.setValue("");
        departmentChoiceBox.setValue("");
        designationTextField.setText("");
        specializationChoiceBox.setValue("");
    }
    private void onBackButtonPressed() {
        try {
            stageManager.switchScene(FxmlView.MENU);
        } catch (Exception e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Issue in switch scene");
        }
    }

    private void onAddButtonPressed() {
        try {
            String teacherName = nameTextField.getText().trim();
            if (teacherName.isEmpty()) {
                FXUtils.showMessage(Alert.AlertType.WARNING, "Please enter the teacher's name.");
                return;
            }

            String subjectAssigned = subjectAssignedChoiceBox.getValue();
            String department = departmentChoiceBox.getValue();
            String designation = designationTextField.getText().trim();
            String specialization = specializationChoiceBox.getValue();

            if (subjectAssigned == null || department == null || designation.isEmpty() || specialization == null) {
                FXUtils.showMessage(Alert.AlertType.WARNING, "Please fill in all required fields.");
                return;
            }

            TeacherRecordEntity teacherRecordEntity = new TeacherRecordEntity();
            teacherRecordEntity.setName(teacherName);
            teacherRecordEntity.setSubject(subjectAssigned);
            teacherRecordEntity.setDepartment(department);
            teacherRecordEntity.setDesignation(designation);
            teacherRecordEntity.setQualification(specialization);

            teacherRecordService.save(teacherRecordEntity);
            FXUtils.showMessage(Alert.AlertType.INFORMATION, "Teacher Record Successfully Saved");
            refreshTeacherTable();
            idTextField.setText(String.valueOf(teacherRecordEntity.getTeacherId()));

        } catch (Exception e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Error occurred while saving teacher record: " + e.getMessage());
        }
        idLabel.setVisible(true);
        idTextField.setVisible(true);
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
            final TeacherRecordEntity teacherRecordEntity = teacherRecordService.findById(searchId);

            if (teacherRecordEntity != null) {
                FXUtils.showMessage(Alert.AlertType.INFORMATION, "Teacher Record found for ID: " + teacherRecordEntity.getTeacherId());
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

            Long teacherId = Long.parseLong(idText);
            teacherRecordService.delete(teacherId);
            FXUtils.showMessage(Alert.AlertType.INFORMATION, "Teacher Record successfully deleted");
            refreshTeacherTable();
            clear();
        } catch (NumberFormatException e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Invalid ID format");
        } catch (Exception e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Error deleting teacher record: " + e.getMessage());
        }
        addButton.setVisible(true);
    }

    private void onUpdateButtonPressed() {
        try {
            Long teacherId = Long.parseLong(idTextField.getText());

            TeacherRecordEntity existingEntity = teacherRecordService.findById(teacherId);


            if (existingEntity != null) {
                existingEntity.setName(nameTextField.getText());
                existingEntity.setDesignation(designationTextField.getText());
                existingEntity.setSubject(subjectAssignedChoiceBox.getValue());
                existingEntity.setDepartment(departmentChoiceBox.getValue());
                existingEntity.setQualification(specializationChoiceBox.getValue());

                teacherRecordService.save(existingEntity);

                FXUtils.showMessage(Alert.AlertType.INFORMATION, "Teacher Record updated successfully");
                refreshTeacherTable();
            } else {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Record not found for ID: " + teacherId);
            }
        } catch (NumberFormatException e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Invalid ID format");
        } catch (Exception e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Error occurred while updating teacher record: " + e.getMessage());
        }
        clear();
        addButton.setVisible(true);
    }

    private  static  List<String> getSpecialization(){
        return  List.of("BSCS","BS Mathematics","PHD Physics","PHD Computer","MSCS","MS English","PHD Islamist");
    }

    private  static  List<String> getDepartment(){
        return  List.of("Physics Department","Chemistry Department","Biology Department","Computer Science Department","Art Department","MS English","PHD Islamist");
    }

    private  static  List<String> getSubjectAssigned(){
        return  List.of("Mathematics","Science","English","Pak Studies","Geography","Computer Science","Islamist","Physics","Chemistry","Biology");
    }

}