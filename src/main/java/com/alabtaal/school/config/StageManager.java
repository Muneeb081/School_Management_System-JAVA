package com.alabtaal.school.config;

import static org.slf4j.LoggerFactory.getLogger;

import com.alabtaal.school.enums.FxmlView;
import java.util.Objects;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lombok.Data;
import org.slf4j.Logger;

@Data
public class StageManager {

  private static final Logger LOG = getLogger(StageManager.class);
  private final Stage stage;
  private final SpringFXMLLoader springFXMLLoader;

  public StageManager(final SpringFXMLLoader springFXMLLoader, final Stage stage) {
    this.springFXMLLoader = springFXMLLoader;
    this.stage = stage;
  }

  private static void logAndExit(final String errorMsg, final Exception exception) {
    LOG.error(errorMsg, exception, exception.getCause());
    Platform.exit();
  }

  public void switchScene(final FxmlView view) {
    final Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
    show(viewRootNodeHierarchy, view.getTitle());
  }

  private void show(final Parent rootNode, final String title) {
    final Scene scene = prepareScene(rootNode);
    stage.setTitle(title);
    stage.setScene(scene);
    stage.sizeToScene();
    stage.centerOnScreen();

    try {
      stage.show();
    } catch (final Exception exception) {
      logAndExit("Unable to show scene for title" + title, exception);
    }
  }

  private Scene prepareScene(final Parent rootNode) {
    Scene scene = stage.getScene();

    if (scene == null) {
      scene = new Scene(rootNode);
    }
    scene.setRoot(rootNode);
    return scene;
  }

  /**
   * Loads the object hierarchy from a FXML document and returns to root node of that hierarchy.
   *
   * @return Parent root node of the FXML document hierarchy
   */
  private Parent loadViewNodeHierarchy(final String fxmlFilePath) {
    Parent rootNode = null;
    try {
      rootNode = springFXMLLoader.load(fxmlFilePath);
      Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
    } catch (final Exception exception) {
      logAndExit("Unable to load FXML view" + fxmlFilePath, exception);
    }
    return rootNode;
  }

  private void traverseNext(Node node, KeyCode keyCode) {
    if (node instanceof ComboBox) {
      ComboBox<?> comboBox = (ComboBox<?>) node;
      if (keyCode == KeyCode.ENTER) {
        comboBox.hide();
        simulateTabKeyTraversal(node);
      } else{
        comboBox.show();
      }
    } else if (node instanceof ChoiceBox) {
      ChoiceBox<?> choiceBox = (ChoiceBox<?>) node;
      if (keyCode == KeyCode.ENTER) {
        simulateTabKeyTraversal(node);
      } else{
        choiceBox.show();
      }
    } else if (node instanceof Button) {
      Button button = (Button) node;
      button.fire();
    } else if (node instanceof TextField) {
      TextField textField = (TextField) node;
      simulateTabKeyTraversal(textField);
    } else if (node instanceof PasswordField) {
      PasswordField passwordField = (PasswordField) node;
      simulateTabKeyTraversal(passwordField);
    } else if (node instanceof TextArea) {
      TextArea textArea = (TextArea) node;
      simulateTabKeyTraversal(textArea);
    } else if (node instanceof DatePicker) {
      DatePicker datePicker = (DatePicker) node;
      simulateTabKeyTraversal(datePicker);
    }
  }

  private void simulateTabKeyTraversal(Node node) {
    KeyEvent keyEvent =
        new KeyEvent(KeyEvent.KEY_PRESSED, "", "Tab", KeyCode.TAB, false, false, false, false);
    node.fireEvent(keyEvent);
  }
}
