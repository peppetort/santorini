package it.polimi.ingsw.GUI;

import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.Messages.Message;
import it.polimi.ingsw.Messages.PlayerCreateSessionMessage;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the create match menu.
 */
public class CreateMenuController implements Initializable {

	public static MainController mainController;

	public TextField sessionName;
	public TextField username;
	public ToggleButton cardBox;
	public RadioButton playerButton2;
	public RadioButton playerButton3;
	public Button backButton;
	public Button createButton;
	public ImageView hourGlass1;
	public ImageView hourGlass2;
	public Label errorLabel;

	private static ArrayList<ImageView> images = new ArrayList<>();
	private static ArrayList<Button> buttons = new ArrayList<>();
	private static Label erLabel = new Label();

	/**
	 * Method for scene initialization.
	 * @param url
	 * @param resourceBundle
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		playerButton2.setSelected(true);
		//cardBox.getItems().addAll("Yes","No");
		//cardBox.getSelectionModel().selectFirst();
		//playerBox.getItems().addAll(2,3);
		//playerBox.getSelectionModel().selectFirst();
		images.add(hourGlass1);
		images.add(hourGlass2);
		buttons.add(createButton);
		buttons.add(backButton);

		errorLabel.setVisible(false);
		erLabel = errorLabel;

	}

	/**
	 *
	 * @param mc
	 */
	public static void setMainController(MainController mc) {
		mainController = mc;
	}

	/**
	 * Method used to handle a mouseClick over {@link Button} "BACK" (backButton).
	 * It loads a different FXML resource and create a new stage that will replace
	 * the current one.
	 * @throws IOException
	 */
	public void handleBack() throws IOException {
		AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("StartMenu.fxml")));
		Scene scene = new Scene(pane, 715, 776);
		AppMain.window.setMinWidth(715);
		AppMain.window.setMinHeight(776);
		AppMain.window.setMaxWidth(715);
		AppMain.window.setMaxHeight(776);
		AppMain.window.setScene(scene);
	}

	/**
	 * Method used to handle the MouseClick over create {@link Button}. It notifies
	 * a {@link PlayerCreateSessionMessage}  to the {@link MainController} attribute
	 * "mainController".
	 */
	public void handleCreate() {
		new Thread(() -> {
			boolean simple;
			int playersNumber;

			simple = !cardBox.isSelected();

			if (playerButton2.isSelected()) {
				playersNumber = 2;
			} else {
				playersNumber = 3;
			}
			Message msg = new PlayerCreateSessionMessage(username.getText(), sessionName.getText(), playersNumber, simple);
			mainController.notify(msg);
		}).start();

		errorLabel.setVisible(false);

		hourGlass1.setVisible(true);
		hourGlass2.setVisible(true);

		backButton.setDisable(true);
		createButton.setDisable(true);

		RotateTransition rt1 = new RotateTransition(Duration.millis(2000), hourGlass1);
		RotateTransition rt2 = new RotateTransition(Duration.millis(2000), hourGlass2);

		rt1.setByAngle(360);
		rt1.setCycleCount(Animation.INDEFINITE);
		rt1.setInterpolator(Interpolator.LINEAR);

		rt2.setByAngle(360);
		rt2.setCycleCount(Animation.INDEFINITE);
		rt2.setInterpolator(Interpolator.LINEAR);

		rt1.play();
		rt2.play();
	}

	/**
	 * Method that handle an exception. The {@link Exception} {@link it.polimi.ingsw.Exceptions.AlreadyExistingSessionException}
	 * read by {@link Client} socket is notified to {@link MainController} that
	 * uses this method to tell the player to choose another Name for the session.
	 */
	public void handleException() {
		Platform.runLater(() -> {
			for(ImageView i: images){
				i.setVisible(false);
			}
			for(Button b: buttons){
				b.setDisable(false);
			}
			erLabel.setText("Session's name already existing");
			erLabel.setVisible(true);
		});
	}

	/**
	 * When the {@link Client} receive a {@link it.polimi.ingsw.Messages.SuccessfulCreate}
	 * the player will enter in a waiting room, handled by {@link WaitController}.
	 * Method loads a different FXML resource and create a new stage that will replace
	 * the current one.
	 */
	public void handleStart() {
		Platform.runLater(() -> {
			try {
				AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Wait.fxml")));
				Scene scene = new Scene(pane, 953, 511);
				AppMain.window.setMinWidth(953);
				AppMain.window.setMinHeight(511);
				AppMain.window.setMaxWidth(953);
				AppMain.window.setMaxHeight(511);
				AppMain.window.setScene(scene);
			} catch (IOException ignored) {
			}
		});
	}

}
