package org.ies.demo.fornix.clientapp.controllerFx;



import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.ies.demo.fornix.clientapp.config.FxmlView;
import org.ies.demo.fornix.clientapp.config.StageManager;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class HomeController implements Initializable {

    @FXML
    private Button theoryButton;

    @FXML
    private Button generatorButton;

    @FXML
    private Button scalesButton;

    @FXML
    private Button chordsButton;

    @FXML
    private Button intervalsButton;

    @FXML
    private Button dictationsButton;

    @FXML
    private Label helloLabel;

    @FXML
    private ImageView gClef;

    private final StageManager stageManager;

    StringProperty nameProperty = new SimpleStringProperty();

    @Lazy
    public HomeController(StageManager stageManager) {
        this.stageManager = stageManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gClef.setFitWidth(1000);
        gClef.setPreserveRatio(true);
        gClef.setSmooth(true);

        helloLabel.textProperty().bind(nameProperty);

    }

    @FXML
    void switchToTheoryScene() {
        stageManager.switchToNextScene(FxmlView.SCALES_THEORY);
    }

    @FXML
    void switchToScalesScene() {
        stageManager.switchToNextScene(FxmlView.SCALES);
    }

    @FXML
    void switchToChordsScene() {

    }

    @FXML
    public void switchToChordGeneratorScene() {
    }

    @FXML
    public void switchToIntervalsScene(ActionEvent actionEvent) {
        stageManager.switchToNextScene(FxmlView.INTERVALS);

    }


    @FXML
    public void switchToDictationsScene(ActionEvent actionEvent) {

    }
}
