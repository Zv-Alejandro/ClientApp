package org.ies.demo.fornix.clientapp.config;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import org.ies.demo.fornix.clientapp.events.SceneResizeEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.io.IOException;
import java.util.Objects;

public class StageManager {

    private final Stage primaryStage;
    private final FxmlLoader fxmlLoader;
    private final String applicationTitle;
    private final ApplicationEventPublisher eventPublisher;

    public StageManager(FxmlLoader fxmlLoader,
                        Stage primaryStage,
                        String applicationTitle,
                        ApplicationEventPublisher eventPublisher) {
        this.primaryStage = primaryStage;
        this.fxmlLoader = fxmlLoader;
        this.applicationTitle = applicationTitle;
        this.eventPublisher = eventPublisher;
    }

    public void switchScene(final FxmlView view) {
        primaryStage.setTitle(applicationTitle);

        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        Parent rootNode = loadRootNode(view.getFxmlPath());


        Scene scene = new Scene(rootNode);
        String stylesheet = Objects.requireNonNull(getClass()
                        .getResource("/styles/styles.css"))
                .toExternalForm();

        scene.getStylesheets().add(stylesheet);

        scene.widthProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number oldSceneWidth,
                    Number newSceneWidth) {
                eventPublisher.publishEvent(new SceneResizeEvent(this, newSceneWidth));
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void switchToNextScene(final FxmlView view) {

        Parent rootNode = loadRootNode(view.getFxmlPath());
        primaryStage.getScene().setRoot(rootNode);
        primaryStage.show();
    }


    private Parent loadRootNode(String fxmlPath) {
        try {
            return fxmlLoader.load(fxmlPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToFullScreenMode() {
        primaryStage.setFullScreen(true);
    }

    public void switchToWindowedMode() {
        primaryStage.setFullScreen(false);
    }

    public boolean isStageFullScreen() {
        return primaryStage.isFullScreen();
    }

    public void exit() {
        primaryStage.close();
    }

}
