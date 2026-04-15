package org.ies.demo.fornix.clientapp.controllerFx;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.ies.demo.fornix.clientapp.config.FxmlView;
import org.ies.demo.fornix.clientapp.config.StageManager;
import org.ies.demo.fornix.clientapp.events.LoginEvent;
import org.ies.demo.fornix.clientapp.exception.ConstraintsException;
import org.ies.demo.fornix.clientapp.exception.NotFoundException;
import org.ies.demo.fornix.clientapp.exception.UserExistsException;
import org.ies.demo.fornix.clientapp.models.Client;
import org.ies.demo.fornix.clientapp.services.ClientService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class ClientControllerFx implements Initializable {

    @FXML
    private Text title;

    @FXML
    private Text subtitle;

    @FXML
    private VBox center;

    @FXML
    private HBox upper;

    @FXML
    private BorderPane root;

    @FXML
    private ImageView logoImage;

    @FXML
    private ImageView settingsImage;

    @FXML
    private Button loginButton;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField passwordCheck;

    @FXML
    private Label clientErrorLabel;

    StringProperty errorProperty = new SimpleStringProperty();

    private final StageManager stageManager;

    private final ClientService clientService;

    private final ApplicationEventPublisher eventPublisher;

    private final PasswordEncoder encoder;


    @Lazy
    public ClientControllerFx(StageManager stageManager, ClientService clientService, ApplicationEventPublisher eventPublisher, PasswordEncoder encoder) {
        this.stageManager = stageManager;
        this.clientService = clientService;
        this.eventPublisher = eventPublisher;
        this.encoder =  encoder;
    }


    public void loadUserAndOpenMarketPlace() {
        String name = username.getText();
        String hash = encoder.encode(password.getText());

        Optional<Client> user = clientService.findByUsername(username.getText());
        if (user.isEmpty()) {
            errorProperty.setValue(
                    "No user with this name found.");
            throw new NotFoundException(errorProperty.getValue());
        }

        Client client = user.get();
        if (!encoder.matches(password.getText(), client.getPasswordHashed())) {
            errorProperty.setValue(
                    "Incorrect password, did you forgot your password?");
            throw new NotFoundException(errorProperty.getValue());
        }

        stageManager.switchToNextScene(FxmlView.MARKETPLACE);

    }


    public void saveUserAndOpenLogInView() {

        String name = username.getText();

        Optional<Client> user = clientService.findByUsername(name);
        if (user.isPresent()) {
            errorProperty.setValue(
                    "User with this name already exists! Sign in or use another name.");
            throw new UserExistsException(errorProperty.getValue());
        }

        if (name.length() > 20 || name.isBlank()) {
            errorProperty.setValue(
                    "Username must not be blank or longer that 20 characters");
            throw new ConstraintsException(errorProperty.getValue());
        }

        if(!password.getText().equals(passwordCheck.getText())){
            errorProperty.setValue(
                    "Passwords don't match"
            );
            throw new ConstraintsException(errorProperty.getValue());
        }
        String hash = encoder.encode(password.getText());

        clientService.saveClient(name,hash);
        stageManager.switchToNextScene(FxmlView.LOGIN);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clientErrorLabel.textProperty().bind(errorProperty);
        username.textProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldText,
                                String newText) {
                eventPublisher.publishEvent(new LoginEvent(this, newText));
            }
        })
            ;
    }

    @FXML
    void switchEmailFormView(){ stageManager.switchToNextScene(FxmlView.EMAIL);}
}
