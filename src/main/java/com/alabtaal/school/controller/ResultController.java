package com.alabtaal.school.controller;

import com.alabtaal.school.config.StageManager;
import com.alabtaal.school.entity.FeeCounterEntity;
import com.alabtaal.school.entity.ResultEntity;
import com.alabtaal.school.entity.StudentRecordEntity;
import com.alabtaal.school.enums.FxmlView;
import com.alabtaal.school.service.ResultService;
import com.alabtaal.school.service.StudentRecordService;
import com.alabtaal.school.util.FXUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class ResultController implements Initializable {


    private final ResultService resultService;
    private final StudentRecordService studentRecordService;
    private final StageManager stageManager;

    @FXML
    private Label idLabel;
    @FXML
    private TextField idTextField;

    @FXML
    private TextField marksTextField;

    @FXML
    private ChoiceBox<String> subjectChoiceBox;
    @FXML
    private ChoiceBox<String> gradeChoiceBox;
    @FXML
    private ChoiceBox<StudentRecordEntity> studentChoiceBox;


    @FXML
    private TableView<ResultEntity> resultTable;
    @FXML
    private TableColumn<ResultEntity, String> idColumn;

    @FXML
    private TableColumn<ResultEntity, String> studentColumn;

    @FXML
    private TableColumn<ResultEntity, String> subjectColumn;

    @FXML
    private TableColumn<ResultEntity, String> marksColumn;

    @FXML
    private TableColumn<ResultEntity, String> gradeColumn;

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

    public ResultController(ResultService resultService, StudentRecordService studentRecordService, @Lazy StageManager stageManager) {
        this.resultService = resultService;
        this.studentRecordService = studentRecordService;
        this.stageManager = stageManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idLabel.setVisible(false);
        idTextField.setVisible(false);
        setSubjectChoiceBox();
        setGradeChoiceBox();
        setStudentChoiceBox();
        setupListeners();
        setupResultTable();
    }

    private void setSubjectChoiceBox(){
        subjectChoiceBox.getItems().clear();
        subjectChoiceBox.setItems(FXCollections.observableList(getSubject()));
    }

    private void setGradeChoiceBox(){
        gradeChoiceBox.getItems().clear();
        gradeChoiceBox.setItems(FXCollections.observableList(getGrade()));
    }

    private void setStudentChoiceBox() {
        List<StudentRecordEntity> studentRecords = studentRecordService.findAll();
        studentChoiceBox.getItems().clear();
        studentChoiceBox.setItems(FXCollections.observableList(studentRecords));

        studentChoiceBox.setConverter(new StringConverter<StudentRecordEntity>() {
            @Override
            public String toString(StudentRecordEntity student) {
                if (student == null) {
                    return "";
                }
                return student.getName() + " (ID: " + student.getStudentId() + ")";
            }

            @Override
            public StudentRecordEntity fromString(String string) {
                return null;
            }
        });
    }


    private void setupListeners() {
        idTextField.textProperty().addListener(
                (observable, oldValue, newValue)-> onIdTextFieldChanged(oldValue,newValue)
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

    private void setupResultTable() {
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getResultId())));
        subjectColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSubject()));
        marksColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMarks().toString()));
        gradeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGrade()));
        studentColumn.setCellValueFactory(
                (cellData) ->{
                    StudentRecordEntity studentRecord = cellData.getValue().getStudentRecord();
                    if (studentRecord != null) {
                        return new SimpleStringProperty(studentRecord.getName());
                    } else {
                        return new SimpleStringProperty("");
                    }
                }
        );
        refreshResultTable();
        resultTable.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldSelection,newSelection) -> {
                    if (newSelection != null){
                        idTextField.setText(String.valueOf(newSelection.getResultId()));
                        marksTextField.setText(newSelection.getMarks().toString());


                        if (newSelection.getSubject() !=null){
                            subjectChoiceBox.setValue(newSelection.getSubject());
                        }else{
                            subjectChoiceBox.setValue(" ");
                        }

                        if (newSelection.getGrade() !=null){
                            gradeChoiceBox.setValue(newSelection.getGrade());
                        }else{
                            gradeChoiceBox.setValue(" ");
                        }

                        if (newSelection.getStudentRecord()!=null){
                            studentChoiceBox.setValue(newSelection.getStudentRecord());
                        }else{
                            studentChoiceBox.setValue(null);
                        }
                        addButton.setVisible(false);
                    }
                }
        );
    }

    private void refreshResultTable() {
        List<ResultEntity> resultRecords = resultService.findAll();
        resultTable.setItems(FXCollections.observableList(resultRecords));
    }

    private void clear()
    {
        idTextField.setText("");
        marksTextField.setText("");
        subjectChoiceBox.setValue("");
        gradeChoiceBox.setValue("");
        studentChoiceBox.setValue(null);
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
            ResultEntity resultEntity = new ResultEntity();
            String marksText = marksTextField.getText().trim();
            if (marksText.isEmpty()) {
                FXUtils.showMessage(Alert.AlertType.WARNING, "Please enter the student's marks.");
                return;
            }
            Double marks = Double.parseDouble(marksText);
            if(marks<0 || marks>100)
            {
                FXUtils.showMessage(Alert.AlertType.WARNING, "Please enter the student's marks within range of 1 to 100");
                return;
            }

            String subject = subjectChoiceBox.getValue();
            String grade = gradeChoiceBox.getValue();
            StudentRecordEntity selectedStudent = studentChoiceBox.getValue();
            if (selectedStudent == null) {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Please select a student.");
                return;
            }

            resultEntity.setStudentRecord(selectedStudent);
            resultEntity.setMarks(marks);
            resultEntity.setSubject(subject);
            resultEntity.setGrade(grade);
            resultService.save(resultEntity);

            FXUtils.showMessage(Alert.AlertType.INFORMATION, "Result Successfully Saved");
            refreshResultTable();
            idTextField.setText(String.valueOf(resultEntity.getResultId()));
            idLabel.setVisible(true);
            idTextField.setVisible(true);

        } catch (Exception e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Error occurred while saving result: " + e.getMessage());
        }
        clear();
    }

    private void onDeleteButtonPressed() {
        try {
            if (idTextField.getText().isEmpty()) {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Please select a result entry to delete.");
                return;
            }
            String idText = idTextField.getText().trim();
            Long resultId = Long.parseLong(idText);
            ResultEntity resultEntity = resultService.findById(resultId);
            if (resultEntity != null && resultEntity.getStudentRecord() != null) {
                resultEntity.setStudentRecord(null);
                resultService.save(resultEntity);
            }
            if (resultEntity == null) {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Result with ID " + resultId + " not found.");
                return;
            }
            resultService.delete(resultEntity);
            refreshResultTable();
            FXUtils.showMessage(Alert.AlertType.INFORMATION, "Result successfully deleted");
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException caught: " + e.getMessage());
            FXUtils.showMessage(Alert.AlertType.ERROR, "Invalid input format for result ID.");
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            FXUtils.showMessage(Alert.AlertType.ERROR, "Failed to delete result.");
        }
        clear();
        addButton.setVisible(true);
    }

    private void onUpdateButtonPressed() {
        try {
            Long resultId = Long.parseLong(idTextField.getText());
            ResultEntity existingEntity = resultService.findById(resultId);

            if (existingEntity != null) {
                String marksText = marksTextField.getText().trim();
                if (marksText.isEmpty()) {
                    FXUtils.showMessage(Alert.AlertType.WARNING, "Please enter the student's marks.");
                    return;
                }
                Double marks = Double.parseDouble(marksText);
                if(marks<0 || marks>100)
                {
                    FXUtils.showMessage(Alert.AlertType.WARNING, "Please enter the student's marks within range of 1 to 100");
                    return;
                }

                existingEntity.setMarks(marks);
                existingEntity.setSubject(subjectChoiceBox.getValue());
                existingEntity.setGrade(gradeChoiceBox.getValue());

                StudentRecordEntity selectedStudent = studentChoiceBox.getValue();
                if (selectedStudent == null) {
                    FXUtils.showMessage(Alert.AlertType.ERROR, "Please select a student.");
                    return;
                }
                existingEntity.setStudentRecord(selectedStudent);

                resultService.save(existingEntity);

                FXUtils.showMessage(Alert.AlertType.INFORMATION, "Result updated successfully");
                refreshResultTable();
            } else {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Record not found for ID: " + resultId);
            }
        } catch (NumberFormatException e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Invalid ID format");
        } catch (Exception e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Error occurred while updating result: " + e.getMessage());
        }
        clear();
        addButton.setVisible(true);
    }


    private void onSearchButtonPressed() {
        try {
            String searchIdText = idTextField.getText().trim();

            if (searchIdText.isEmpty()) {
                FXUtils.showMessage(Alert.AlertType.ERROR, "ID field is empty");
                return;
            }

            Long searchId = Long.parseLong(searchIdText);
            final ResultEntity resultEntity = resultService.findById(searchId);

            if (resultEntity != null) {
                FXUtils.showMessage(Alert.AlertType.INFORMATION, "Result found for ID: " + resultEntity.getResultId());
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

    private static List<String> getGrade() {
        return List.of("A", "B","C","D","F");
    }

    private  static  List<String> getSubject(){
        return  List.of("Mathematics","Science","English","Pak Studies","Geography","Computer Science","Islamist","Physics","Chemistry","Biology");
    }
}
