package com.alabtaal.school.util;

import javafx.scene.control.Alert;

public class FXUtils {
    public static void showMessage(Alert.AlertType alertType, String message)
    {

        final Alert alert=new Alert(alertType);
        alert.setTitle(alertType.name());
        alert.setContentText(message);
        alert.showAndWait();
    }
}
