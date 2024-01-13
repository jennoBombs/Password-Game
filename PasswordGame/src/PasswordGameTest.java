

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import static javafx.application.Application.launch;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Modality;

import pw.PasswordGame;
import pw.MediumPassword;
import pw.StrongerPassword;
import pw.UserPassword;
import pw.StrongPassword;

public class PasswordGameTest extends Application {

    private final int MIN_PASSWORD_LENGTH = 8;
    private final int MAX_PASSWORD_LENGTH = 12;
    private int passwordLength = MIN_PASSWORD_LENGTH;
    private Label statusLabel;
    private Label passwordLabel;
    private Label resultLabel;
    private Label lengthLabel;
    private Label siteLabel;
    private Label instructionLabel;
    private Label comboBoxLabel;
    private TextField passwordTextField;
    private TextField siteTextField;
    private RandomAccessFile randomAccessFile;
    private ArrayList<String> passwordList = new ArrayList<>();
    private ArrayList<String> siteList = new ArrayList<>();
    private ComboBox<String> passwordComboBox = new ComboBox<>();
    
    private void updateComboBox() {
    passwordComboBox.getItems().clear(); // Remove all existing items from the combo box
    for (String site : siteList) {
        int index = siteList.indexOf(site);
        String password = passwordList.get(index);
        passwordComboBox.getItems().add(site + ": " + password);
    }
}

    
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Open or create a random access file for passwords
        File passwordFile = new File("passwords.dat");
        randomAccessFile = new RandomAccessFile(passwordFile, "rw");

        // Create a GridPane layout for the GUI
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));

        // Add labels, textfields, slider, and buttons to the GridPane
        passwordLabel = new Label("Enter password:");
        resultLabel = new Label();
        lengthLabel = new Label("Password Length:");
        statusLabel = new Label();
        siteLabel = new Label();
        instructionLabel = new Label("Instructions:\n" + 
                "If '8' is selected:\n" +
                "your password requires 1 upper case, 1 lower case, 1 number,\n" +
                "1 special character, with at least 8 characters.\n" +
                "If '10' is selected:\n" + 
                "your password requires 2 upper case,2 lower case, 2 numbers,\n" +
                "2 special characters, with at least 10 characters.\n" +
                "If '12' is selected:\n" +
                "your password requires 2 upper case,2 lower case, 3 numbers,\n" +
                "3 special characters, with at least 12 characters.\n");
        comboBoxLabel = new Label("Sites with saved Passwords:");
        
        // Set the border of the instructionLabel to make it stand out
        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1));
        instructionLabel.setBorder(new Border(borderStroke));
        instructionLabel.setPadding(new Insets(10));
        
        passwordTextField = new TextField();
        siteTextField = new TextField();
        
        Button saveButton = new Button ("Save password");
        Button checkStrengthButton = new Button("Check strength");
        Button viewPasswordsButton = new Button("View passwords");

        Slider lengthSlider = new Slider(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH, MIN_PASSWORD_LENGTH);
        lengthSlider.setShowTickLabels(true);
        lengthSlider.setShowTickMarks(true);
        lengthSlider.setMajorTickUnit(2);
        lengthSlider.setMinorTickCount(0);
        lengthSlider.setSnapToTicks(true);
        lengthSlider.setOrientation(Orientation.HORIZONTAL);

        HBox sliderBox = new HBox(lengthSlider);
        sliderBox.setAlignment(Pos.CENTER);

        gridPane.add(instructionLabel, 0, 0, 3, 3);
        gridPane.add(passwordLabel, 0, 4);
        gridPane.add(passwordTextField, 1, 4);
        gridPane.add(checkStrengthButton, 2, 4);
        gridPane.add(resultLabel, 0, 5, 3, 1);
        gridPane.add(lengthLabel, 0, 6);
        gridPane.add(sliderBox, 1, 6, 2, 1);
        gridPane.add(viewPasswordsButton, 2, 7);
        gridPane.add(saveButton, 1, 7);
        gridPane.add(statusLabel, 0, 8, 3, 1);
        gridPane.add(comboBoxLabel, 0,9,3,1);
        gridPane.add(passwordComboBox, 0, 10, 3, 1);

        // When the user clicks 'Check strength', the password is tested and sets the text
        // of the status and result labels.
        checkStrengthButton.setOnAction(event -> {
            UserPassword userPassword = new UserPassword(passwordTextField.getText(), passwordLength);
            userPassword.checkPasswordStrength();
            statusLabel.setText(userPassword.getStatus());
            resultLabel.setText("Points: " + userPassword.getPoints());    
        });
        // Create a new stage for the site name prompt
        Stage siteNameStage = new Stage();
        siteNameStage.initModality(Modality.APPLICATION_MODAL);
        siteNameStage.setTitle("Enter website name");

        // Create a GridPane layout for the site name prompt
        GridPane siteNameGridPane = new GridPane();
        siteNameGridPane.setAlignment(Pos.CENTER);
        siteNameGridPane.setHgap(10);
        siteNameGridPane.setVgap(10);
        siteNameGridPane.setPadding(new Insets(25, 25, 25, 25));

        // Add a Label and a TextField for the user to enter the website name
        Label siteLabel = new Label("Add a site name for the password:");
        TextField siteTextField = new TextField();
        siteNameGridPane.add(siteLabel, 0, 0);
        siteNameGridPane.add(siteTextField, 1, 0);

        // Add a Save button to confirm the website name entry
        Button siteNameSaveButton = new Button("Save");
        siteNameSaveButton.setOnAction(event -> siteNameStage.close());
        siteNameGridPane.add(siteNameSaveButton, 1, 1);

        // Set the Scene of the site name Stage to the GridPane layout
        siteNameStage.setScene(new Scene(siteNameGridPane));

        // Set up the Save button to prompt the user for the website name and save the password
        saveButton.setOnAction(event -> {
        // Display the site name prompt
        siteNameStage.showAndWait();

        // Get the website name entered by the user
        String websiteName = siteTextField.getText();
        siteList.add(siteTextField.getText());

        // Get the password entered by the user
        String password = passwordTextField.getText();
        String site = siteTextField.getText();
   
        siteList.add(site);
        passwordList.add(password);
        updateComboBox();
        // Save the password with the website name in the random access file
        try (RandomAccessFile randomAccessFile = new RandomAccessFile("passwords.dat", "rw")) {
            // Move the file pointer to the end of the file
            randomAccessFile.seek(randomAccessFile.length());

            // Write the website name and password to the file
            randomAccessFile.writeUTF(websiteName);
            randomAccessFile.writeUTF(password);

            // Add the website name to the passwordList
            passwordList.add(websiteName);

            // Update the statusLabel with a success message
            statusLabel.setText("Password saved successfully for " + websiteName);
            statusLabel.setTextFill(Color.GREEN);
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Error: Failed to save password");
            statusLabel.setTextFill(Color.RED);
        }
    });

    // Create a list of site names and add it to the ComboBox
    
    while (randomAccessFile.getFilePointer() < randomAccessFile.length()) {
        if (randomAccessFile.length() > randomAccessFile.getFilePointer()) {
            String site = randomAccessFile.readUTF();
            String password = randomAccessFile.readUTF();
            passwordList.add(password);
            siteList.add(site);
        } else {
            System.out.println("End of file reached.");
        }
    }
    passwordComboBox.getItems().addAll(siteList);
    
    viewPasswordsButton.setOnAction(event -> {
        passwordComboBox.getItems().clear();
        for (String site : siteList) {
            passwordComboBox.getItems().add(site);
        }
    });

    // When a site is selected, display the corresponding password in the status label
    passwordComboBox.setOnAction(event -> {
        int index = passwordComboBox.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            String site = siteList.get(index);
            String password = passwordList.get(index);
            statusLabel.setText("Site: " + site + ", Password: " + password);
        }
        
    });

        // Set the password length based on the slider value
        lengthSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            passwordLength = newValue.intValue();
            lengthLabel.setText("Password Length: " + passwordLength);
        });
        
        // Create a Scene and set it to the primaryStage
        Scene scene = new Scene(gridPane, 420, 500);
        primaryStage.setTitle("Password Strength Checker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
