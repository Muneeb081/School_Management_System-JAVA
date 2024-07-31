package com.alabtaal.school.controller;

import com.alabtaal.school.config.StageManager;
import com.alabtaal.school.entity.MasterEntity;
import com.alabtaal.school.enums.FxmlView;
import com.alabtaal.school.service.MasterService;
import com.alabtaal.school.util.FXUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class MasterController implements Initializable {

    private final MasterService masterService;
    private final StageManager stageManager;

    @FXML
    private Label idLabel;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private Button backButton;

    @FXML
    private Button addButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button updateButton;

    @FXML
    private TableView<MasterEntity> masterTableView;

    @FXML
    private TableColumn<MasterEntity, String> idColumn;

    @FXML
    private TableColumn<MasterEntity, String> emailColumn;

    @FXML
    private TableColumn<MasterEntity, String> phoneColumn;

    @FXML
    private TableColumn<MasterEntity, String> addressColumn;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
    private static final String PHONE_REGEX = "^\\+?\\d{1,3}?[- .]?(\\(\\d{1,4}\\))?[\\d .-]{1,11}$";

    public MasterController(MasterService masterService, @Lazy StageManager stageManager) {
        this.masterService = masterService;
        this.stageManager = stageManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idLabel.setVisible(false);
        idTextField.setVisible(false);
        setupMasterTextView();
        setupListeners();
    }

    private boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean validatePhone(String phone) {
        Pattern pattern = Pattern.compile(PHONE_REGEX);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    private void refreshTeacherTable() {
        List<MasterEntity> masterRecords = masterService.findAll();
        masterTableView.setItems(FXCollections.observableList(masterRecords));
    }

    private void setupListeners() {
        idTextField.textProperty().addListener(
                (observable, oldValue, newValue) -> onIdTextFieldChanged(oldValue, newValue)
        );

        backButton.setOnAction(
                (event) -> onBackButtonPressed()
        );

        addButton.setOnAction(
                (event) -> onAddButtonPressed()
        );
        searchButton.setOnAction(
                (event) -> onSearchButtonPressed()
        );
        deleteButton.setOnAction(
                (event) -> onDeleteButtonPressed()
        );
        updateButton.setOnAction(
                (event) -> onUpdateButtonPressed()
        );
    }

    private void clear() {
        idTextField.setText("");
        emailTextField.setText("");
        phoneTextField.setText("");
        addressTextField.setText("");
    }

    public void onIdTextFieldChanged(String oldValue, String newValue) {
        try {
            if (newValue.isBlank()) {
                idTextField.setText(newValue);
            } else {
                Long.parseLong(newValue);
            }
        } catch (Exception e) {
            idTextField.setText(oldValue);
        }
    }

    private void setupMasterTextView() {
        idColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(String.valueOf(cellData.getValue().getId()))
        );
        emailColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(cellData.getValue().getEmail())
        );
        phoneColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(cellData.getValue().getPhone())
        );
        addressColumn.setCellValueFactory(
                (cellData) -> new SimpleStringProperty(cellData.getValue().getAddress())
        );

        refreshTeacherTable();

        masterTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        idTextField.setText(String.valueOf(newSelection.getId()));
                        emailTextField.setText(newSelection.getEmail());
                        phoneTextField.setText(newSelection.getPhone());
                        addressTextField.setText(newSelection.getAddress());
                        addButton.setVisible(false);
                    }
                }
        );
    }

    private void onBackButtonPressed() {
        stageManager.switchScene(FxmlView.MENU);
    }

    private void onAddButtonPressed() {
        try {
            final MasterEntity masterEntity = new MasterEntity();

            String email = emailTextField.getText().trim();
            String phone = phoneTextField.getText().trim();
            String address = addressTextField.getText().trim();

            if (!email.isEmpty() && validateEmail(email)) {
                masterEntity.setEmail(email);
            } else {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Invalid or empty email");
                return;
            }

            if (!phone.isEmpty() && validatePhone(phone)) {
                masterEntity.setPhone(phone);
            } else {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Invalid or empty phone number");
                return;
            }

            if (!address.isEmpty()) {
                masterEntity.setAddress(address);
            } else {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Address cannot be empty");
                return;
            }

            masterService.save(masterEntity);
            FXUtils.showMessage(Alert.AlertType.INFORMATION, "Master Table Record Successfully Saved");
            refreshTeacherTable();
            idTextField.setText(String.valueOf(masterEntity.getId()));
            idLabel.setVisible(true);
            idTextField.setVisible(true);

        } catch (Exception e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Record did not save successfully: " + e.getMessage());
        }
        clear();

    }

    private void onSearchButtonPressed() {
        try {
            Long searchId = Long.parseLong(idTextField.getText());

            final MasterEntity entity = masterService.findById(searchId);

            if (entity != null) {
                FXUtils.showMessage(Alert.AlertType.INFORMATION, "Master Record found: " + entity.getId());
                System.out.println("Master Record found for : " + entity.getId());
            } else {
                FXUtils.showMessage(Alert.AlertType.INFORMATION, "Required record not found for  " + searchId);
                System.out.println("Record not found for: " + searchId);
            }

        } catch (Exception e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, e.getMessage());
        }
        clear();
        addButton.setVisible(true);
    }

    private void onDeleteButtonPressed() {
        if (idTextField.getText().isEmpty()) {
            FXUtils.showMessage(Alert.AlertType.INFORMATION, "ID must be entered");
            return;
        }

        try {
            masterService.delete(Long.parseLong(idTextField.getText()));
            FXUtils.showMessage(Alert.AlertType.INFORMATION, "record deleted successfully");
            refreshTeacherTable();
            clear();
        } catch (Exception e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, e.getMessage());
        }
        addButton.setVisible(true);
    }

    private void onUpdateButtonPressed() {

        try {
            Long id = Long.parseLong(idTextField.getText());
            MasterEntity existingEntity = masterService.findById(id);

            if (existingEntity != null) {
                existingEntity.setEmail(emailTextField.getText());
                existingEntity.setPhone(phoneTextField.getText());
                existingEntity.setAddress(addressTextField.getText());

                masterService.save(existingEntity);

                FXUtils.showMessage(Alert.AlertType.INFORMATION, "Record updated successfully");
                refreshTeacherTable();
            } else {
                FXUtils.showMessage(Alert.AlertType.ERROR, "Record not found for ID: " + id);
            }
        } catch (Exception e) {
            FXUtils.showMessage(Alert.AlertType.ERROR, "Error occurred while updating record: " + e.getMessage());
        }
        clear();
        addButton.setVisible(true);
    }
}
