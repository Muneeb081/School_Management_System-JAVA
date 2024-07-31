package com.alabtaal.school.controller;

import com.alabtaal.school.config.StageManager;
import com.alabtaal.school.entity.FeeCounterEntity;
import com.alabtaal.school.entity.StudentRecordEntity;
import com.alabtaal.school.enums.FxmlView;
import com.alabtaal.school.service.FeeCounterService;
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
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;


@Controller
public class FeeCounterController implements Initializable {

    private final FeeCounterService feeCounterService;
    private final StudentRecordService studentRecordService;
    private final StageManager stageManager;

    @FXML
    private Label idLabel;
    @FXML
    private TextField idTextField;

    @FXML
    private DatePicker paymentDatePicker;

    @FXML
    private TextField feesTextField;

    @FXML
    private ChoiceBox<StudentRecordEntity> studentChoiceBox;

    @FXML
    private ChoiceBox<String> statusChoiceBox;


    @FXML
    private Button backButton;
    @FXML
    private Button addButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button deleteButton;
    @FXML
    Button updateButton;

    @FXML
    private TableView<FeeCounterEntity> feeCounterTableView;

    @FXML
    private TableColumn <FeeCounterEntity,String> idColumn;

    @FXML
    private TableColumn <FeeCounterEntity,String> nameColumn;


    @FXML
    private TableColumn <FeeCounterEntity,String> dateColumn;

    @FXML
    private TableColumn <FeeCounterEntity,String> feesColumn;

    @FXML
    private TableColumn <FeeCounterEntity,String> statusColumn;

    public FeeCounterController(FeeCounterService feeCounterService, StudentRecordService studentRecordService, @Lazy StageManager stageManager) {
        this.feeCounterService = feeCounterService;
        this.studentRecordService = studentRecordService;
        this.stageManager = stageManager;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idLabel.setVisible(false);
        idTextField.setVisible(false);
        setupStudentChoiceBox();
        setupStatusChoiceBox();
        setupListeners();
        setupFeeCounterTextView();

    }

    private void setupListeners()
    {
        idTextField.textProperty().addListener(
                (observable, oldValue, newValue)-> onIdTextFieldChanged(oldValue,newValue)
        );

        feesTextField.textProperty().addListener(
                (observable,oldValue,newValue)->onFeeTextFieldChanged(oldValue,newValue)
        );


        addButton.setOnAction(
                (event)->onAddButtonPressed()
        );
        deleteButton.setOnAction(
                (event)->onDeleteButtonPressed()
        );
        backButton.setOnAction(
                (event)->onBackButtonPressed()
        );
        updateButton.setOnAction(
                (event)->onUpdateButtonPressed()
        );
        searchButton.setOnAction(
                (event)->onSearchButtonPressed()
        );

    }

    private void setupStudentChoiceBox() {
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

    private void setupStatusChoiceBox(){
        statusChoiceBox.getItems().clear();
        statusChoiceBox.setItems(FXCollections.observableList(getStatus()));
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

    public void onFeeTextFieldChanged(String oldValue,String newValue)
    {
        try{
            if(newValue.isBlank())
            {
                feesTextField.setText(newValue);
                return;
            }
            Double.parseDouble(newValue);
        }catch (Exception e)
        {
            feesTextField.setText(oldValue);
        }
    }

    private  static  List<String> getStatus(){
        return  List.of("Paid","Unpaid");
    }


    private void setupFeeCounterTextView() {
        idColumn.setCellValueFactory(
                (cellData)->new SimpleStringProperty(cellData.getValue().getFeeCounterId().toString())
        );
        nameColumn.setCellValueFactory(
                (cellData)->{
                    StudentRecordEntity studentRecord = cellData.getValue().getStudentRecord();
                    if (studentRecord != null) {
                        return new SimpleStringProperty(studentRecord.getName());
                    } else {
                        return new SimpleStringProperty("");
                    }
                }
        );

        dateColumn.setCellValueFactory(
                (cellData)->new SimpleStringProperty(String.valueOf(cellData.getValue().getPaymentDate()))
        );
        feesColumn.setCellValueFactory(
                (cellData)->new SimpleStringProperty(cellData.getValue().getAmount().toString())
        );
        statusColumn.setCellValueFactory(
                (cellData)->new SimpleStringProperty(cellData.getValue().getStatus().toString())
        );

        refreshTeacherTable();

        feeCounterTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldSelection,newSelection)->{
                    if(newSelection!=null)
                    {
                        idTextField.setText(String.valueOf(newSelection.getFeeCounterId()));
                        paymentDatePicker.setValue(newSelection.getPaymentDate());
                        feesTextField.setText(newSelection.getAmount().toString());
                        statusChoiceBox.setValue(newSelection.getStatus());
                        if (newSelection.getStudentRecord() !=null){
                            studentChoiceBox.setValue(newSelection.getStudentRecord());
                        }else{
                            studentChoiceBox.setValue(null);
                        }
                        studentChoiceBox.setValue(newSelection.getStudentRecord());
                        addButton.setVisible(false);
                    }
                }
        );
    }


    private void clear()
    {
        idTextField.setText("");
        paymentDatePicker.setValue(null);
        feesTextField.setText("");
        statusChoiceBox.setValue("");
        studentChoiceBox.setValue(null);
    }

    private void refreshTeacherTable() {
        List<FeeCounterEntity> feeCounterRecords = feeCounterService.findAll();
        feeCounterTableView.setItems(FXCollections.observableList(feeCounterRecords));
    }
    private void onAddButtonPressed() {
        try {
            FeeCounterEntity feeCounterEntity = new FeeCounterEntity();
            StudentRecordEntity selectedStudent = studentChoiceBox.getValue();
            if (selectedStudent == null) {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Please select a student.");
                return;
            }
            selectedStudent = studentRecordService.findById(selectedStudent.getStudentId());
            feeCounterEntity.setStudentRecord(selectedStudent);
            LocalDate paymentDate = paymentDatePicker.getValue();
            if (paymentDate == null) {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Date Picker cannot be empty");
                return;
            }
            feeCounterEntity.setPaymentDate(paymentDate);
            String feesText = feesTextField.getText().trim();
            if (feesText.isEmpty()) {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Amount/Fees cannot be empty");
                return;
            }
            feeCounterEntity.setAmount(Double.parseDouble(feesText));
            String status = statusChoiceBox.getValue().toString();
            if (status.isEmpty()) {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Status CheckBox cannot be empty");
                return;
            }
            feeCounterEntity.setStatus(status);
            feeCounterService.save(feeCounterEntity);
            FXUtils.showMessage(Alert.AlertType.INFORMATION, "Student Fee Successfully Saved");
            refreshTeacherTable();
            idTextField.setText(String.valueOf(feeCounterEntity.getFeeCounterId()));
            idLabel.setVisible(true);
            idTextField.setVisible(true);
        } catch (NumberFormatException e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Invalid input format for fee amount");
        } catch(Exception e) {
            e.printStackTrace();
            FXUtils.showMessage(Alert.AlertType.ERROR, "Failed to save student fee");
        }
        clear();

    }
    private void onDeleteButtonPressed() {
        try {
            if (idTextField.getText().isEmpty()) {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Please select a fee entry to delete.");
                return;
            }
            long feeId = Long.parseLong(idTextField.getText());
            FeeCounterEntity feeCounterEntity=feeCounterService.findById(feeId);
            if (feeCounterEntity != null && feeCounterEntity.getStudentRecord() != null) {
                feeCounterEntity.setStudentRecord(null);
                feeCounterService.save(feeCounterEntity);
            }
            feeCounterService.delete(feeCounterEntity);
            refreshTeacherTable();
            FXUtils.showMessage(Alert.AlertType.INFORMATION, "Student fee successfully deleted");
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException caught: " + e.getMessage());
            FXUtils.showMessage(Alert.AlertType.ERROR, "Invalid input format for fee ID.");
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
            FXUtils.showMessage(Alert.AlertType.ERROR, "Failed to delete student fee.");
        }
        clear();
        addButton.setVisible(true);
    }
    private void onBackButtonPressed()
    {
        try{
            stageManager.switchScene(FxmlView.MENU);
        }catch(Exception e)
        {
            FXUtils.showMessage(Alert.AlertType.ERROR,"Error in switching scene to Menu page");
        }
    }

    private void onUpdateButtonPressed() {
        try {
            if (idTextField.getText().isEmpty()) {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Please select a fee entry to update.");
                return;
            }

            long feeId = Long.parseLong(idTextField.getText());
            FeeCounterEntity existingFee = feeCounterService.findById(feeId);
            if (existingFee == null) {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Selected fee entry does not exist.");
                return;
            }
            StudentRecordEntity selectedStudent = studentChoiceBox.getValue();
            if (selectedStudent == null) {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Please select a student.");
                return;
            }
            existingFee.setStudentRecord(selectedStudent);


            LocalDate paymentDate = paymentDatePicker.getValue();
            if (paymentDate == null) {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Date Picker cannot be empty");
                return;
            }
            existingFee.setPaymentDate(paymentDate);

            String feesText = feesTextField.getText().trim();
            if (feesText.isEmpty()) {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Amount/Fees cannot be empty");
                return;
            }
            existingFee.setAmount(Double.parseDouble(feesText));

            String status = statusChoiceBox.getValue().toString();
            if (status.isEmpty()) {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Status CheckBox cannot be empty");
                return;
            }
            existingFee.setStatus(status);
            feeCounterService.save(existingFee);


            FXUtils.showMessage(Alert.AlertType.INFORMATION, "Student Fee Successfully Updated");
            refreshTeacherTable();
            clear();
        } catch (NumberFormatException e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Invalid input format for fee amount");
        } catch (Exception e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Failed to update student fee");
        }
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
            final FeeCounterEntity feeCounterEntity = feeCounterService.findById(searchId);

            if (feeCounterEntity  != null) {
                FXUtils.showMessage(Alert.AlertType.INFORMATION, "Fee  Record found for ID: " + feeCounterEntity.getFeeCounterId());
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
}
