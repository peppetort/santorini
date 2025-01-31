package it.polimi.ingsw.GUI;

import it.polimi.ingsw.Client.Box;
import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.Messages.ChatUpdateMessage;
import it.polimi.ingsw.Messages.InvalidChoiceMessage;
import it.polimi.ingsw.Messages.PlayerChatMessage;
import it.polimi.ingsw.Model.Actions;
import it.polimi.ingsw.Model.Color;
import it.polimi.ingsw.Model.God;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the PlayingStage.fxml stage. The {@link it.polimi.ingsw.Client.Client} in this stage is playing a
 * {@link it.polimi.ingsw.Model.Game}.
 */
public class PlayingStageController implements Initializable {

	public GridPane boardPane;
	public GridPane domePane;
	public GridPane actionPane;
	public GridPane pawnPane;

	public Label placeLabel;
	public Label moveLabel;
	public Label buildLabel;
	public Label selectLabel;

	public Button endButton;
	public Button undoButton;

	public AnchorPane godInfo;
	public ImageView podiumGod;
	public ImageView powerIcon;
	public Label godName;
	public Label actionType;
	public Text actionDescription;

	private static String godNameLabel;
	private static Image podiumGodImage;
	private static Image powerIconImage;
	private static String actionTypeLabel;
	private static String actionDescriptionLabel;


	public ImageView turnPlayerColor;
	public Text turnPlayerName;
	public ImageView turnPlayerGod;

	private boolean open = false;

	public TextArea chatField;
	public TextField chatText;
	public Button sendButton;

	private static MainController mainController;

	static MenuButton[][] menu = new MenuButton[5][5];

	static Building[][] buildings;
	static Building[][] domes;
	static Pawn[][] pawns;
	static MenuItem[][] actions;

	protected static int x, y;

	ActionsHandler actionsHandler = new ActionsHandler(mainController, this);

	static ObservableList<Actions> list = FXCollections.observableArrayList();
	static ObservableList<String> messages = FXCollections.observableArrayList();

	static ObservableList<String> turnName = FXCollections.observableArrayList();
	static ObservableList<Color> turnColor = FXCollections.observableArrayList();
	static ObservableList<God> turnGod = FXCollections.observableArrayList();

	public static void setMainController(MainController mc) {
		mainController = mc;
	}

	/**
	 * Initialize various components of the graphical interface. The board is a 5x5 {@link GridPane} which each cell
	 * contains {@link ImageView} fro buildings or domes and pawns. Each cell has a {@link MenuButton} that is filled
	 * with {@link MenuItem} of the possible actions for the {@link it.polimi.ingsw.Model.Player}.
	 * @param url
	 * @param resourceBundle
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		if (turnName != null) {
			turnPlayerName.setText(turnName.get(0));
		}

		if (turnColor != null) {
			Image color1;
			switch (turnColor.get(0)) {
				case RED:
					color1 = new Image(Objects.requireNonNull(PlayingStageController.class.getClassLoader().getResource("img/btn_coral_pressed.png")).toExternalForm());
					break;
				case BLUE:
					color1 = new Image(Objects.requireNonNull(PlayingStageController.class.getClassLoader().getResource("img/btn_blue_pressed.png")).toExternalForm());
					break;
				case GREEN:
					color1 = new Image(Objects.requireNonNull(PlayingStageController.class.getClassLoader().getResource("img/btn_purple_pressed.png")).toExternalForm());
					break;
				default:
					throw new IllegalArgumentException();
			}
			turnPlayerColor.setImage(color1);
		}

		try {
			GodObject podium = new GodObject(turnGod.get(0));
			turnPlayerGod.setImage(podium.getPodiumGodImage());
		} catch (IndexOutOfBoundsException | NullPointerException ignored) {
		}
		;

		endButton.setDisable(true);
		undoButton.setDisable(true);

		if (godNameLabel != null) {
			godName.setText(godNameLabel);
			actionType.setText(actionTypeLabel);
			actionDescription.setText(actionDescriptionLabel);
			podiumGod.setImage(podiumGodImage);
			powerIcon.setImage(powerIconImage);
		} else {
			godName.setText(mainController.client.getStatus().getUsername());
			actionType.setText("Default Turn");
			actionDescription.setText("Your worker must move to an adjacent space and then build");
		}

		chatField.setWrapText(true);
		chatField.setEditable(false);

		podiumGod.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			TranslateTransition transition = new TranslateTransition(Duration.millis(500), godInfo);

			if (open) {
				transition.setToX(0);
				open = false;
			} else {
				transition.setToX(200);
				open = true;
			}

			transition.play();
			event.consume();
		});

		mainController.setPlaying(true);

		turnName.addListener((ListChangeListener.Change<? extends String> change) -> {
			if (change.next()) {
				turnPlayerName.setText(change.getAddedSubList().get(0));
			}
		});

		turnColor.addListener((ListChangeListener.Change<? extends Color> change) -> {
			if (change.next()) {
				Image color;
				switch ((Color) change.getAddedSubList().get(0)) {
					case RED:
						color = new Image(Objects.requireNonNull(PlayingStageController.class.getClassLoader().getResource("img/btn_coral_pressed.png")).toExternalForm());
						break;
					case BLUE:
						color = new Image(Objects.requireNonNull(PlayingStageController.class.getClassLoader().getResource("img/btn_blue_pressed.png")).toExternalForm());
						break;
					case GREEN:
						color = new Image(Objects.requireNonNull(PlayingStageController.class.getClassLoader().getResource("img/btn_purple_pressed.png")).toExternalForm());
						break;
					default:
						throw new IllegalArgumentException();
				}
				turnPlayerColor.setImage(color);
			}
		});

		turnGod.addListener((ListChangeListener.Change<? extends God> change) -> {
			if (change.next()) {
				God god = change.getAddedSubList().get(0);
				if (god != null) {
					GodObject podium = new GodObject(god);
					turnPlayerGod.setImage(podium.getPodiumGodImage());
				}
			}
		});

		messages.addListener((ListChangeListener.Change<? extends String> change) -> {
			if (change.next()) {
				chatField.appendText(change.getAddedSubList().get(0));
			}
		});

		undoButton.setOnAction(actionsHandler::handleUndo);
		endButton.setOnAction(actionsHandler::handleEnd);

		actions = new MenuItem[5][5];
		pawns = new Pawn[5][5];
		buildings = new Building[5][5];
		domes = new Building[5][5];

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				pawns[i][j] = new Pawn();
				buildings[i][j] = new Building();
				domes[i][j] = new Building();
			}
		}


		try {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {

					menu[i][j] = new MenuButton();
					menu[i][j].setPrefHeight(80);
					menu[i][j].setPrefWidth(80);
					menu[i][j].setOpacity(0);
					menu[i][j].setPopupSide(Side.RIGHT);
					menu[i][j].setOnMouseEntered(this::handleMouseOver);
					menu[i][j].setOnMouseExited(this::handleMouseExit);
					menu[i][j].setOnMouseClicked(this::handleAction);

					GridPane.setConstraints(menu[i][j], j, i);
					GridPane.setConstraints(pawns[i][j], j, i);
					GridPane.setConstraints(buildings[i][j], j, i);
					GridPane.setConstraints(domes[i][j], j, i);

					actionPane.getChildren().add(menu[i][j]);
					pawnPane.getChildren().add(pawns[i][j]);
					boardPane.getChildren().add(buildings[i][j]);
					domePane.getChildren().add(domes[i][j]);
				}
			}
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}

		list.addListener((ListChangeListener.Change<? extends Actions> change) -> {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					menu[i][j].getItems().clear();
				}
			}


			Color player;
			int selected;
			int worker1X = -1;
			int worker1Y = -1;
			int worker2X = -1;
			int worker2Y = -1;
			int selectedX = -1;
			int selectedY = -1;

			try {
				player = mainController.client.getStatus().getColor();
				selected = mainController.client.getStatus().getSelected();
				worker1X = mainController.client.getBoard().getPlayersLatestBox().get(player)[0].getX();
				worker1Y = mainController.client.getBoard().getPlayersLatestBox().get(player)[0].getY();
				worker2X = mainController.client.getBoard().getPlayersLatestBox().get(player)[1].getX();
				worker2Y = mainController.client.getBoard().getPlayersLatestBox().get(player)[1].getY();

				if (selected == 1) {
					selectedX = worker1X;
					selectedY = worker1Y;
				} else {
					selectedX = worker2X;
					selectedY = worker2Y;
				}
			} catch (NullPointerException ignored) {
			}

			if (change.next()) {
				for (Actions a : Actions.values()) {
					if (list.contains(a) && change.wasAdded()) {
						switch (a) {
							case PLACE:
								placeLabel.getStyleClass().remove("actionLabel");
								placeLabel.getStyleClass().add("actionLabelSelected");

								for (int i = 0; i < 5; i++) {
									for (int j = 0; j < 5; j++) {
										actions[i][j] = new MenuItem("Place");
										actions[i][j].getStyleClass().add("menuLabel");
										actions[i][j].setOnAction(actionsHandler::handlePlace);
										menu[i][j].getItems().add(actions[i][j]);
									}
								}

								break;
							case BUILD:
								buildLabel.getStyleClass().remove("actionLabel");
								buildLabel.getStyleClass().add("actionLabelSelected");

								for (int i = selectedX - 1; i < selectedX + 2; i++) {
									for (int j = selectedY - 1; j < selectedY + 2; j++) {
										try {
											if (i != selectedX || j != selectedY) {
												actions[i][j] = new MenuItem("Build");
												actions[i][j].getStyleClass().add("menuLabel");
												actions[i][j].setOnAction(actionsHandler::handleBuild);
												menu[i][j].getItems().add(actions[i][j]);
											}
										} catch (IndexOutOfBoundsException ignored) {
										}
									}
								}
								break;
							case MOVE:
								moveLabel.getStyleClass().remove("actionLabel");
								moveLabel.getStyleClass().add("actionLabelSelected");

								for (int i = selectedX - 1; i < selectedX + 2; i++) {
									for (int j = selectedY - 1; j < selectedY + 2; j++) {
										try {
											if (i != selectedX || j != selectedY) {
												actions[i][j] = new MenuItem("Move");
												actions[i][j].getStyleClass().add("menuLabel");
												actions[i][j].setOnAction(actionsHandler::handleMove);
												menu[i][j].getItems().add(actions[i][j]);
											}
										} catch (IndexOutOfBoundsException ignored) {
										}
									}
								}
								break;
							case DECK:
								Platform.runLater(() -> {
									try {
										AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("AllCardsMenu.fxml")));
										Scene scene = new Scene(pane, 953, 511);
										AppMain.window.setMinWidth(953);
										AppMain.window.setMinHeight(511);
										AppMain.window.setMaxWidth(953);
										AppMain.window.setMaxHeight(511);
										AppMain.window.setScene(scene);
									} catch (IOException ignored) {
									}
								});
								break;
							case CARD:
								Platform.runLater(() -> {
									try {
										AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("SelectCardMenu.fxml")));
										Scene scene = new Scene(pane, 953, 511);
										AppMain.window.setMinWidth(953);
										AppMain.window.setMinHeight(511);
										AppMain.window.setMaxWidth(953);
										AppMain.window.setMaxHeight(511);
										AppMain.window.setScene(scene);
										AppMain.window.setScene(scene);
									} catch (IOException ignored) {
									}
								});
								break;
							case END:
								endButton.setDisable(false);
								break;
							case SELECT:
								selectLabel.getStyleClass().remove("actionLabel");
								selectLabel.getStyleClass().add("actionLabelSelected");

								actions[worker1X][worker1Y] = new MenuItem("Select");
								actions[worker2X][worker2Y] = new MenuItem("Select");
								actions[worker1X][worker1Y].getStyleClass().add("menuLabel");
								actions[worker2X][worker2Y].getStyleClass().add("menuLabel");
								actions[worker1X][worker1Y].setOnAction(actionsHandler::handleSelect);
								actions[worker2X][worker2Y].setOnAction(actionsHandler::handleSelect);
								menu[worker1X][worker1Y].getItems().add(actions[worker1X][worker1Y]);
								menu[worker2X][worker2Y].getItems().add(actions[worker2X][worker2Y]);
								break;
							case UNDO:
//								undoLabel.getStyleClass().remove("actionLabel");
//								undoLabel.getStyleClass().add("actionLabelSelected");
								undoButton.setDisable(false);
								break;
							case DOME:
//								buildDomeLabel.getStyleClass().remove("actionLabel");
//								buildDomeLabel.getStyleClass().add("actionLabelSelected");

								for (int i = selectedX - 1; i < selectedX + 2; i++) {
									for (int j = selectedY - 1; j < selectedY + 2; j++) {
										try {
											if (i != selectedX || j != selectedY) {
												actions[i][j] = new MenuItem("Dome");
												actions[i][j].getStyleClass().add("menuLabel");
												actions[i][j].setOnAction(actionsHandler::handleBuildDome);
												menu[i][j].getItems().add(actions[i][j]);
											}
										} catch (IndexOutOfBoundsException ignored) {
										}
									}
								}

								break;
						}
					} else if (change.wasRemoved() || !list.contains(a)) {
						switch (a) {
							case PLACE:
								placeLabel.getStyleClass().remove("actionLabelSelected");
								placeLabel.getStyleClass().add("actionLabel");
								break;
							case BUILD:
								buildLabel.getStyleClass().remove("actionLabelSelected");
								buildLabel.getStyleClass().add("actionLabel");
								break;
							case MOVE:
								moveLabel.getStyleClass().remove("actionLabelSelected");
								moveLabel.getStyleClass().add("actionLabel");
								break;
							case END:
								endButton.setDisable(true);
								break;
							case SELECT:
								selectLabel.getStyleClass().remove("actionLabelSelected");
								selectLabel.getStyleClass().add("actionLabel");
								break;
							case UNDO:
								undoButton.setDisable(true);
								break;
						}

					}
				}
			}
		});

	}

	/**
	 * Method that set the top Image that represents the {@link it.polimi.ingsw.Model.Player} that has the current turn.
	 * It change the name, the color and the god image.
	 * @param name
	 * @param player
	 * @param god
	 */
	public static void setTurnPlayer(String name, Color player, God god) {
		if (turnName.isEmpty()) {
			turnName.add(name);
		} else {
			turnName.set(0, name);
		}

		if (turnColor.isEmpty()) {
			turnColor.add(player);
		} else {
			turnColor.set(0, player);
		}

		if (god != null) {
			if (turnGod.isEmpty()) {
				turnGod.add(god);
			} else {
				turnGod.set(0, god);
			}
		}
	}

	/**
	 * The {@link ObservableList} list will be cleared and it will be filled with the possible {@link Actions} for the
	 * {@link it.polimi.ingsw.Model.Player}.
	 * @param act
	 */
	public static void setActionLabel(ArrayList<Actions> act) {
		Platform.runLater(() -> {
			try {
				list.clear();
				list.addAll(act);
			} catch (NullPointerException e) {
				System.out.print("lista vuota");
			}
		});
	}

	/**
	 * When the {@link it.polimi.ingsw.Model.Player} click over a {@link MenuButton} of the {@link GridPane} this method
	 * will 'save' the coordinates where x is the row and y the column (according to the conventions used by {@link it.polimi.ingsw.Model.Board}).
	 * @param e
	 */
	public void handleAction(javafx.scene.input.MouseEvent e) {

		//NEL MODEL LA X E' LA RIGA E Y LA COLONNA

		x = GridPane.getRowIndex((Node) e.getSource());
		y = GridPane.getColumnIndex((Node) e.getSource());

		e.consume();
	}

	/**
	 * Increase the opacity of the {@link MenuButton} to let the player knows where is the cursor over the board pane.
	 * @param e
	 */
	public void handleMouseOver(javafx.scene.input.MouseEvent e) {
		((MenuButton) e.getSource()).setOpacity(0.4);
	}

	/**
	 * Dual of the handleMouseOver method. When the cursor exit the cell the opacity will be re-setted to the default
	 * value which is zero.
	 * @param e
	 */
	public void handleMouseExit(javafx.scene.input.MouseEvent e) {
		((MenuButton) e.getSource()).setOpacity(0);
	}

	/**
	 * When the {@link it.polimi.ingsw.Client.ClientBoard} the {@link MainController} is notified and this method will
	 * update the board view.
	 */
	public static void updateBoard() {
		Client client = mainController.client;

		//box ha il livello e il colore della pedina
		// box get level e get player
		try {
			Box[][] boardModel = client.getBoard().getBoard();
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (boardModel[i][j].getLevel() == 4) {
						domes[i][j].buildDome();
					} else {
						buildings[i][j].build(boardModel[i][j].getLevel());
					}
					pawns[i][j].setColor(boardModel[i][j].getPlayer());
				}
			}
		} catch (NullPointerException ignored) {
		}

	}

	/**
	 * Used to notify a new {@link PlayerChatMessage}. Triggered by a {@link MouseEvent} over the send message {@link Button}
	 * near the chatText {@link TextField}.
	 */
	@FXML
	private void handleSend() {
		mainController.notify(new PlayerChatMessage(chatText.getText()));
		chatText.setText("");
	}

	/**
	 * Updates the chat {@link TextArea} adding a new {@link String} to the {@link ObservableList} list messages.
 	 * @param msg
	 */
	public static void handleChatUpdate(ChatUpdateMessage msg) {
		messages.add(msg.getMessage());
	}

	/**
	 * Updates the chat {@link TextArea} adding a new red {@link String} to the {@link ObservableList} list messages which
	 * is the {@link InvalidChoiceMessage} message.
	 */
	public static void handleException(InvalidChoiceMessage message) {
		updateBoard();
		messages.add("\u001B[31m"+"Error: " + message.getMessage() + "\u001B[0m"+"\n");
		//messages.add("Error: " + message.getMessage() + "\n");
	}

	/**
	 * Based on the player's {@link God} it handle the {@link GodObject} (bottom left).
	 * @param selected
	 */
	public static void handleCardChoice(God selected) {

		GodObject god = new GodObject(selected);
		godNameLabel = god.getGodNameLabel();
		podiumGodImage = god.getPodiumGodImage();
		powerIconImage = god.getPowerIconImage();
		actionTypeLabel = god.getActionTypeLabel();
		actionDescriptionLabel = god.getActionDescriptionLabel();

	}

}
